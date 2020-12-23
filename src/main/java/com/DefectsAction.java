package com;

import JavaBasedConfig.Configs;
import com.docker.DockerExecutor;
import com.executor.Execute;
import com.utils.CodeUtils;
import com.vo.Bug;
import com.vo.BuggyVersion;
import com.vo.FixVersion;

import java.math.BigDecimal;
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

    public String pullBug(String bugID) throws Exception {
        return dockerServer.pullBug(bugID);
    }

    public String info(String bugId) throws Exception {
        Bug bug = DefectsDB.getBug(bugId);
        String errorMessage = bug.getErrorMessage();
        String describe = bug.getDescribe();
        String rootCause = bug.getRootCause();

        String SIRName = DefectsDB.getSirName(bugId).toLowerCase();

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
        String commitTimeBuggy = dockerServer.run(cdCmd+";git log --pretty=format:“%cd” " + buggycommit + " -1");
        sb.append("BuggyVersion").append(" ").append(buggycommit).append(" ").append(commitTimeBuggy)
                .append("\n");

        FixVersion fixVersion = bug.getFixVersion();
        String fixtestCmd = fixVersion.getFixtestCmd();
        String fixcommit = fixVersion.getFixcommit();
        String commitTimeFix = dockerServer.run(cdCmd+";git log --pretty=format:“%cd” " + fixcommit + " -1");
        sb.append("FixVersion").append(" ").append(fixcommit).append(" ").append(commitTimeFix)
                .append("\n");

        String diffCount = dockerServer.run(cdCmd+";git rev-list " + buggycommit + ".." + fixcommit + " --count");
        sb.append("Commits count between BuggyVersion and FixVersion:").append(diffCount);
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


    public void test(String bugId, String version, boolean r) throws Exception {
        Bug bug = DefectsDB.getBug(bugId);
        if (version.equals("buggy")){
            BuggyVersion buggyVersion = bug.getBuggyVersion();
            String buggytestCmd = buggyVersion.getBuggytestCmd();
            String buggycommit = buggyVersion.getBuggycommit();
            String sirName=DefectsDB.getSirName(bugId);
            //Some cases can't reproducible,use -r to force
            if (r==true) {
                dockerServer.reproducible();
            }
            System.out.println(dockerServer.checkout(sirName, buggycommit));
            dockerServer.runTest(buggytestCmd);
            dockerServer.endTest();
        }
        else if (version.equals("fix")){
            FixVersion fixVersion = bug.getFixVersion();
            String fixtestCmd = fixVersion.getFixtestCmd();
            String fixcommit = fixVersion.getFixcommit();
            String sirName=DefectsDB.getSirName(bugId);
            //Some cases can't reproducible,use -r to force
            if (r==true) {
                dockerServer.reproducible();
            }
            System.out.println(dockerServer.checkout(sirName, fixcommit));
            dockerServer.runTest(fixtestCmd);
            dockerServer.endTest();
        }else {
            System.out.println("Input is wrong!");
        }

    }

    public String diff(String bugId) throws Exception {

        Bug bug = DefectsDB.getBug(bugId);
        String rootCause = bug.getRootCause();
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggycommit = buggyVersion.getBuggycommit();

        FixVersion fixVersion = bug.getFixVersion();
        String fixcommit = fixVersion.getFixcommit();

        String SIRName = DefectsDB.getSirName(bugId).toLowerCase();
        String cdCmd="cd "+File.separator+"home"+File.separator+"metadata"+File.separator+SIRName;

        System.out.println("-----------Diff--------------\nCHANGE FILES:");
        //>log.txt;cat log.txt;rm log.txt
        dockerServer.runPrintln(cdCmd+";git diff " + buggycommit + " " + fixcommit + " --stat");
        System.out.println("CHANGE DETAILS:");

        List<String> causeSet = null;
        causeSet= CodeUtils.getRootCause(bugId);
        dockerServer.runPrintln(cdCmd+";git diff " + buggycommit + " " + fixcommit,causeSet);
        return "";
    }

    public void setEnviroment() {
        Configs.refresh();
        String[] toolPaths = Configs.envPath.split(";");
        System.out.println(toolPaths);
        exec.setEnviroment(toolPaths);
        dockerServer.setEnviroment(toolPaths);
    }

    public void refresh() {
        Configs.refresh();
        DefectsDB.initDB();
    }

    public void add(String s) {
        exec.execPrintln("docker cp " + s + "conreg4j-java-plain:/scripts");
        System.out.println("Success");
    }

    public  String  startDocker() {

        return exec.exec("docker start "+ DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
    }
    public String exit() {

        return exec.exec("docker stop "+DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
    }

}
