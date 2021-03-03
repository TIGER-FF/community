package com.tigerff.community.dto;

import com.tigerff.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/1 16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String content;

    private Long parentId;   //这个是问题的id

    private Integer type;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtUpdate;

    private Integer likeCount;

    private Integer readCount;

    private User user;
}
