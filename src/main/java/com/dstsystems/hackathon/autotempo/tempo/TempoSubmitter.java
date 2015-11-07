package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoAuthor;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoIssue;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklog;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklogAttribute;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.*;

public class TempoSubmitter {

    private static final String ACCOUNT_ATTRIBUTE = "_Account_";
    private static final String REST_ISSUE_PATH = "/rest/api/2/issue/";
    private static final String REST_WORKLOG_PATH = "/rest/tempo-timesheets/3/worklogs/";
    private static final String TIME_TRACKING_FIELD = "?fields=timetracking";

    private TempoUserProfileModel userProfile;
    private ObjectMapper objectMapper;
    private boolean dry;

    public TempoSubmitter(TempoUserProfileModel userProfile) {
        this.userProfile = userProfile;

        // Tempo operates in local time
        objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
    }

    public void submitWorklog(WorklogModel worklogModel) throws IOException {
        String worklogJson = getWorklogJson(worklogModel);

        if (!dry) {
            postWorklog(worklogJson);
        }
    }

    protected String getWorklogJson(WorklogModel worklogModel) throws IOException {
        TempoWorklog tempoWorklog = new TempoWorklog();
        tempoWorklog.setComment(worklogModel.getComment());
        tempoWorklog.setDateStarted(worklogModel.getDate());
        tempoWorklog.setTimeSpentSeconds(worklogModel.getTimeSpent());

        TempoAuthor tempoAuthor = new TempoAuthor();
        tempoAuthor.setName(userProfile.getUsername());
        tempoWorklog.setAuthor(tempoAuthor);

        TempoIssue tempoIssue = new TempoIssue();
        tempoIssue.setKey(worklogModel.getIssueKey());
        tempoIssue.setRemainingEstimateSeconds(getNewRemainingEstimate(
                worklogModel.getIssueKey(), worklogModel.getTimeSpent()));
        tempoWorklog.setIssue(tempoIssue);

        List<TempoWorklogAttribute> worklogAttributes = new ArrayList<>();
        worklogAttributes.add(new TempoWorklogAttribute(
                ACCOUNT_ATTRIBUTE, worklogModel.getAccountKey()));
        tempoWorklog.setWorklogAttributes(worklogAttributes);

        return objectMapper.writeValueAsString(tempoWorklog);
    }

    protected long getRemainingEstimate(String issueKey) throws IOException {
        HttpGet httpGet = new HttpGet(userProfile.getURL() + REST_ISSUE_PATH + issueKey + TIME_TRACKING_FIELD);
        String issueJson = sendHttpRequest(httpGet);

        JsonNode jsonNode = new ObjectMapper().readTree(issueJson);
        return jsonNode.path("fields").path("timetracking").path("remainingEstimateSeconds").asLong();
    }

    protected Long getNewRemainingEstimate(String issueKey, long timeSpent) throws IOException {
        try {
            long remainingEstimate = getRemainingEstimate(issueKey);
            remainingEstimate -= timeSpent;
            if (remainingEstimate < 0) {
                remainingEstimate = 0;
            }

            return remainingEstimate;
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                // We might not have the right to view internal issues
                // In this case, just use no time estimate, since internal issues
                // do not require estimates
                return null;
            } else {
                throw e;
            }
        }
    }

    private String postWorklog(String content) throws IOException {
        HttpPost httpPost = new HttpPost(userProfile.getURL() + REST_WORKLOG_PATH);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Origin", userProfile.getURL()); // Required for JIRA cloud instances
        httpPost.setEntity(new StringEntity(content));

        return sendHttpRequest(httpPost);
    }

    private String sendHttpRequest(HttpUriRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build()) {
            Credentials credentials = new UsernamePasswordCredentials(
                    userProfile.getUsername(), userProfile.getPassword());
            request.addHeader(new BasicScheme().authenticate(credentials, request, null));

            try (CloseableHttpResponse response = httpClient.execute(request)){
                String result = IOUtils.toString(response.getEntity().getContent());

                int httpStatus = response.getStatusLine().getStatusCode();
                if (httpStatus != 200) {
                    throw new HttpStatusException("Got http response code " + httpStatus + ": " + result, httpStatus);
                }

                return result;
            }
        } catch (AuthenticationException e) {
            // From BasicScheme, should not happen
            throw new RuntimeException(e);
        }
    }

    public boolean isDry() {
        return dry;
    }

    public void setDry(boolean dry) {
        this.dry = dry;
    }

}
