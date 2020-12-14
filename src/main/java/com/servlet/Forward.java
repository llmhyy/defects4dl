package com.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class Forward {

    @RequestMapping("/bugList")
    public String bugList(){
        return "bug";
    }

    @RequestMapping("/bugDetail")
    public String bugDetail(){
        return "bug_detail";
    }
}
