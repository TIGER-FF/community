package com.tigerff.community.controller;

import com.tigerff.community.dto.AccessToken;
import com.tigerff.community.dto.GithubUser;
import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.User;
import com.tigerff.community.model.UserExample;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        //connection_opts: { request: { timeout: 300 }
        Map<String,Integer> map2=new HashMap<>();
        map2.put("timeout",300000);
        Map<String,Map<String,Integer>> map1=new HashMap<>();
        map1.put("request",map2);
        accessToken.setConnection_opts(map1);
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

            //判断是否数据中已经存在了这个人
            UserExample example = new UserExample();
            example.createCriteria().andCountIdEqualTo(String.valueOf(githubUser.getId()));
            final List<User> users = userMapper.selectByExample(example);
            //存在，则更新信息，不存在则去插入信息
            if(users.size()==0)
            {//不存在
                User user = new User();
                user.setCountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                String token = UUID.randomUUID().toString();
                //在 cookie 中进行存储
                response.addCookie(new Cookie("token",token));
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtUpdate(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                int insert = userMapper.insert(user);
                if(insert==1)
                    log.info("插入数据成功"+user);
                else
                    log.info("插入数据失败");
                //登录成功，重定向到 index 页面
            }
            else
            {
                //存在
                User updateUser=new User();
                updateUser.setName(githubUser.getName());
                String token = UUID.randomUUID().toString();
                //在 cookie 中进行存储
                response.addCookie(new Cookie("token",token));
                updateUser.setToken(token);
                updateUser.setGmtUpdate(System.currentTimeMillis());
                updateUser.setAvatarUrl(githubUser.getAvatarUrl());
                UserExample userExample = new UserExample();
                userExample.createCriteria()
                        .andIdEqualTo(users.get(0).getId());
                int update = userMapper.updateByExampleSelective(updateUser, userExample);
                if(update==1)
                    log.info("更新数据成功"+updateUser);
                else
                    log.info("更新数据失败");
                //登录成功，重定向到 index 页面
            }
            return "redirect:/";
        }else
            //返回到 index 重新登录
            return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response)
    {
        //退出登录，就是移除 session 中的对象，并删除 cookie
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        //删除 cookie 就是将这个对象设置为 null 并将最大存活时间设置为 0
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
