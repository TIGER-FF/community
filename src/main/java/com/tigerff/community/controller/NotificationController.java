package com.tigerff.community.controller;

import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import com.tigerff.community.mapper.CommentExMapper;
import com.tigerff.community.mapper.CommentMapper;
import com.tigerff.community.model.Comment;
import com.tigerff.community.model.CommentExample;
import com.tigerff.community.model.Notification;
import com.tigerff.community.model.User;
import com.tigerff.community.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/6 0:28
 */
@Slf4j
@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    CommentMapper commentMapper;
    // 当点击了通知，进行跳转，跳转时候将 notification 的 status 设置为 1 表示已经读了
    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
            throw new CustomizeException(CustomizeErrorCode.NOT_LOGIN);
        //将这个通知置为 已读
        Notification notification =notificationService.setRead(id);

        Integer type = notification.getType();
        if(notification.getStatus()==1)
        {
            //如果这块的 type 为 1 表示为是 问题则直接返回到问题的详情页
            Long questionId=notification.getOuterId();
            //如果是评论类型 2
            if(type== CommentTypeEnum.COMMENT.getType())
            {
                CommentExample example = new CommentExample();
                example.createCriteria()
                        .andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
                Comment comment = commentMapper.selectByExample(example).get(0);
                questionId=comment.getParentId();
            }
            log.info("已经成功将第"+id+"条通知设置为已读");
            return "redirect:/question/"+questionId;
        }
        else {
            log.error("设置第" + id + "条通知设置为已读时候出现异常");
            return "redirect:/";
        }
    }
}
