package com.dstsystems.hackathon.autotempo.tempo.models;

public class TempoIssue {

    private String key;

    private Long remainingEstimateSeconds;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getRemainingEstimateSeconds() {
        return remainingEstimateSeconds;
    }

    public void setRemainingEstimateSeconds(Long remainingEstimateSeconds) {
        this.remainingEstimateSeconds = remainingEstimateSeconds;
    }

}
