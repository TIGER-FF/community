package com.tigerff.community.dto;

import com.tigerff.community.exception.CustomizeErrorCode;
import com.tigerff.community.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/27 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//自定义的结果对象
public class ResponseDto<T> {
    private Integer code;
    private String message;
    private T data;
    ResponseDto(Integer code,String message)
    {
        this.code=code;
        this.message=message;
        this.data=null;
    }
    public static ResponseDto errorOf(Integer code,String message)
    {
        return new ResponseDto(code,message);
    }
    public static ResponseDto errorOf(CustomizeErrorCode errorCode)
    {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }
    public  static  ResponseDto okOf()
    {
        return new ResponseDto(200,"请求成功");
    }
    public  static  <T> ResponseDto okOf(T data)
    {
        return new ResponseDto(200,"请求成功",data);
    }

    public static ResponseDto errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }
}
