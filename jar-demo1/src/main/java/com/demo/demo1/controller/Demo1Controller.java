package com.demo.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luoyong on 16-9-18.
 */
@Controller
@RequestMapping("/demo1")
public class Demo1Controller {

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "demo1.test";
    }
}
