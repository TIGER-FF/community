package com.tigerff.community.controller;

import com.tigerff.community.mapper.QuestionMapper;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.Question;
import com.tigerff.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/15 19:44
 */
@Slf4j
@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish()
    {
        return "publish";
    }

    //将发布页面的表单提交到数据库
    @PostMapping("/publish")
    public String doPublish(Model model,
                            HttpServletRequest request,
                            @RequestParam(name = "title") String title,
                            @RequestParam(name = "content") String content,
                            @RequestParam(name = "tag") String tag)
    {
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("tag",tag);
        if(title==null||"".equals(title.trim()))
        {
            model.addAttribute("errorMessage"," title 不能为空");
            return "publish";
        }
        if(content==null||"".equals(content.trim()))
        {
            model.addAttribute("errorMessage"," content 不能为空");
            return "publish";
        }
        if(tag==null||"".equals(tag.trim()))
        {
            model.addAttribute("errorMessage"," tag 不能为空");
            return "publish";
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Question question = new Question();
        question.setCreator(user.getId());
        question.setContent(content);
        question.setTag(tag);
        question.setTitle(title);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtUpdate(question.getGmtCreate());
        question.setReadCount(0);
        question.setWatchCount(0);
        question.setLikeCount(0);
        int insert = questionMapper.insert(question);
        if(insert==1) {
            log.info("\n插入数据成功\n" + question);
            return "redirect:/";
        }
        else
            log.error("\n插入数据失败\n"+question);
        //发布成功返回到主页面，失败回显刚才的输入，并提示错误信息

        return "publish";
    }
}
