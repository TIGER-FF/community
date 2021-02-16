package com.tigerff.community.mapper;

import com.tigerff.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/15 20:14
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question values(null,#{creator},#{title},#{content},#{tag},#{gmtCreate},#{gmtUpdate},#{readCount},#{watchCount},#{likeCount})")
    int insertQuestion(Question question);
    @Select("select * from question limit #{page},#{size}")
    List<Question> findQuestions(@Param(value = "page") int page, @Param(value = "size") int size);
    @Select("select count(1) from question")
    Integer getCount();
    @Select("select count(1) from question where creator=#{id}")
    Integer getCurrentCount(@Param("id") Long id);
    @Select("select * from question  where creator=#{creator}  limit #{page},#{size}")
    List<Question> findCurrentQuestions(@Param("creator") Long id, @Param("page") int page, @Param("size") int size);
}
