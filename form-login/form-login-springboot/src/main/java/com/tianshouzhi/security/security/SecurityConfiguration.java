package com.tianshouzhi.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * Created by tianshouzhi on 2017/12/18.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new JdbcUserDetailsManager())
                .withObjectPostProcessor(new ObjectPostProcessor<AbstractUserDetailsAuthenticationProvider>() {
                    @Override
                    public <O extends AbstractUserDetailsAuthenticationProvider> O postProcess(O object) {
                        object.setUserCache(new EhCacheBasedUserCache());
                        return object;
                    }
                })
                .and()
                .authenticationEventPublisher(new DefaultAuthenticationEventPublisher(eventPublisher))
                .inMemoryAuthentication()
                .withUser(User.withDefaultPasswordEncoder().username("tianshouzhi").password("tianshouzhi123").roles("USER"));
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserCache(new EhCacheBasedUserCache());
        provider.setUserDetailsService(new JdbcUserDetailsManager());
        return provider;
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
                    //通过successHandler取代successForwardUrl方法
                    .successHandler(new AjaxAuthenticationSuccessHandler())
                    //通过failureHandler取代failureForwardUrl方法
                    .failureHandler(new AjaxAuthenticationFailureHandler())
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint);

    }
}
