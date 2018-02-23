package com.tianshouzhi.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.*;

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
