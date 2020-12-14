package com.vo;

public class BuggyVersion {
    private String buggytestCmd;
    //private String buildCmd;
    private String buggycommit;
    //private String orignCommit;


    public BuggyVersion(String buggytestCmd, String buggycommit) {
        this.buggytestCmd = buggytestCmd;
        this.buggycommit = buggycommit;
    }

    public String test(String cmdLine){
        return null;
    }
    public String build(String cmdLine){
        return null;
    }
    public String checkOut(String cmdLine){
        return null;
    }

    public String getBuggytestCmd() {
        return buggytestCmd;
    }

    public String getBuggycommit() {
        return buggycommit;
    }

    public void setBuggytestCmd(String buggytestCmd) {
        this.buggytestCmd = buggytestCmd;
    }

    public void setBuggycommit(String buggycommit) {
        this.buggycommit = buggycommit;
    }
}
