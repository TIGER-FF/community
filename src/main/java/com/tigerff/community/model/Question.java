package com.tigerff.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/15 20:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private Long id;
    //创建者 id
    private Long creator;
    private String title;
    private String content;
    private String tag;
    private String gmtCreate;
    private String gmtUpdate;
    private Integer readCount;
    private Integer watchCount;
    private Integer likeCount;
}

