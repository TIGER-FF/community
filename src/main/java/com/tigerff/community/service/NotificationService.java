package com.tigerff.community.service;

import com.tigerff.community.dto.NotificationDto;
import com.tigerff.community.dto.PageInfo;
import com.tigerff.community.dto.QuestionDto;
import com.tigerff.community.enums.CommentTypeEnum;
import com.tigerff.community.enums.NotificationEnum;
import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import com.tigerff.community.mapper.NotificationMapper;
import com.tigerff.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/5 22:52
 */
@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    public PageInfo<NotificationDto> findCurrentNotification(User user, int page, int size) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(user.getId());
        int totalNotification = (int)notificationMapper.countByExample(notificationExample);
        int totalPage=(int) Math.ceil(totalNotification *1.0/size);
        if(page<1)
            page=1;
        if(totalPage>0&&page>totalPage)
        {
            page=totalPage;
        }
        if(totalPage==0)
        {
            totalPage=1;
            page=totalPage;
        }
        if(size<=0)
            size=5;
        PageInfo<NotificationDto> pageInfo=new PageInfo<>();
        /**
         * page =1
         * size=5
         * size*(page-1)
         * limit 1,5
         */
        pageInfo.setCurrentPage(page);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(user.getId());
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(size * (page - 1), size));

        List<NotificationDto> notificationDtos = notifications.stream().map(notification -> {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            if(notification.getType()== CommentTypeEnum.QUESTION.getType())
                notificationDto.setTypeName(NotificationEnum.QUESTION_NOTIFICATION.getMessage());
            else
                notificationDto.setTypeName(NotificationEnum.COMMENT_NOTIFICATION.getMessage());
            return notificationDto;
        }).collect(Collectors.toList());

        pageInfo.getQuestionDtos().addAll(notificationDtos);
        pageInfo.setTotalPage(totalPage);
        //设置页面
        ArrayList<Integer> list=new ArrayList<>();
        list.add(page);
        for(int i=1;i<5;i++)
        {
            if(page-i>0)
                list.add(0,page-i);
            if(page+i<=totalPage)
                list.add(page+i);
        }
        pageInfo.setList(list);
        //判断 是否是第一页
        if(page==1)
            pageInfo.setFirstPage(false);
        else
            pageInfo.setFirstPage(true);
        //判断是否是最后一页
        if(page==totalPage)
            pageInfo.setEndPage(false);
        else
            pageInfo.setEndPage(true);
        //判断是否包含第一页
        if(list.contains(1))
            pageInfo.setShowFirstPage(false);
        else
            pageInfo.setShowFirstPage(true);
        //判断是否包含最后一页
        if(list.contains(totalPage))
            pageInfo.setShowEndPage(false);
        else
            pageInfo.setShowEndPage(true);
        return pageInfo;
    }
    //创建通知
    public void createNotification(Long notifier,
                                   String notifierName,
                                   Long outerId,
                                   String outerTitle,
                                   Integer type,
                                   Integer status,
                                   Long receiver)
    {
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(notifier);
        notification.setNotifierName(notifierName);
        notification.setOuterId(outerId);
        notification.setOuterTitle(outerTitle);
        notification.setReceiver(receiver);
        notification.setStatus(status);
        notification.setType(type);
        notificationMapper.insert(notification);
    }

    public Notification setRead(Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null)
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        // 1 表示已读
        notification.setStatus(1);
        notificationMapper.updateByPrimaryKey(notification);

        return notification;
    }

    /**
     * 获取接收人为 id 的未读数通知数
     * @param id 接收人的 id
     * @return
     */
    public Long getUnreadCount(Long id)
    {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(0);
        long unReadCount = notificationMapper.countByExample(notificationExample);
        return unReadCount;
    }
}
