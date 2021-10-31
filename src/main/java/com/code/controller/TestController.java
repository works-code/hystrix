package com.code.controller;

import com.code.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    public HystrixService hystrixService;

    @RequestMapping(value = "/")
    public String test(@RequestParam Boolean errYn) throws Exception{
        return hystrixService.A(errYn);
    }

}
