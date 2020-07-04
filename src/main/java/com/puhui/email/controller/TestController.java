package com.puhui.email.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 邹玉玺
 * @date: 2020/7/4-10:02
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }
}
