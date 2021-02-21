package com.tigerff.community.controller;

import com.tigerff.community.dto.QuestionDto;
import com.tigerff.community.model.User;
import com.tigerff.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/21 23:23
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model)
    {
        QuestionDto questionDto=questionService.findQuestionById(id);
        model.addAttribute("questionDto",questionDto);
        return "question";
    }
}
