package com.vo;

import com.vo.BuggyVersion;
import com.vo.FixVersion;

public class Bug {

    private String bugID;
    private String errorMessage;  //报错信息
    private String describe;    //错误描述
    private String rootCause;  //核心修复代码段
    private String type;   //General Bug类别
    private BuggyVersion BuggyVersion;
    private FixVersion FixVersion;

    public Bug(String bugID, String errorMessage, String describe, String rootCause, String type , BuggyVersion buggyVersion, FixVersion fixVersion) {
        this.bugID = bugID;
        this.errorMessage = errorMessage;
        this.describe = describe;
        this.rootCause = rootCause;
        this.type = type;
        this.BuggyVersion = buggyVersion;
        this.FixVersion = fixVersion;
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

    public String getRootCause() {
        return rootCause;
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

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
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
}
