package com.docker;

import com.executor.Executor;
import com.utils.ColorConsole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DockerExecutor extends Executor {
    private static ProcessBuilder pb = new ProcessBuilder();
    // .sh file is stored in this folder
    private final static String SCRIPTS_FOLDER = "script";

    private final static String BASH = "bash";

    public static String DOCKER_JAVA_PLAIN_CONTAINER_ID = "fc379fe8669b";

    private final static String DOCKER_EXEC_BASED_CMD = "docker exec";
    private final static String CAT = "cat";

    public void runPrintln(String arg,String bugId) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + bugId + " " + BASH + " -c " + "\""
                + arg + "\"";
        execPrintln(cmd, pb);
    }
    public String diffRunPrintln(String arg,String bugId) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + bugId + " " + BASH + " -c " + "\""
                + arg + "\"";
        return execPrintlnW(cmd, pb);
    }

    // ==================运行测试用例=====================
    public void runTest(String arg,String bugId) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + bugId + " " + "bash "
                + "/" + SCRIPTS_FOLDER + "/" + arg;
        execPrintlnTest(cmd, pb);
    }
    public void readTxt(String bugId,String version){
        // 读取script目录下的txt文件
        String readTxt = DOCKER_EXEC_BASED_CMD + " " + bugId + " " + CAT + " " + "/" + SCRIPTS_FOLDER + "/"+ bugId + "-" + version + ".txt";
        execPrintln(readTxt, pb);
    }

    public String readTxtW(String bugId,String version){
        // 读取script目录下的txt文件
        String readTxt = DOCKER_EXEC_BASED_CMD + " " + bugId + " " + CAT + " " + "/" + SCRIPTS_FOLDER + "/"+ bugId + "-" + version + ".txt";
        return execPrintlnW(readTxt, pb);
    }

    public String execPrintln(String cmd, ProcessBuilder pb) {
        StringBuffer sb = new StringBuffer();
        try {
            pb.command("cmd.exe", "/c", cmd);
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
        }
        return sb.toString();
    }

    // 运行测试用例
    public String execPrintlnTest(String cmd, ProcessBuilder pb) {
        StringBuffer sb = new StringBuffer();
        try {
            pb.command("cmd.exe", "/c", cmd);
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
        } catch (Exception ex) {
        }
        return sb.toString();
    }

    public String execPrintlnW(String cmd, ProcessBuilder pb) {
        StringBuffer sb = new StringBuffer();
        try {
            pb.command("cmd.exe", "/c", cmd);
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {

                sb.append(line).append("\n");
            }
        } catch (Exception ex) {
            System.out.println("===bufferReader.readLine()为空！===");
        }
        return sb.toString();
    }

    public String run(String arg,String bugId) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + bugId + " " + BASH + " -c " + "\""
                + arg + "\"";
        return exec(cmd, pb);
    }

    public String exec(String cmd, ProcessBuilder pb) {
        StringBuffer sb = new StringBuffer();
        try {
            pb.command("cmd.exe", "/c", cmd);
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception ex) {
        }
        return sb.toString();
    }

    public void setEnviroment(String args[]) {

        Map<String, String> map = pb.environment();
        StringBuilder PATH = new StringBuilder(map.get("Path"));
        for (int i = 1; i < args.length; i++) {
            PATH.append(File.pathSeparator).append(args[i]);
        }
        map.put("PATH", PATH.toString());
    }

    private boolean checkRootCause(String line, List<String> causeList) {
        String tmp = line.replace('+', ' ').replace('-', ' ').replace(" ", "");
        for (String s : causeList) {
            s = s.replace(" ", "");
            if (tmp.contains(s) && s.toCharArray().length > 3 && !tmp.contains("/**")) {
                return true;
            }
        }
        return false;
    }

}
