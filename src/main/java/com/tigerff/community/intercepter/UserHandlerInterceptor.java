package com.tigerff.community.intercepter;

import com.tigerff.community.mapper.UserMapper;
import com.tigerff.community.model.User;
import com.tigerff.community.model.UserExample;
import com.tigerff.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/18 23:18
 */
@Component
public class UserHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //更据 token 去数据库找出 user 信息，放在 session 中
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(cookie.getValue());
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.getUnreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unReadCount", unreadCount);
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
