package com.tigerff.community.controller;

import com.tigerff.community.dto.CommentDto;
import com.tigerff.community.dto.CommentReadDto;
import com.tigerff.community.dto.ResponseDto;
import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import com.tigerff.community.model.Comment;
import com.tigerff.community.model.User;
import com.tigerff.community.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/27 14:15
 */
@Slf4j
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    //这个是提交评论来处理的api--接收后台的参数，
    // parentId - 就是问题的 id
    // content 评论内容
    // type 评论分为一级评论和二级评论用 type 来区分 一级就是直接回复问题，二级就是回复评论的
    // 用一个对象来封装，好接收
    @ResponseBody  //加上这个 @ResponseBody 后 spring 会将返回给页面的数据自动解析位 json 格式
    @PostMapping("/comment")
    public Object post(@RequestBody CommentReadDto commentReadDto,
                       HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");//获取当前的用户
        if(user==null)
        {
            return ResponseDto.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(commentReadDto.getComment()==null||commentReadDto.getComment()==""||commentReadDto==null)
        {
            return ResponseDto.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment=new Comment();
        comment.setContent(commentReadDto.getComment());
        comment.setParentId(commentReadDto.getParentId());
        comment.setType(commentReadDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtUpdate(comment.getGmtCreate());
        comment.setLikeCount(0);
        comment.setReadCount(0);
        comment.setCommentator(user.getId());
        int insert = commentService.insert(user,comment);
        if(insert==1)
        {
            log.info("插入评论成功-----"+comment);
            return ResponseDto.okOf();
        }else {
            log.info("插入数据失败--------"+comment);
            return ResponseDto.errorOf(500,"插入数据失败");
        }
    }
    //获取二级评论
    @ResponseBody  //加上这个 @ResponseBody 后 spring 会将返回给页面的数据自动解析位 json 格式
    @GetMapping("/comment/{id}")
    public ResponseDto<List<CommentDto>> content2(@PathVariable(name = "id") Long id)
    {
        List<CommentDto> commentList = commentService.getCommentList(id, CommentTypeEnum.COMMENT);
        return ResponseDto.okOf(commentList);
    }
}
