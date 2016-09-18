package com.demo.web;

import com.demo.demo1.client.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by luoyong on 16-9-18.
 */
@Controller
@RequestMapping("/")
public class TestController {

    @Resource(name = "demo1Client")
    private Client client1;

    @Resource(name = "demo2Client")
    private com.demo.demo2.client.Client client2;

    @RequestMapping("/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        client1.startClient("1214");
        client2.startClient("1356");
        return  modelAndView;
    }
}
