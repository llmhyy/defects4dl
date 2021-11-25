package com.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class Forward {

    @RequestMapping("/bugList")
    public String bugList(){
        return "bug.html";
    }

    @RequestMapping("/bugDetail")
    public String bugDetail(){
        return "bug_detail.html";
    }

    @RequestMapping("/showDiff")
    public String codeDiff(){
        return "showDiff.html";
    }
    @RequestMapping("/diff")
    public String codeDiff1(){
        return "diff.html";
    }
    @RequestMapping("/javadoc")
    public String javadoc(){
        return "index.html";
    }
}
