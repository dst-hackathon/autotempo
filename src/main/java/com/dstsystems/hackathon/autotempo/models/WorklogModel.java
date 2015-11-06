package com.dstsystems.hackathon.autotempo.models;

import java.util.Date;

public class WorklogModel {

    private String issueKey;
    private String comment;
    private String accountKey;
    private long timeSpent;
    private Date date;

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
