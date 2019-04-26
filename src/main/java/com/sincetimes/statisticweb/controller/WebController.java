package com.sincetimes.statisticweb.controller;


import com.sincetimes.statisticweb.model.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Controller
public class WebController implements WebMvcConfigurer {


    @GetMapping("/")
    public String gate() {
        return "index";
    }


    @GetMapping("/test")
    @ResponseBody
    public String testGet() {
        return "test get succeed!";
    }

    @PostMapping("/test")
    @ResponseBody
    public String testPost(@RequestBody Test newTest) {
        return "test psot succeed!"+newTest.toString();
    }

}
