package com.tigerff.community.service;

import com.tigerff.community.dto.CommentDto;
import com.tigerff.community.dto.PageInfo;
import com.tigerff.community.dto.QuestionDto;
import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import com.tigerff.community.mapper.CommentMapper;
import com.tigerff.community.mapper.QuestionExMapper;
import com.tigerff.community.mapper.QuestionMapper;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/16 19:05
 */
@Service
public class QuestionService {
    @Autowired
    QuestionExMapper questionExMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    public PageInfo findQuestions(String search,int page, int size) {
        QuestionExample questionExample1 = new QuestionExample();
        StringJoiner sj=new StringJoiner("|");
        int totalQuestion=0;
        //添加搜索功能
        if(search!=null&&(!"".equals(search)))
        {
            String[] s = search.split(" ");
            for(String p:s)
                sj.add(p);
            questionExample1.setOrderByClause("REGEXP"+sj.toString());
             totalQuestion= questionExMapper.countByExample(sj.toString());
        }
        else
            totalQuestion=(int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage=(int) Math.ceil(totalQuestion*1.0/size);
        if(page<1)
            page=1;
        if(totalPage>0&&page>totalPage)
        {
            page=totalPage;
        }
        if(totalPage==0)
        {
            totalPage=1;
            page=totalPage;
        }
        if(size<=0)
            size=5;
        PageInfo pageInfo=new PageInfo();
        /**
         * page =1
         * size=5
         * size*(page-1)
         * limit 1,5
         */
        pageInfo.setCurrentPage(page);
        //List<Question> questions=questionMapper.findQuestions(size*(page-1),size);
        //添加搜索功能
        List<Question> questions=null;
        if(search!=null&&(!"".equals(search))) {
            questions=questionExMapper.selectByExampleWithRowbounds(sj.toString(),size * (page - 1),size);
        }
        else
        {
            QuestionExample questionExample = new QuestionExample();
            questionExample.setOrderByClause("gmt_create desc");
            questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(size * (page - 1), size));
        }

        ArrayList<QuestionDto> questionDtos=new ArrayList<>();


        for(Question question:questions)
        {
            User creator = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(creator);
            questionDtos.add(questionDto);
        }
        pageInfo.setQuestionDtos(questionDtos);
        pageInfo.setTotalPage(totalPage);
        //设置页面
        ArrayList<Integer> list=new ArrayList<>();
        list.add(page);
        for(int i=1;i<5;i++)
        {
            if(page-i>0)
                list.add(0,page-i);
            if(page+i<=totalPage)
                list.add(page+i);
        }
        pageInfo.setList(list);
        //判断 是否是第一页
        if(page==1)
            pageInfo.setFirstPage(false);
        else
            pageInfo.setFirstPage(true);
        //判断是否是最后一页
        if(page==totalPage)
            pageInfo.setEndPage(false);
        else
            pageInfo.setEndPage(true);
        //判断是否包含第一页
        if(list.contains(1))
            pageInfo.setShowFirstPage(false);
        else
            pageInfo.setShowFirstPage(true);
        //判断是否包含最后一页
        if(list.contains(totalPage))
            pageInfo.setShowEndPage(false);
        else
            pageInfo.setShowEndPage(true);


        return pageInfo;

        
    }

    public QuestionDto findQuestionById(Long id) {
        QuestionDto questionDto=new QuestionDto();
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null)
        {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    //浏览数加一
    public void incReadCount(Long id) {
        questionExMapper.incWatchCount(id);
    }

    public List<Question> findQuestionByTag(Long id, String tag) {
        tag=tag.replaceAll(",","|");
        return questionExMapper.findQuestionByTag(id,tag);
    }
}
