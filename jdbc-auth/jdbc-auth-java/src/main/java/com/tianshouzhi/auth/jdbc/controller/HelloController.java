package com.tianshouzhi.auth.jdbc.controller;

import org.springframework.security.access.annotation.Secured;

/**
 * Created by tianshouzhi on 2018/1/27.
 */
//@RestController
public class HelloController {

  @Secured("hasRole('user')")
  public String hello(){
      return "hello";
  }
}
