package com;

import JavaBasedConfig.Configs;
import com.docker.DockerExecutor;
import com.executor.Execute;
import com.vo.Bug;
import com.vo.BuggyVersion;
import com.vo.Distance;
import com.vo.FixVersion;
import com.docker.DockerExecutor;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefectsAction {
    public static Execute exec = new Execute();
    private static DockerExecutor dockerExecutor =new DockerExecutor();
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

        // 获取location分数与修复字符串长度
        String localScore = localMark(bugId);
        String fixLength = stringLength(bugId);

        Bug Bug = new Bug(bugId, errorMessage,describe,rootCause,type,BuggyVersion,FixVersion,localScore,fixLength);

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
        // System.out.println(diffInfo);
        return diffInfo;
    }

    public String diffInfoStat(String bugId) throws Exception {

        Bug bug = DefectsDB.getBug(bugId);
        String rootCause = bug.getRootCause();
        BuggyVersion buggyVersion = bug.getBuggyVersion();
        String buggyCommit = buggyVersion.getBuggycommit();
        FixVersion fixVersion = bug.getFixVersion();
        String fixCommit = fixVersion.getFixcommit();
        // String SIRName = DefectsDB.getSirName(bugId).toLowerCase();
        String SIRName = DefectsDB.getSirName(bugId);
        String cdCmd="cd "+"/"+"home"+"/"+"metadata"+"/"+SIRName;
        String diffInfoStat = dockerServer.diffRunPrintln(cdCmd+";git diff " + buggyCommit + " " + fixCommit + " --stat",bugId);
        return diffInfoStat;
    }

    // ================计算定位分数================
    /*
    public String localMark(String bugId) throws Exception {
        String str = diffInfo(bugId);
        // 总的换行符个数
        float localDistance = 0;
        // 定位分数
        float localMark = 0;
        // 减去的分数
        int mScore = 0;
        String distance = "";
        // 每个chunk中'-'和'+'的数量
        int minusC = 0;
        int plusC = 0;
        String[] strs = str.split("@@");
        // 遍历chunk
        for(int i = 2;i< strs.length;i=i+2){
            String weWant = strs[i].toString();
            // int minus = weWant.indexOf('-');
            // int plus = weWant.indexOf('+');
            int minus = 0;
            int plus = 0;
            for(int j =0;j<weWant.length();j++){
                if(weWant.charAt(j)=='-' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='-'){
                    minus = j;
                    break;
                }
            }
            for(int j =0;j<weWant.length();j++){
                if(weWant.charAt(j)=='+' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='+'){
                    plus = j;
                    break;
                }
            }
            for(int j = 0;j<weWant.length();j++){
                if(weWant.charAt(j)=='+' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='+'){
                    plusC++;
                }
                if(weWant.charAt(j)=='-' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='-'){
                    minusC++;
                }
            }

            if(minus <= 0 || plus <= 0){
                mScore += 2;
            }else {
                if(minus > plus){
                    distance = weWant.substring(plus,minus);
                }else {
                    distance = weWant.substring(minus,plus);
                }
                // 计算distance中有多少换行符
                int hhf = 0;
                for(int j = 0;j<distance.length();j++){
                    if(distance.charAt(j)=='\n'){
                        hhf++;
                    }
                }
                if(hhf == 0){
                    continue;
                }
                if(minusC == plusC){
                    localDistance = 0;
                }
                localDistance = localDistance + (float)hhf/(hhf+1) - 1/2;

            }

        }
        localMark = (float) (10.5 - mScore - localDistance);
        if (localMark <= 0){
            localMark = 0;
        }
        return String.valueOf(localMark);
    }
    */
    public String localMark(String bugId) throws Exception {
        float localScore = 0;
        // 读取buggy分支运行结果，获取异常运行轨迹
        String bugTrace = dockerExecutor.readTxtW(bugId,"buggy");
        // 判断里面有没有Traceback关键字
        int include_trace = bugTrace.indexOf("Traceback");
        if(include_trace != -1){
            List fileNames = new ArrayList();
            List errorLines = new ArrayList();
            List diffFileNames = new ArrayList();
            List diffLines = new ArrayList();
            // 包含关键字，有报错，获取报错信息
            fileNames = getFileName(bugTrace);
            errorLines = getErrorLine(bugTrace);
            // 获取diff中的信息
            String diffInfoStat = diffInfoStat(bugId);
            diffFileNames = getFileName(diffInfoStat);
            String diffInfo = diffInfo(bugId);
            diffLines = getDiffLine(diffInfo);



            // 匹配报错文件与diff中修改的文件,取其中最小的行数差
            int distance = 5000;
            for(int i = 0;i< fileNames.size();i++){
                for(int j = 0;j< diffFileNames.size();j++){
                    if(fileNames.get(i).equals(diffFileNames.get(j))){
                        int distance1 = Integer.parseInt(errorLines.get(i).toString()) - Integer.parseInt(diffLines.get(j).toString());
                        if(Math.abs(distance1) < distance){
                            distance = Math.abs(distance1);
                        }
                    }
                }
            }
            if(distance == 5000){
                // 没有匹配到，获取文件结构,计算文件距离
                // 人为查看文件距离，或者具体获取报错路径，然后查看diff修改的路径，计算距离
                localScore = Distance.selectDis(bugId);

            }else {
                // 匹配到，计算定位难度
                localScore = (float)distance/(float)(distance+1);
            }

        }else {
            // 不包含关键字，测试用例没有报错
            localScore = 10;
        }

        return String.valueOf(localScore);
    }


    // 正则表达式 捕获报错文件名
    public List<String> getFileName(String str){
        List fileNames = new ArrayList();
        String patternFile = "[\\w]+(\\.py)";
        Pattern rf = Pattern.compile(patternFile);
        Matcher mf = rf.matcher(str);
        while(mf.find()){
            fileNames.add(mf.group());
        }
        return fileNames;
    }

    // 正则表达式，捕获报错行数
    public List<String> getErrorLine(String str){
        List errorLines = new ArrayList();
        String patternLine = "line\\s\\d+";
        Pattern rl = Pattern.compile(patternLine);
        Matcher ml = rl.matcher(str);
        while(ml.find()){
            String a = getNumber(ml.group());
            errorLines.add(a);
        }
        return errorLines;
    }

    // 正则表达式，获取diff中的修改行数
    public List<String> getDiffLine(String str){
        List diffLines = new ArrayList();
        String pattern = "[@][@][\\s][+-]\\d+";
        Pattern dl = Pattern.compile(pattern);
        Matcher m = dl.matcher(str);
        while(m.find()){
            String a = getNumber(m.group());
            diffLines.add(a);
        }
        return diffLines;
    }

    // 正则表达式，取数字
    public String getNumber(String str){
        String num = null;
        String pattern = "\\d+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while(m.find()){
            num = m.group();
        }
        return num;
    }



    // ================计算修复字符串长度================
    public String stringLength(String bugId) throws Exception {
        String str = diffInfo(bugId);
        int stringLength = 0;
        String[] strs = str.split("@@");
        // 修改的chunk数
        for(int i=2;i< strs.length;i=i+2){
            String weWant = strs[i].toString();
            int lengths = 0;
            int length = 0;
            int porm = 0;   // +或-
            int lineBreak = 0;  // 换行符
            // 遍历每个chunk,找+或-，然后换行符，计算它们之间的距离
            for(int j=0;j<weWant.length();j++){

                if((weWant.charAt(j)=='+' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='+') || (weWant.charAt(j)=='-' && weWant.charAt(j-1)=='\n' && weWant.charAt(j+1)!='-')){
                    porm = j;
                }
                if(weWant.charAt(j)=='\n'){
                    lineBreak = j;
                }
                if(porm !=0 && lineBreak !=0 && lineBreak>porm){
                    length = lineBreak - porm;
                    lineBreak = 0;
                    porm = 0;
                    lengths += length;
                }
            }
            stringLength += lengths;
        }
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
