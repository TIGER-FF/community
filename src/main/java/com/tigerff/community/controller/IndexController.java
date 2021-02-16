package com.tigerff.community.controller;

import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 15:52
 */
@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model)
    {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies)
        {
            if(cookie.getName().equals("token")) {
                //更据 token 去数据库找出 user 信息，放在 session 中
                User user = userMapper.findUserByToken(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    return "index";
                }
            }
        }
        return "index";
    }
}
