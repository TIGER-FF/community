package com.tigerff.community.exception;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 15:22
 */
public enum CustomizeErrorCode implements CustomizeErrorCodeInter{
    QUESTION_NOT_FOUND("你找的问题不存在或者已经被删除");

    @Override
    public String getMessage() {
        return message;
    }
    private String message;
    CustomizeErrorCode(String message)
    {
        this.message=message;
    }
}
