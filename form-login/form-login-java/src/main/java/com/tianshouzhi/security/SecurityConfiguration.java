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
				.withUser(User.withDefaultPasswordEncoder()
						.username("tianshouzhi")
						.password("tianshouzhi123")
						.roles("USER"));
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//访问静态资源不需要安全保护
		web.ignoring()
				.antMatchers("/bower_components/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 访问index.html不要权限验证
              .antMatchers("/index.html").permitAll()
				// 其他所有路径都需要权限校验
		      .anyRequest().authenticated()
				// csrf默认开启，这里先显式关闭
		      .and().csrf().disable()
				// 配置表单登录
		      .formLogin()
				  // 表单登录页面地址
		     	 .loginPage("/login.html")
				  // form表单POST请求url提交地址，默认为/login
		     	 .loginProcessingUrl("/login")
				  // form表单用户名参数名
		     	 .passwordParameter("password")
				  // form表单密码参数名
		     	 .usernameParameter("username")
				  // 登录成功跳转地址
//		     	 .successForwardUrl("/success.html")
//				  // 登录失败跳转地址
//		     	 .failureForwardUrl("/error.html")
				  // 允许所有用户都有权限访问登录页面
				 .permitAll();
	}
}
