package com.dstsystems.hackathon.autotempo.tempo.models;

public class TempoIssue {

    private String key;

    private long remainingEstimateSeconds;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public void setRemainingEstimateSeconds(long remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
    }

}
