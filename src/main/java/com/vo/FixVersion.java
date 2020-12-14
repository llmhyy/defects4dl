package com.vo;

public class FixVersion {
    private String fixtestCmd;
    //private String buildCmd;
    private String fixcommit;

    public FixVersion(String fixtestCmd, String fixcommit) {
        this.fixtestCmd = fixtestCmd;
        this.fixcommit = fixcommit;
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

    public String getFixtestCmd() {
        return fixtestCmd;
    }

    public String getFixcommit() {
        return fixcommit;
    }

    public void setFixtestCmd(String fixtestCmd) {
        this.fixtestCmd = fixtestCmd;
    }

    public void setFixcommit(String fixcommit) {
        this.fixcommit = fixcommit;
    }
}
