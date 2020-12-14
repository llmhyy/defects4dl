package com.vo;

import java.util.ArrayList;
import java.util.List;

public class SIR {
    private List<Bug> bugs = new ArrayList<>();
    private String SIRName;
    private String source;

    public SIR(String SIRName, String source){
        this.SIRName = SIRName;
        this.source = source;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public String getSIRName() {
        return SIRName;
    }

    public String getSource() {
        return source;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    public void setSIRName(String SIRName) {
        this.SIRName = SIRName;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void addBug(Bug bug){
        this.bugs.add(bug);
    }
    public void removeBug(Bug bug){
        getBugs().remove(bug);
    }

}



