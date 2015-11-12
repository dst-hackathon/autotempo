package com.dstsystems.hackathon.autotempo.tempo;

public class TempoUrlService {

    private static final String REST_ISSUE_PATH = "/rest/api/2/issue/";
    private static final String REST_WORKLOG_PATH = "/rest/tempo-timesheets/3/worklogs/";
    private static final String TIME_TRACKING_FIELD = "?fields=timetracking";

    private String baseUrl;

    public TempoUrlService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getIssueTimetrackingUrl(String issueKey) {
        return baseUrl + REST_ISSUE_PATH + issueKey + TIME_TRACKING_FIELD;
    }

    public String getWorklogUrl() {
        return baseUrl + REST_WORKLOG_PATH;
    }

}
