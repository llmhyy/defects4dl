package com.servlet;

import com.DefectsAction;
import com.DefectsDB;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vo.Bug;
import com.vo.SIR;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GetBug{

    @RequestMapping("/getBug")
    @CrossOrigin
    public String getBug(){
        DefectsDB db = new DefectsDB();
        List<SIR> bugInfo = db.initDB();
        String bugInfoJs = JSON.toJSONString(bugInfo);
        return bugInfoJs;
    }


    @RequestMapping("/getBugInfo")
    @CrossOrigin
    public String getBugInfo(String bugID) throws Exception {
        DefectsAction da = new DefectsAction();
        Bug bugInfoD = da.bugDetail(bugID);
        String bugInfoDe = JSON.toJSONString(bugInfoD);
        return bugInfoDe;
    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello 1";
    }
}
