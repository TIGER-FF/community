package com.tigerff.community.mapper;

import com.tigerff.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 22:37
 */
@Mapper
public interface UserMapper {
    /**
     * 保存登录的用户数据基本情况和 token
     * mybatis 中 sql 语句中 #{} 会在预处理时候处理为 ？ 占位符
     *
     * mybatis 中 sql 语句中 ${} 会在预处理时时候会直接讲表示的字符串进行 替换
     *
     * 一般用 #{} 安全
     *
     * @param user
     * @return
     */
    @Insert("insert into user values(null,#{countId},#{name},#{token},#{gmtCreate},#{gmtUpdate})")
    int insertUser(User user);
}
