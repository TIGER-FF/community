package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/5 21:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id; //
    private Long notifier;//谁通知的 id
    private String notifierName;//谁通知的的 姓名
    private Long outerId; //通知的问题或者评论的 id
    private String outerTitle;// 通知的问题或者评论的内容
    private Integer type; // 通知的是问题还是评论
    private String typeName; // 获取他枚举类型的值
    private Long gmtCreate; //通知的时间
    private Integer status; //这个通知是已读还是未读的
}
