package com.tigerff.community.mapper;

import com.tigerff.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Select("select * from question where id!=#{id} and tag REGEXP #{tag}")
    List<Question> findQuestionByTag(@Param("id") Long id,@Param("tag") String tag);
    @Select("select count(*) from question where title REGEXP #{search}")
    int countByExample(@Param("search") String search);
    //搜索
    @Select("select * from question  where title REGEXP #{search} limit #{page},#{size}")
    List<Question> selectByExampleWithRowbounds(@Param("search") String search,@Param("page") int i, @Param("size") int size);
}
