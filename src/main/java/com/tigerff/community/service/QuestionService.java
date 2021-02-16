package com.tigerff.community.service;

import com.tigerff.community.dto.PageInfo;
import com.tigerff.community.dto.QuestionDto;
import com.tigerff.community.mapper.QuestionMapper;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.Question;
import com.tigerff.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/16 19:05
 */
@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PageInfo findQuestions(int page, int size) {
        Integer totalQuestion = questionMapper.getCount();
        Integer totalPage=(int) Math.ceil(totalQuestion/size);
        if(page<1)
            page=1;
        if(page>totalPage)
            page=totalPage;
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
        List<Question> questions=questionMapper.findQuestions(size*(page-1),size);
        ArrayList<QuestionDto> questionDtos=new ArrayList<>();

        for(Question question:questions)
        {
            User creator=userMapper.findUserById(question.getCreator());
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
}
