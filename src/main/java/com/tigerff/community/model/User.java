package com.tigerff.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String countId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtUpdate;
    private String avatarUrl;
}
