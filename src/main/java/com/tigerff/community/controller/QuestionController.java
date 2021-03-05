package com.tigerff.community.controller;

import com.tigerff.community.dto.CommentDto;
import com.tigerff.community.dto.QuestionDto;
import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.model.Question;
import com.tigerff.community.model.User;
import com.tigerff.community.service.CommentService;
import com.tigerff.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/21 23:23
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model)
    {
        QuestionDto questionDto=questionService.findQuestionById(id);
        //增加刷新页面，去增加浏览数
        questionService.incReadCount(id);
        //获取评论列表
        List<CommentDto> commentDtoList = commentService.getCommentList(id, CommentTypeEnum.QUESTION);
        //获取和问题相关的问题
        List<Question> questionTag=questionService.findQuestionByTag(id,questionDto.getTag());
        model.addAttribute("questionDto",questionDto);
        model.addAttribute("commentDtoList",commentDtoList);
        model.addAttribute("questionTag",questionTag);
        return "question";
    }
}
