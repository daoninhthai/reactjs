package com.nt.rookie.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("greeting")
    public String greeting(Map<String, Object> model){
        model.put("message", "Hello Thuong Nguyen");
        return "greeting";
    }
}
