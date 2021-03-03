package com.tigerff.community.advice;

import com.tigerff.community.dto.ResponseDto;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 13:33
 */
@ControllerAdvice//这一块是全局异常处理
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    //这一块我们需要返会时候给页面带上信息，而不是 json 数据，所以返回类型 改为 ModelAndView
    Object handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType))
        {
            //这一块用来判断是否采用了 ajax 技术，采用了返回的的 contentType 是 application/json
            if(ex instanceof CustomizeException)
            {
                return ResponseDto.errorOf((CustomizeException)ex);
            }else
                return ResponseDto.errorOf(CustomizeErrorCode.SYS_ERROR);
        }
        else {// text/html
            //这一块来判断错误类型进行输出不同的提示信息
            if(ex instanceof CustomizeException)
            {
                //获取到自定义错误的提醒信息
                model.addAttribute("message",ex.getMessage());
            }
            else
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            //返回到 error.html 页面
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
