package com.tigerff.community.controller;

import com.tigerff.community.dto.AccessToken;
import com.tigerff.community.dto.GithubUser;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.User;
import com.tigerff.community.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 18:32
 */
@Slf4j
@Controller
public class OAuthController {
    @Autowired
    HttpUtils httpUtils;
    //下面的配置，放在了 properties 中通过 @Value 注解进行注入
    @Value("${OAuth.client_id}")
    private String client_id;
    @Value("${OAuth.client_secret}")
    private String client_secret;
    @Value("${OAuth.redirect_uri}")
    private String redirect_uri;
    @Value("${OAuth.url}")
    private String url;

    @Autowired
    UserMapper userMapper;
    /**
     * 处理 OAuth 的 callback 请求
     * @return String 成功返回到 index 页面，不成功则重新登录
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(client_id);
        accessToken.setClient_secret(client_secret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirect_uri);
        accessToken.setState(state);
        //发送 post 请求到 OAuth 获取到 accessToken
        String responseStr = httpUtils.getAccessToken(accessToken);
        //根据是否获取到 access_token 判断是否登录成功
        responseStr=responseStr.split("&")[0].split("=")[1];
        //根据得到的 accessToken 利用 get 请求获取个人信息
        GithubUser githubUser = httpUtils.getPersonInfo(url, responseStr);
        if(githubUser!=null)
        {
            log.info("登录成功");
            //讲信息存储在 session 中进行长久保存  ---但是不能在服务器重启时候，让其刷新还在线，现在
            //实现就是讲生成的 token(UUID) 存储在 cookie 中根据 cookie 进行判断是否之前登录过
//            HttpSession session = request.getSession();
//            session.setAttribute("user",githubUser);   放在了 indexController 页面那块

            //在 mysql 中存储
            User user = new User();
            user.setCountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            //在 cookie 中进行存储
            response.addCookie(new Cookie("token",token));
            user.setToken(token);
            user.setGmtCreate(String.valueOf(System.currentTimeMillis()));
            user.setGmtUpdate(user.getGmtCreate());
            int insert = userMapper.insertUser(user);
            if(insert==1)
                log.info("插入数据成功"+user);
            else
                log.info("插入数据失败");
            //登录成功，重定向到 index 页面
            return "redirect:/";
        }else
            //返回到 index 重新登录
            return "index";


    }
}
