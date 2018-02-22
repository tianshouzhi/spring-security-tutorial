package com.tianshouzhi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("tianshouzhi")
                                .password("tianshouzhi123")
                                .roles("USER"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/{bower_components,app}/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                     .antMatchers("/index.html").permitAll()

                     .anyRequest().authenticated()
                .and()
                   .csrf().disable()
                .formLogin()
                    //不需要指定loginPage
                    //.loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    //通过successHandler取代successForwardUrl方法
                    .successHandler(new AjaxAuthenticationSuccessHandler())
                    //通过failureHandler取代failureForwardUrl方法
                    .failureHandler(new AjaxAuthenticationFailureHandler())
                    .permitAll();
    }
}
