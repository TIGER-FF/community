package com.tigerff.community.enums;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/5 21:34
 */
public enum NotificationEnum {
    COMMENT_NOTIFICATION(1,"回复了你的评论"),
    QUESTION_NOTIFICATION(2,"回复了你的问题");
    private Integer type;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getType() {
        return type;
    }

    NotificationEnum(int type, String message) {
        this.type=type;
        this.message=message;
    }
}
