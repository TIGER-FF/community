package com.tigerff.community.controller;

import com.tigerff.community.dto.PageInfo;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 15:52
 */
@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    // 加上搜索功能
    public String index(@RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "size",defaultValue = "7") int size,
                        Model model)
    {
        //这一块重复代码加在了 interceptor 中 2020.2.28
//        Cookie[] cookies = request.getCookies();
//        if (cookies!=null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    //更据 token 去数据库找出 user 信息，放在 session 中
//                    User user = userMapper.findUserByToken(cookie.getValue());
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                        break;
//                    }
//                }
//            }
//        }
        //每次访问主页，去看数据中提问，并展示到主页--分页--并且在返回结果中包含是谁提问的
        //提问人信息//
        PageInfo pageInfo=questionService.findQuestions(search,page,size);
        model.addAttribute("pageInfo",pageInfo);
        //添加搜索
        model.addAttribute("search",search);
        return "index";
    }
}
