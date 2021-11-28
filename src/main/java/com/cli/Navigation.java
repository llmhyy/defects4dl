package com.cli;

import com.ConsoleColors.ConsoleColors;
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
    private final static String PULL_ONE_BUG = "pullOneBug";
    private final static String START_ONE_BUG = "startOneBug";
    private final static String REFRESH = "refresh";
    private final static String PULL_BUGS = "pullBug";
    private final static String RUN_BUGS = "runBug";
    private final static String FEATURES = LIST + "    --show all bug lise\n" + LIST
            + " grep <Key*>           --filter by keyword\n" + INFO
            + "<bugID>                  --show bug Info,such as testcase,\n" + TEST
            + "<bugId> <version>               --test a case.\n" + PULL_BUGS
            + "    --pull and run all the bug in the Docker Repo.\n" + RUN_BUGS
            + "    --run all the bug in the Docker Repo.\n" + DIFF
            + "<bugId>                    --diff two version\n" + PULL_ONE_BUG
            + "<bugId>      --Pull a Docker mirror based on bugId\n" + START_ONE_BUG
            + "<bugId>      --Start one Docker mirror based on bugId\n" + REFRESH
            + "    --refresh the library\n" + HELP
            + "    --get all features\n" + EXIT + "    --exit system";
    static DefectsAction jctbe = new DefectsAction();

    public static void main(String[] args) {

        System.out.println("Set environment....");
        jctbe.setEnviroment();

        System.out.println("Starting docker...");
        System.out.println("DataSet start successful ");
        System.out.println(ConsoleColors.YELLOW + "When you first use our library, you need to pull the Docker Images down using the 'pullBug' command!!! ");
        System.out.println(ConsoleColors.YELLOW + "You can also pull a single image through the `pullOneBug <bugId>` command!!!");
        System.out.println(ConsoleColors.YELLOW + "If you've previously pulled a docker image and the container is now closed, you can use `runBug` to start all Containers or `startOneBug <bugId>` command to start a single container!!! ");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(ConsoleColors.RESET + "defects4dl#:");
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
                    System.out.println("Input is wrong,Please enter the 'help' command to view the command");
                } else {
                    System.out.println(jctbe.info(bugID));
                }
            } else if (params[0].equalsIgnoreCase(TEST)) {
                String budID = params[1];
                String version = params[2];
                if (params.length != 3 ) {
                    System.out.println("Input is wrong,Please enter the 'help' command to view the command");
                }
                jctbe.test(budID, version);

            } else if (params[0].equalsIgnoreCase(PULL_ONE_BUG)) {
                String budID = params[1];
                if (params.length > 2) {
                    System.out.println("Input is wrong,Please enter the 'help' command to view the command");
                } else {
                    System.err.println(jctbe.PullOneBug(budID));
                }
            }else if (params[0].equalsIgnoreCase(START_ONE_BUG)) {
                String budID = params[1];
                if (params.length > 2) {
                    System.out.println("Input is wrong,Please enter the 'help' command to view the command");
                } else {
                    System.err.println(jctbe.startOneBug(budID));
                    System.out.println("Start Success!");
                }
            } else if (params[0].equalsIgnoreCase(REFRESH)) {
                jctbe.refresh();
            }else if (params[0].equalsIgnoreCase(PULL_BUGS)) {
                jctbe.pullAllBug();
            }else if(params[0].equalsIgnoreCase(RUN_BUGS)){
                jctbe.runAllBug();
            } else {
                System.err.println("Unrecognized commands");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
