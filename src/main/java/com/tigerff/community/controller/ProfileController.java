package com.tigerff.community.controller;

import com.tigerff.community.dto.PageInfo;
import com.tigerff.community.model.User;
import com.tigerff.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/16 21:56
 */
@Controller
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @GetMapping("/profile/{action}")
    public String myQuestion(@PathVariable(name = "action") String action,
                             @RequestParam(name = "page",defaultValue = "1") int page,
                             @RequestParam(name = "size",defaultValue = "5") int size,
                             HttpServletRequest request,
                             Model model)
    {
        /**
         * 先去判断 session 中是否存在 user 对象，存在即证明登录，不存在，则返回到首页
         */
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null)
            return "redirect:/";
        //存在，则去查找当前用户的提问

        if ("question".equals(action)) {
            model.addAttribute("section","question");
            PageInfo pageInfo=profileService.findCurrentQuestion(user,page,size);
            model.addAttribute("pageInfo",pageInfo);
            return "profile";
        }
        else
            return "redirect:/";
    }
}
