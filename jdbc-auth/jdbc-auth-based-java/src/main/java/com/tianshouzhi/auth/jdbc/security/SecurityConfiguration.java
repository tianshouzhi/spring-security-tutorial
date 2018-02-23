package com.tianshouzhi.auth.jdbc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.sql.DataSource;

/**
 * Created by tianshouzhi on 2017/12/18.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
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
                    ////默认创建的用户, 注意每次重启都会创建，因此可能会出错
//                    .withUser(User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").roles("USER"))
                    .userCache(new NullUserCache());
        //默认加上前缀ROLE
        //roles 等价于使用SimpleGrantedAuthority
//        User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").roles("USER");
//        User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").authorities(new )

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
                .antMatchers("/users/**").hasAuthority("ROLE_USER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .and()
                  .csrf().disable()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .permitAll();

    }
}
