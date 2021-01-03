package com;

import com.docker.DockerExecutor;

import java.io.File;
import java.util.List;

public class DockerServer {
    private static DockerExecutor dockerExecutor =new DockerExecutor();
    private  final static String DOCKER_HOME="home";

    public String checkout(String sirName,String commitID,String bugId) {
        String cmd ="cd "+"/"+DOCKER_HOME+ "/"+"metadata"+"/"+sirName+";git checkout -f "+commitID;
        return dockerExecutor.run(cmd,bugId);
    }

    public void runPrintln(String cmd,String bugId) {
        dockerExecutor.runPrintln(cmd,bugId);
    }
    public String diffRunPrintln(String cmd,String bugId) {
        return dockerExecutor.diffRunPrintln(cmd,bugId);
    }
    public void runPrintln(String cmd, List<String> rootcause) {
        dockerExecutor.runPrintln(cmd,rootcause);
    }
    public String run(String cmd ,String bugId) {
        return dockerExecutor.run(cmd,bugId);
    }

    public void setEnviroment(String[] toolPaths) {
        dockerExecutor.setEnviroment(toolPaths);
    }

    public void runTest(String cmd,String bugId) {
        dockerExecutor.runTest(cmd,bugId);
    }
    public String runTestW(String cmd,String bugId) {
        return dockerExecutor.runTestW(cmd,bugId);
    }
//    public void endTest() {
//        String cmd ="rm -rf /home/rSwitch.lock";
//        dockerExecutor.run(cmd,bugId);
//    }
//    public String pullBug(String bugID) {
//        String sirName=DefectsDB.getSirName(bugID);
//        String cdCmd="cd "+File.separator+"home"+File.separator+"metadata"+File.separator+sirName;
//        return dockerExecutor.run(cdCmd+";git checkout master;git pull origin master");
//    }


}
