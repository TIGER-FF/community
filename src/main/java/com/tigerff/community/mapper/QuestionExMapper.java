package com.tigerff.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/25 17:17
 */
@Mapper
public interface QuestionExMapper {
    //增加浏览数
    @Update("update question set watch_count=watch_count+1 where id=#{id}")
    void incWatchCount(@Param("id") Long id);
    //增加回复数
    @Update("update question set read_count=read_count+1 where id=#{id}")
    void incReadCount(@Param("id") Long id);

}
