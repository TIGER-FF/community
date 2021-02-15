package com.tigerff.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 15:52
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model)
    {
        //model.addAttribute("msg","tiger");
        return "index";
    }
}
