package com.servlet;

import com.DefectsAction;
import com.DefectsDB;
import com.alibaba.fastjson.JSON;
import com.vo.Bug;
import com.vo.SIR;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class GetBug{

    private static Integer global=0;

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

    @RequestMapping("/getDiffInfo")
    @CrossOrigin
    public String getDiff(String bugId) throws Exception{
        DefectsAction da = new DefectsAction();
        String diffInfo = da.diffInfo(bugId);
        return diffInfo;
    }

    @RequestMapping("/getTestBuggy")
    @CrossOrigin
    public String getTestBuggy(String bugId) throws Exception{
        global = 0;
        DefectsAction da = new DefectsAction();
        global = 20;
        String testBuggy = da.testBuggy(bugId);
        //global = 0;
        return testBuggy;
    }

    @RequestMapping("/getTestFix")
    @CrossOrigin
    public String getTestFix(String bugId) throws Exception{
        global = 0;
        DefectsAction da = new DefectsAction();
        global = 20;
        String testFix = da.testFix(bugId);
        return testFix;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello 1";
    }

    @RequestMapping("/testProgress")
    @CrossOrigin
    public String progress() throws IOException {
        if (global>=20&&global<95){
            global = global + 5;
        }
        return String.valueOf(global);
    }
}
