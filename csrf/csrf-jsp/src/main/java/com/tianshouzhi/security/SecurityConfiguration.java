package com.tianshouzhi.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

/**
 * Created by tianshouzhi on 2018/2/26.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 默认采用的实现通过LazyCsrfTokenRepository委派给HttpSessionCsrfTokenRepository处理
		LazyCsrfTokenRepository csrfTokenRepository = new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository());

		http

		      .csrf()// 此方法返回CsrfConfigurer，用于配置CsrfFilter
		      .csrfTokenRepository(csrfTokenRepository) // 指定CsrfTokenRepository
		      .ignoringAntMatchers("/xx"); // 指定不需要进行CSRF防御的资源URL
		// .requireCsrfProtectionMatcher() //指定RequestMatcher

	}
}
