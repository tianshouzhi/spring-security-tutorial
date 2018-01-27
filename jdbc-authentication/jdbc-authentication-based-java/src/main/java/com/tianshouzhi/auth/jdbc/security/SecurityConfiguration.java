package com.tianshouzhi.auth.jdbc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.sql.DataSource;

/**
 * Created by tianshouzhi on 2017/12/18.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()   //JdbcUserDetailsManagerConfigurer
                    .dataSource(dataSource)  //语法仅仅适用于HyperSQL DataBase  HSQLDB
//                    .withDefaultSchema() // spring-security-core模块 org.springframework.security.core.userdetails.jdbc包下的 users.ddl文件
                    .usersByUsernameQuery(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY)//默认
                    .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
                    .authoritiesByUsernameQuery(JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY)//默认
                    .rolePrefix("")//默认为空串：""
                    .groupAuthoritiesByUsername(JdbcDaoImpl.DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY)
                    .withUser(User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").roles("USER"))//默认创建的用户,重启又创建，会报错
                    .userCache(new NullUserCache());


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/bower_components/**")
                .antMatchers("/app/**")
                .antMatchers("/index.html")
                .antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   .anyRequest().authenticated()
                .and()
                  .csrf().disable()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .permitAll();

    }
}
