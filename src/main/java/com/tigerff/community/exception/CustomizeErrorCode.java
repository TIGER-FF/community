package com.tigerff.community.exception;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 15:22
 */
public enum CustomizeErrorCode implements CustomizeErrorCodeInter{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在或者已经被删除"),
    TARGET_NOT_FOUND(2002,"没有选中任何问题"),
    NOT_LOGIN(2003,"当前用户没有登录"),
    SYS_ERROR(2004,"换个姿势再来一次"),
    COMMENT_TYPE_ERROR(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"评论类型错误或者不存在"),
    COMMENT_IS_EMPTY(2006,"评论类不能为空")
    ;



    private Integer code;
    private String message;
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
