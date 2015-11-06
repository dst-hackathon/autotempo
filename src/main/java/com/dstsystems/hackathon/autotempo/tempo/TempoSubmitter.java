package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoAuthor;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoIssue;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklog;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklogAttribute;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
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
import java.util.ArrayList;
import java.util.List;

public class TempoSubmitter {

    private static final String ACCOUNT_ATTRIBUTE = "_account_";
    private static final String REST_ISSUE_PATH = "/rest/api/2/issue/";
    private static final String REST_WORKLOG_PATH = "/rest/tempo-timesheets/3/worklogs/";
    private static final String TIME_TRACKING_FIELD = "?fields=timetracking";

    private TempoConfig tempoConfig;

    public TempoSubmitter(TempoConfig tempoConfig) {
        this.tempoConfig = tempoConfig;
    }

    public void submitWorklog(WorklogModel worklogModel) throws IOException {
        String worklogJson = getWorklogJson(worklogModel);
        postWorklog(worklogJson);
    }

    protected String getWorklogJson(WorklogModel worklogModel) throws IOException {
        TempoWorklog tempoWorklog = new TempoWorklog();
        tempoWorklog.setComment(worklogModel.getComment());
        tempoWorklog.setDateStarted(worklogModel.getDate());
        tempoWorklog.setTimeSpentSeconds(worklogModel.getTimeSpent());

        TempoAuthor tempoAuthor = new TempoAuthor();
        tempoAuthor.setName(tempoConfig.getUsername());
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

        return new ObjectMapper().writeValueAsString(tempoWorklog);
    }

    protected long getRemainingEstimate(String issueKey) throws IOException {
        HttpGet httpGet = new HttpGet(tempoConfig.getUrl() + REST_ISSUE_PATH + issueKey + TIME_TRACKING_FIELD);
        String issueJson = sendHttpRequest(httpGet);

        JsonNode jsonNode = new ObjectMapper().readTree(issueJson);
        return jsonNode.path("fields").path("timetracking").path("remainingEstimateSeconds").asLong();
    }

    protected long getNewRemainingEstimate(String issueKey, long timeSpent) throws IOException {
        long remainingEstimate = getRemainingEstimate(issueKey);
        remainingEstimate -= timeSpent;
        if (remainingEstimate < 0) {
            remainingEstimate = 0;
        }

        return remainingEstimate;
    }

    private String postWorklog(String content) throws IOException {
        HttpPost httpPost = new HttpPost(tempoConfig.getUrl() + REST_WORKLOG_PATH);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Origin", tempoConfig.getUrl()); // Required for JIRA cloud instances
        httpPost.setEntity(new StringEntity(content));

        return sendHttpRequest(httpPost);
    }

    private String sendHttpRequest(HttpUriRequest request) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {
            Credentials credentials = new UsernamePasswordCredentials(
                    tempoConfig.getUsername(), tempoConfig.getPassword());
            request.addHeader(new BasicScheme().authenticate(credentials, request, null));

            httpClient = HttpClientBuilder.create().build();
            response = httpClient.execute(request);

            int httpStatus = response.getStatusLine().getStatusCode();
            if (httpStatus != 200) {
                throw new IOException("Got http response code " + httpStatus);
            }

            return IOUtils.toString(response.getEntity().getContent());
        } catch (AuthenticationException e) {
            // From BasicScheme, should not happen
            throw new RuntimeException(e);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }
}
