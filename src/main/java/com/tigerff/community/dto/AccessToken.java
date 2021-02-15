package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 18:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
