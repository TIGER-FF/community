package com.tigerff.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/16 19:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private Integer currentPage;
    private Integer totalPage;
    private ArrayList<QuestionDto> questionDtos=new ArrayList<>();
    //页码
    private ArrayList<Integer> list=new ArrayList<>();
    //判断是否是否是第一页
    private boolean isFirstPage;
    private boolean isEndPage;
    private boolean isShowFirstPage;
    private boolean isShowEndPage;
}
