package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoAuthor;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoIssue;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklog;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklogAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TempoSubmitter {

    private static final String ACCOUNT_ATTRIBUTE = "_account_";
    private static final String REST_WORKLOG_PATH = "/rest/tempo-timesheets/3/worklogs/";

    private TempoConfig tempoConfig;

    public TempoSubmitter(TempoConfig tempoConfig) {
        this.tempoConfig = tempoConfig;
    }

    public void submitWorklog(WorklogModel worklogModel) throws IOException {
        String worklogJson = getWorklogJson(worklogModel);
        sendHttpRequest(worklogJson);
    }

    protected String getWorklogJson(WorklogModel worklogModel) throws JsonProcessingException {
        TempoWorklog tempoWorklog = new TempoWorklog();
        tempoWorklog.setComment(worklogModel.getComment());
        tempoWorklog.setDateStarted(worklogModel.getDate());
        tempoWorklog.setTimeSpentSeconds(worklogModel.getTimeSpent());

        TempoAuthor tempoAuthor = new TempoAuthor();
        tempoAuthor.setName(tempoConfig.getUsername());
        tempoWorklog.setAuthor(tempoAuthor);

        TempoIssue tempoIssue = new TempoIssue();
        tempoIssue.setKey(worklogModel.getIssueKey());
        tempoWorklog.setIssue(tempoIssue);
        // TODO: need to get and calculate remaining time for non-internal issues

        List<TempoWorklogAttribute> worklogAttributes = new ArrayList<>();
        worklogAttributes.add(new TempoWorklogAttribute(
                ACCOUNT_ATTRIBUTE, worklogModel.getAccountKey()));
        tempoWorklog.setWorklogAttributes(worklogAttributes);

        return new ObjectMapper().writeValueAsString(tempoWorklog);
    }

    private String sendHttpRequest(String content) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(tempoConfig.getUrl() + REST_WORKLOG_PATH);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Origin", tempoConfig.getUrl()); // Required for JIRA cloud instances
            httpPost.setEntity(new StringEntity(content));

            response = httpClient.execute(httpPost);

            int httpStatus = response.getStatusLine().getStatusCode();
            if (httpStatus != 200) {
                throw new IOException("Got http response code " + httpStatus);
            }

            return IOUtils.toString(response.getEntity().getContent());
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
