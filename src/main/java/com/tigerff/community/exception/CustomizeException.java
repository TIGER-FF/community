package com.tigerff.community.exception;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 14:58
 */
//这一块继承 RuntimeException 是为了后面用时候，就不用 try catch 了，会直接跳出
public class CustomizeException extends RuntimeException{
    //错误信息
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCodeInter errorCode)
    {
        this.message=errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
