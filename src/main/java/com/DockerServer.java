package com;

import com.docker.DockerExecutor;

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

    public String run(String cmd ,String bugId) {
        return dockerExecutor.run(cmd,bugId);
    }

    public void setEnviroment(String[] toolPaths) {
        dockerExecutor.setEnviroment(toolPaths);
    }

    public void runTest(String cmd,String bugId,String version) {
        dockerExecutor.runTest(cmd,bugId);
        // System.out.println("执行查看命令！");
        // dockerExecutor.readTxt(bugId,version);
    }

    public String runTestW(String cmd,String bugId,String version) {
        return dockerExecutor.runTestW(cmd, bugId);
    }

    // 运行支持的工具
    public String runTool(String cmd, String bugId){
        return dockerExecutor.runTool(cmd, bugId);
    }


}
