package com.tianshouzhi.auth.jdbc.security;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by tianshouzhi on 2018/1/22.
 */
@Configuration
public class DataSourceConfiguration {
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("shxx12151022");
        dataSource.setUrl("jdbc:mysql://localhost:3306/jdbc-auth?characterEncoding=UTF-8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
