package com.tigerff.community.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 22:44
 */
@Configuration
public class JDBCConfig {
    @Bean
    public DataSource getDataSource()
    {
        DataSourceBuilder dataSourceBuilder=DataSourceBuilder.create();
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("321123ww");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/community?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        return dataSourceBuilder.build();
    }
}
