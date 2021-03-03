package com.tigerff.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/2 1:27
 */
@Mapper
public interface CommentExMapper {
    //增加回复数
    @Update("update comment set read_count=read_count+1 where id=#{id}")
    void incReadCount(@Param("id") Long id);
}
