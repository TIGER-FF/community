package com.tigerff.community.exception;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 15:08
 */
//定义这个接口，就是为了以后传入错误的类型时候只需要传入这个接口就行了
public interface CustomizeErrorCodeInter {
    String getMessage();
    Integer getCode();
}
