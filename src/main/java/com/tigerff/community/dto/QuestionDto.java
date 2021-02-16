package com.tigerff.community.dto;

import com.tigerff.community.model.Question;
import com.tigerff.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/16 19:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    //创建者 id
    private Long creator;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtUpdate;
    private Integer readCount;
    private Integer watchCount;
    private Integer likeCount;
    private User user;
}
