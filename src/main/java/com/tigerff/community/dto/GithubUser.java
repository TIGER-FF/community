package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 21:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private Long id;
    private String name;
    private String bio;
}
