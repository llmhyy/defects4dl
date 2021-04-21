package com.vo;

import com.vo.BuggyVersion;
import com.vo.FixVersion;

public class Bug {

    private String bugID;
    private String errorMessage;  // 报错信息
    private String describe;    // 错误描述
    // private String rootCause;  // 核心修复代码段
    private String operateScore; //operate操作符分数
    private String type;   // General Bug类别
    private BuggyVersion BuggyVersion;
    private FixVersion FixVersion;
    private String localScore;   // 定位难度
    private String fixLength;  // 通过修改字符的长度与修改的行数计算的修复难度


    public Bug(String bugID, String errorMessage, String describe, String operateScore, String type, BuggyVersion buggyVersion, FixVersion fixVersion, String localScore, String fixLength) {
        this.bugID = bugID;
        this.errorMessage = errorMessage;
        this.describe = describe;
        this.operateScore = operateScore;
        this.type = type;
        this.BuggyVersion = buggyVersion;
        this.FixVersion = fixVersion;
        this.localScore = localScore;
        this.fixLength = fixLength;
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
}
