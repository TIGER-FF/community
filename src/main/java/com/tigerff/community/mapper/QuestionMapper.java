package com.tigerff.community.mapper;

import com.tigerff.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/15 20:14
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question values(null,#{creator},#{title},#{content},#{tag},#{gmtCreate},#{gmtUpdate},#{readCount},#{watchCount},#{likeCount})")
    int insertQuestion(Question question);
}
