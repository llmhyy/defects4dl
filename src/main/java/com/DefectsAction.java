package com;

import JavaBasedConfig.Configs;
import com.docker.DockerExecutor;
import com.executor.Execute;
import com.utils.CodeUtils;
import com.vo.Bug;
import com.vo.BuggyVersion;
import com.vo.FixVersion;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class DefectsAction {
    public static Execute exec = new Execute();
    DockerServer dockerServer =new DockerServer();

    public String[] ls() {
        return DefectsDB.bugIDsToArray();
    }

    public String[] ls(String pattern) {
        String[] tmp = DefectsDB.bugIDsToArray();
        List<String> res = new ArrayList();
        for (int i = 0; i < tmp.length; i++) {
            String re = tmp[i];
            if (re.contains(pattern)) {
                res.add(re);
            }
        }
        return res.toArray(new String[res.size()]);
    }


    public String info(String bugId) throws Exception {
        Bug bug = DefectsDB.getBug(bugId);
        String errorMessage = bug.getErrorMessage();
        String describe = bug.getDescribe();
        String rootCause = bug.getRootCause();

        // String SIRName = DefectsDB.getSirName(bugId).toLowerCase();
        String SIRName = DefectsDB.getSirName(bugId);

        StringBuilder sb = new StringBuilder("---------bugInfo----------- \n");

        sb.append("BugID: ").append(bugId).append("\n");
        sb.append("ErrorMessage: ").append(errorMessage).append("\n");
        sb.append("Describe: ").append(describe).append("\n");
        sb.append("ROOTCAUSE:").append(rootCause).append("\n");

        sb.append("INFO DETAIL:\n");
        String cdCmd="cd /home/metadata/"+SIRName;

        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggytestCmd = buggyVersion.getBuggytestCmd();
        String buggycommit = buggyVersion.getBuggycommit();
        String commitTimeBuggy = dockerServer.run(cdCmd+";git log --pretty=format:“%cd” " + buggycommit + " -1",bugId);
        sb.append("BuggyVersion").append(" ").append(buggycommit).append(" ").append(commitTimeBuggy)
                .append("\n");

        FixVersion fixVersion = bug.getFixVersion();
        String fixtestCmd = fixVersion.getFixtestCmd();
        String fixcommit = fixVersion.getFixcommit();
        String commitTimeFix = dockerServer.run(cdCmd+";git log --pretty=format:“%cd” " + fixcommit + " -1",bugId);
        sb.append("FixVersion").append(" ").append(fixcommit).append(" ").append(commitTimeFix)
                .append("\n");

        //String diffCount = dockerServer.run(cdCmd+";git rev-list " + buggycommit + ".." + fixcommit + " --count");
        //sb.append("Commits count between BuggyVersion and FixVersion:").append(diffCount);

        String localMark = localMark(bugId);
        sb.append("Positioning score:").append(localMark).append("\n");
        String stringLength = stringLength(bugId);
        sb.append("Fix string length:").append(stringLength).append("\n");
        return sb.toString();

    }

    public Bug bugDetail(String bugId) throws Exception {

        Bug bug = DefectsDB.getBug(bugId);
        String errorMessage = bug.getErrorMessage();
        String describe = bug.getDescribe();
        String rootCause = bug.getRootCause();
        String type = bug.getType();
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggytestCmd = buggyVersion.getBuggytestCmd();
        String buggycommit = buggyVersion.getBuggycommit();
        BuggyVersion BuggyVersion = new BuggyVersion(buggytestCmd,buggycommit);

        FixVersion fixVersion = bug.getFixVersion();
        String fixtestCmd = fixVersion.getFixtestCmd();
        String fixcommit = fixVersion.getFixcommit();
        FixVersion FixVersion = new FixVersion(fixtestCmd,fixcommit);

        Bug Bug = new Bug(bugId, errorMessage,describe,rootCause,type,BuggyVersion,FixVersion);

        return Bug;

    }

    public void test(String bugId, String version) throws Exception {
        Bug bug = DefectsDB.getBug(bugId);
        if (version.equals("buggy")){
            BuggyVersion buggyVersion = bug.getBuggyVersion();
            String buggytestCmd = buggyVersion.getBuggytestCmd();
            String buggycommit = buggyVersion.getBuggycommit();
            String sirName=DefectsDB.getSirName(bugId);
            //System.out.println(dockerServer.checkout(sirName, buggycommit,bugId));
            dockerServer.runTest(buggytestCmd,bugId,version);
            //dockerServer.endTest();
        }
        else if (version.equals("fix")){
            FixVersion fixVersion = bug.getFixVersion();
            String fixtestCmd = fixVersion.getFixtestCmd();
            String fixcommit = fixVersion.getFixcommit();
            String sirName=DefectsDB.getSirName(bugId);
            //System.out.println(dockerServer.checkout(sirName, fixcommit,bugId));
            dockerServer.runTest(fixtestCmd,bugId,version);
            //dockerServer.endTest();
        }else {
            System.out.println("Input is wrong!");
        }
    }

    public String testBuggy(String bugId) throws Exception{
        String version = "buggy";
        Bug bug = DefectsDB.getBug(bugId);
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggytestCmd = buggyVersion.getBuggytestCmd();
        String buggycommit = buggyVersion.getBuggycommit();
        String sirName=DefectsDB.getSirName(bugId);
        return dockerServer.runTestW(buggytestCmd,bugId,version);
    }
    public String testFix(String bugId) throws Exception{
        String version = "fix";
        Bug bug = DefectsDB.getBug(bugId);
        FixVersion fixVersion = bug.getFixVersion();
        String fixtestCmd = fixVersion.getFixtestCmd();
        String fixcommit = fixVersion.getFixcommit();
        String sirName=DefectsDB.getSirName(bugId);
        return dockerServer.runTestW(fixtestCmd,bugId,version);
    }

    public String diff(String bugId) throws Exception {

        Bug bug = DefectsDB.getBug(bugId);
        String rootCause = bug.getRootCause();
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggyCommit = buggyVersion.getBuggycommit();

        FixVersion fixVersion = bug.getFixVersion();
        String fixCommit = fixVersion.getFixcommit();

        // String SIRName = DefectsDB.getSirName(bugId).toLowerCase();
        String SIRName = DefectsDB.getSirName(bugId);
        String cdCmd="cd "+"/"+"home"+"/"+"metadata"+"/"+SIRName;

        System.out.println("-----------Diff--------------\nCHANGE FILES:");
        dockerServer.runPrintln(cdCmd+";git diff " + buggyCommit + " " + fixCommit + " --stat",bugId);
        System.out.println("CHANGE DETAILS:");

        dockerServer.runPrintln(cdCmd+";git diff " + buggyCommit + " " + fixCommit,bugId);
        return "";
    }

    public String diffInfo(String bugId) throws Exception {

        Bug bug = DefectsDB.getBug(bugId);
        String rootCause = bug.getRootCause();
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggyCommit = buggyVersion.getBuggycommit();
        FixVersion fixVersion = bug.getFixVersion();
        String fixCommit = fixVersion.getFixcommit();
        // String SIRName = DefectsDB.getSirName(bugId).toLowerCase();
        String SIRName = DefectsDB.getSirName(bugId);
        String cdCmd="cd "+"/"+"home"+"/"+"metadata"+"/"+SIRName;
        String diffInfo = dockerServer.diffRunPrintln(cdCmd+";git diff " + buggyCommit + " " + fixCommit,bugId);
        return diffInfo;
    }

    // 计算定位分数
    public String localMark(String bugId) throws Exception {
        String str = diffInfo(bugId);
        int localDistance = 0;
        String[] strs = str.split("@@");
        String weWant = strs[strs.length-1].toString();
        // 定位分数
        int localMark = 0;

        int minus = weWant.indexOf('-');
        int plus = weWant.indexOf('+');
        String distance = weWant.substring(minus,plus);

        // 计算distance中有多少换行符
        for(int i = 0;i<distance.length();i++){
            if(distance.charAt(i)=='\n'){
                localDistance++;
            }
        }
        localMark = 10 - localDistance + 1;

        return String.valueOf(localMark);
    }

    // 计算修复字符串长度
    public String stringLength(String bugId) throws Exception {
        String str = diffInfo(bugId);
        String[] strs = str.split("@@");
        String weWant = strs[strs.length-1].toString();
        // 修改字符串长度
        int stringLength = weWant.length();
        return String.valueOf(stringLength);
    }

    public void setEnviroment() {
        Configs.refresh();
        String[] toolPaths = Configs.envPath.split(";");
        exec.setEnviroment(toolPaths);
        dockerServer.setEnviroment(toolPaths);
    }

    public void refresh() {
        Configs.refresh();
        DefectsDB.initDB();
    }

    public void add(String s) {

        System.out.println("This feature is not yet implemented.");
    }
    // Pull images
    public void pullAllBug(){
        String[] bugList = ls();
        int i = 0;
        for (String bugId: bugList) {
            i++;
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) i / (float) bugList.length * 100);
            System.out.println("Pulling and Starting the container "+ bugId + "  completed  "+ result +"%");
            pullBug(bugId);
            reNameBug(bugId);
        }
        System.out.println("All Pulled");
    }
    // Start Containers
    public void runAllBug(){
        String[] bugList = ls();
        int i = 0;
        for (String bugId: bugList) {
            i++;
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) i / (float) bugList.length * 100);
            System.out.println("Starting the container "+ bugId + "  completed  "+ result +"%");
            startDocker(bugId);
        }
        System.out.println("All Started");
    }

    public String pullBug(String bugId){
        return  exec.exec("docker pull defects4dl/"+bugId);
    }

    public String reNameBug(String bugId){
        return  exec.exec("docker run --name "+bugId+" -it -d defects4dl/"+bugId);
    }

    public  String  startDocker(String bugId) {

        return exec.exec("docker start "+ bugId);
    }
    public  String  startDocker() {

        return exec.exec("docker start "+ DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
    }

    public String exit() {

        return exec.exec("docker stop "+DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
    }

}
