package com.vo;

public class Bug {

    private String bugID;
    private String errorMessage;  // 报错信息
    private String describe;    // 错误描述
    private String operateScore; //operate操作符分数
    private String type;   // General Bug类别
    private BuggyVersion BuggyVersion;
    private FixVersion FixVersion;
    private String localScore;   // 定位难度
    private String fixLength;  // 通过修改字符的长度与修改的行数计算的修复难度
    private String character;   // 动态或静态Bug，1代表动态，0代表静态
    private String commit_url;  //github中commit的url，主要是为了给没有pull docker镜像的用户看


    public Bug(String bugID, String errorMessage, String describe, String operateScore, String type, BuggyVersion buggyVersion, FixVersion fixVersion, String localScore, String fixLength, String character, String commit_url) {
        this.bugID = bugID;
        this.errorMessage = errorMessage;
        this.describe = describe;
        this.operateScore = operateScore;
        this.type = type;
        this.BuggyVersion = buggyVersion;
        this.FixVersion = fixVersion;
        this.localScore = localScore;
        this.fixLength = fixLength;
        this.character = character;
        this.commit_url = commit_url;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBugID() {
        return bugID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDescribe() {
        return describe;
    }

    public String getOperateScore() {
        return operateScore;
    }

    public void setBugID(String bugID) {
        this.bugID = bugID;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setOperateScore(String operateScore) {
        this.operateScore = operateScore;
    }

    public com.vo.BuggyVersion getBuggyVersion() {
        return BuggyVersion;
    }

    public FixVersion getFixVersion() {
        return FixVersion;
    }

    public void setBuggyVersion(BuggyVersion buggyVersion) {
        BuggyVersion = buggyVersion;
    }

    public void setFixVersion(FixVersion fixVersion) {
        this.FixVersion = fixVersion;
    }

    public String getLocalScore() {
        return localScore;
    }

    public String getFixLength() {
        return fixLength;
    }

    public void setLocalScore(String localScore) {
        this.localScore = localScore;
    }

    public void setFixLength(String fixLength) {
        this.fixLength = fixLength;
    }

    public String getCharacter() {
        return character;
    }

    public String getCommit_url() {
        return commit_url;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setCommit_url(String commit_url) {
        this.commit_url = commit_url;
    }
}
