package com.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Execute extends Executor{
    public final static String OS_WINDOWS = "windows 10";
    public final static String OS_MAC = "mac";
    public final static String OS_UNIX = "unix";
    private static String OS = System.getProperty("os.name").toLowerCase();
    ProcessBuilder pb = new ProcessBuilder();

    public void setEnviroment(String args[]) {

        Map<String, String> map = pb.environment();
        StringBuilder PATH = null;
        if(OS.equals(OS_WINDOWS)){
            PATH = new StringBuilder(map.get("Path"));
        }else {
            PATH = new StringBuilder(map.get("PATH"));
        }
        //StringBuilder PATH = new StringBuilder(map.get("Path"));
        for (int i = 1; i < args.length; i++) {
            PATH.append(File.pathSeparator).append(args[i]);
        }
        map.put("PATH", PATH.toString());
    }

    public void setDirectory(File file) {
        pb.directory(file);
    }

    public String exec(String cmd) {
        pb.redirectErrorStream(true);
        StringBuilder builder = new StringBuilder();
        try {
            //String OS = System.getProperty("os.name").toLowerCase();
            if (OS.equals(OS_WINDOWS)) {
                pb.command("cmd.exe", "/c", cmd);
            } else {
                pb.command("bash", "-c", cmd);
            }
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null){
                builder.append("\n").append(line);
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public int execPrintln(String cmd) {
        int a = 1;
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.equals(OS_WINDOWS)) {
                pb.command("cmd.exe", "/c", cmd);
            } else {
                pb.command("bash", "-c", cmd);
            }
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }
            try {
                a = process.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Execute.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    public int execPrintlnC(String cmd, List<String> rootCauseList) {
        int a = 1;
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.equals(OS_WINDOWS)) {
                pb.command("cmd.exe", "/c", cmd);
            } else {
                pb.command("bash", "-c", cmd);
            }
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (checkRootCause(line, rootCauseList)) {
                    //ColorConsole.println(line);
                } else {
                    System.out.println(line);
                }
            }
            try {
                a = process.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Execute.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    public boolean checkRootCause(String line, List<String> causeList) {
        String tmp = line.replace('+', ' ').replace('-', ' ').replace(" ", "");
        for (String s : causeList) {
            s=s.replace(" ", "");
            if (tmp.equals(s) && s.toCharArray().length>=2) {
                return true;
            }
        }
        return false;
    }
}
