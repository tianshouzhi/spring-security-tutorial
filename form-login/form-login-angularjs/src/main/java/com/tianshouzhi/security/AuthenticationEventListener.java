package com.tianshouzhi.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by tianshouzhi on 2018/1/21.
 */
@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        System.out.println(event);
        //监听认证成功事件
        if(event instanceof AuthenticationSuccessEvent){
            // ....
            return;
        }
        //监听认证失败事件
        if(event instanceof AbstractAuthenticationFailureEvent){
            // ....
        }
    }
}
