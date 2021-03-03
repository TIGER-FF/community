package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/27 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//用来接收后台传来评论的数据封装
public class CommentReadDto {
    private Long parentId;
    private String comment;
    private Integer type;

}
