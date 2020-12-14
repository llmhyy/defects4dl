package com.servlet;

import com.DefectsDB;
import com.alibaba.fastjson.JSON;
import com.vo.SIR;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GetBug{

    @RequestMapping("/getBug")
    @CrossOrigin
    //public Map<String,String> getBug(){
    public String getBug(){
        DefectsDB db = new DefectsDB();
        //Map<String, String> bugInfo = db.initDB();
        List<SIR> bugInfo = db.initDB();


        String bugInfoJs = JSON.toJSONString(bugInfo);
        System.out.println(bugInfoJs);

        return bugInfoJs;

    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello 1";
    }
}
