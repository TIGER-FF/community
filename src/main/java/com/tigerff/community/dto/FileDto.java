package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/8 1:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Integer success;
    private String message;
    private String url;
}
