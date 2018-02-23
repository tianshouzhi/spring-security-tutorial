package com.tianshouzhi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tianshouzhi on 2017/12/21.
 */
@RestController
public class UserController {
    public UserController() {
        System.out.println("UserController.UserController");
    }

    @GetMapping("/user")
    public Object user(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return principal;
    }
}
