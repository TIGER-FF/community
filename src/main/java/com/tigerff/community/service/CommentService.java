package com.tigerff.community.service;

import com.tigerff.community.dto.CommentDto;
import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeErrorCodeInter;
import com.tigerff.community.exception.CustomizeException;
import com.tigerff.community.mapper.*;
import com.tigerff.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/27 15:09
 */
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExMapper questionExMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentExMapper commentExMapper;
    @Transactional//加上事务的注解
    public int insert(Comment comment) {
        //判断问题的 id 是否为空
        if(comment.getParentId()==null)
        {
            //返回错误
            throw  new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }
        //判断类型数据是否正确
        if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType()))
        {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_ERROR);
        }
        commentExMapper.incReadCount(comment.getParentId());
        if(comment.getType()==CommentTypeEnum.COMMENT.getType())
        {
            //回复的是评论--二级
            //接下来，判断这个评论是否存在
            Comment selectComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(selectComment==null)
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
        }else
        {
            //直接回复的是问题--一级
            //判断问题是否存在
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            questionExMapper.incReadCount(comment.getParentId());
        }
        return commentMapper.insert(comment);
    }
    /**
     * 获取回复问题的列表
     * @param id 问题的 id
     * @return
     */
    public List<CommentDto> getCommentList(Long id, CommentTypeEnum question)
    {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(question.getType());
        //安装创建时间的倒序进行排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if(commentList.size()==0)
            return new ArrayList<>();
        //将评论人的信息加入到这个里面
        //获取到评论人的 id 合集
        List<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).distinct().collect(Collectors.toList());
        //获取到这些人的信息
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(commentators);
        List<User> users = userMapper.selectByExample(userExample);
        //将这些评论和评论人进行匹配--可以让 users 对应成 map 这样就不用两层 for 循环进行遍历了，见小了复杂度

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //遍历评论，和评论人进行匹配
        List<CommentDto> commentDtos = commentList.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
