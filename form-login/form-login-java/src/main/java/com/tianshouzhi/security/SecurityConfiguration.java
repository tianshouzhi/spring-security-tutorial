package com.tianshouzhi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

/**
 * Created by tianshouzhi on 2017/12/18.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").roles("USER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html").permitAll()//访问index.html不要权限验证
                .anyRequest().authenticated()//其他所有路径都需要权限校验
                .and()
                .csrf().disable()//默认开启，这里先显式关闭
                .formLogin()  //内部注册 UsernamePasswordAuthenticationFilter
                .loginPage("/login.html") //表单登录页面地址
                .loginProcessingUrl("/login")//form表单POST请求url提交地址，默认为/login
                .passwordParameter("password")//form表单用户名参数名
                .usernameParameter("username") //form表单密码参数名
                .successForwardUrl("/success.html")  //登录成功跳转地址
                .failureForwardUrl("/error.html") //登录失败跳转地址
                //.defaultSuccessUrl()//如果用户没有访问受保护的页面，默认跳转到页面
                //.failureUrl()
                //.failureHandler(AuthenticationFailureHandler)
                //.successHandler(AuthenticationSuccessHandler)
                //.failureUrl("/login?error")
                .permitAll();//允许所有用户都有权限访问登录页面
    }
}
