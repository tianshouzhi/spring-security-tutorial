package com.tianshouzhi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("tianshouzhi").password("tianshouzhi123").roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
