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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/15 19:44
 */
@Slf4j
@Controller
public class PublishController {
    @Autowired
    UserMapper userMapper;
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
        if(title==null||title.trim()=="")
        {
            model.addAttribute("errorMessage"," title 不能为空");
            return "publish";
        }
        if(content==null||content.trim()=="")
        {
            model.addAttribute("errorMessage"," content 不能为空");
            return "publish";
        }
        if(tag==null||tag.trim()=="")
        {
            model.addAttribute("errorMessage"," tag 不能为空");
            return "publish";
        }
        User user=null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies)
        {
            if(cookie.getName().equals("token")) {
                //更据 token 去数据库找出 user 信息，放在 session 中
                 user= userMapper.findUserByToken(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        Question question = new Question();
        question.setCreator(user.getId());
        question.setContent(content);
        question.setTag(tag);
        question.setTitle(title);
        question.setGmtCreate(String.valueOf(System.currentTimeMillis()));
        question.setGmtUpdate(question.getGmtCreate());
        question.setReadCount(0);
        question.setWatchCount(0);
        question.setLikeCount(0);
        int insert = questionMapper.insertQuestion(question);
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
