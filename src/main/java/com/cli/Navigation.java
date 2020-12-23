package com.cli;

import com.DefectsAction;
import com.docker.DockerExecutor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Navigation {
    private final static String EXIT = "exit";
    private final static String LIST = "ls";
    private final static String HELP = "help";
    private final static String INFO = "info";
    private final static String TEST = "test";
    private final static String DIFF = "diff";
    private final static String ADD = "add";
    private final static String PULL = "pull";
    private final static String REFRESH = "refresh";
    private final static String FEATURES = LIST + "    --show all bug lise\n" + LIST
            + " grep <Key*>           --filter by keyword\n" + INFO
            + "<bugID>                  --show bug Info,such as testcase,\n"
            + "                                diff in wc bic,commitcount,datediff\n" + TEST
            + "<bugId> <version><param>    --test a case.Note that param equals '-r',means run\n"
            + "                                   the reproducible case, default as null\n" + DIFF
            + "<bugId> <version1> <version2>    --diff two version,~1 means fixed parent version\n" + ADD
            + "<script file path>     --add a script to docker\n" + PULL
            + "<bugID> <version>      --get a version source code\n" + REFRESH + "    --refresh Configs and DB\n" + HELP
            + "    --get all features\n" + EXIT + "    --exit system";
    static DefectsAction jctbe = new DefectsAction();

    public static void main(String[] args) {

        System.out.println("Set enviroment....");
        jctbe.setEnviroment();

        System.out.println("Start docker");
        String result = jctbe.startDocker();
        boolean flag = result.contains(DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
        result = flag == true ? "Docker start successful" : "Something wrong in docker starting";
        System.out.println(result);
        System.out.println("DataSet start successful ");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("defects4dl#:");
        try {

            String cmd = bf.readLine().trim();
            while (!cmd.equalsIgnoreCase(EXIT)) {
                navigation(cmd);
                System.out.print("defects4dl#:");
                cmd = bf.readLine().trim();
            }
            if (jctbe.exit().contains(DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID)) {
                System.out.println("Exit success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void navigation(String cmd) {
        String[] params = cmd.split(" ");
        try {
            if (params[0].equals(ADD)) {
                jctbe.add(params[1]);
            } else if (params[0].equalsIgnoreCase(HELP)) {
                StringBuilder sb = new StringBuilder();
                sb.append(FEATURES);
                System.out.println(sb);
            } else if (params[0].equalsIgnoreCase(DIFF)) {
                String bugID = params[1];
//                String version1 = params[2];
//                String version2 = params[3];
                System.out.println(jctbe.diff(bugID));
            } else if (params[0].equalsIgnoreCase(LIST)) {
                // ls grep pool*
                String[] buglist;
                if (params.length > 2) {
                    String pattern = params[2];
                    buglist = jctbe.ls(pattern);
                } else {
                    buglist = jctbe.ls();
                }
                for (int i = 0; i < buglist.length; i++) {
                    System.out.println(buglist[i]);
                }
            } else if (params[0].equalsIgnoreCase(INFO)) {
                String bugID = params[1];
                if (params.length > 2) {
                    System.out.println("Input is wrong");
                } else {
                    System.out.println(jctbe.info(bugID));
                }
            } else if (params[0].equalsIgnoreCase(TEST)) {
                String budID = params[1];
                String version = params[2];
                boolean r = false;
                if (params.length > 3 && params[3].equalsIgnoreCase("-r")) {
                    r = true;
                }
                jctbe.test(budID, version, r);

            } else if (params[0].equalsIgnoreCase(PULL)) {
                String budID = params[1];
                if (params.length < 3) {
                    System.out.println(jctbe.pullBug(budID));
                } else {
                    System.err.println("This feature is removed");
                }
            } else if (params[0].equalsIgnoreCase(REFRESH)) {
                jctbe.refresh();
            } else {
                System.err.println("Unrecognized commands");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
