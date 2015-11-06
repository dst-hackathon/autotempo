package com.dstsystems.hackathon.autotempo.tempo.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class TempoWorklog {

    // Tempo uses local time, but Jackson wants to serialize in UTC, so also pass the timezone
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date dateStarted;

    private long timeSpentSeconds;

    private String comment;

    private TempoAuthor author;

    private TempoIssue issue;

    private List<TempoWorklogAttribute> worklogAttributes;

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public long getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public void setTimeSpentSeconds(long timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TempoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(TempoAuthor author) {
        this.author = author;
    }

    public TempoIssue getIssue() {
        return issue;
    }

    public void setIssue(TempoIssue issue) {
        this.issue = issue;
    }

    public List<TempoWorklogAttribute> getWorklogAttributes() {
        return worklogAttributes;
    }

    public void setWorklogAttributes(List<TempoWorklogAttribute> worklogAttributes) {
        this.worklogAttributes = worklogAttributes;
    }

}
