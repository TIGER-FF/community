package com.tigerff.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 15:57
 *
 *
 * 处理错误的访问地址的处理器
 */
@Controller
//如果没有这个类的话，默认加载的处理的类为 BasicErrorController ,当这个列生效了，这个 BasicErrorController 就不会生效了
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
    //这个 copy 来自 BasicErrorController 当要返回的是 text/html 时候，就会执行
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus status = getStatus(request);
        //根据返回的状态码，去输出不同的错误信息
        if(status.is4xxClientError())
        {
            model.addAttribute("message","亲，请求错误了，再看看");
        }
        else if(status.is5xxServerError())
        {
            model.addAttribute("message","服务器过热了，程序员哥哥正在降温");
        }
        //返回错误页面
        return new ModelAndView("error");
    }
    //也是 copy 来自BasicErrorController 类中的方法
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
