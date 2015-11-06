package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoAuthor;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoIssue;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklog;
import com.dstsystems.hackathon.autotempo.tempo.models.TempoWorklogAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TempoSubmitter {

    public static final String ACCOUNT_ATTRIBUTE = "_account_";

    private TempoConfig tempoConfig;

    public TempoSubmitter(TempoConfig tempoConfig) {
        this.tempoConfig = tempoConfig;
    }

    public void submitWorklog(WorklogModel worklogModel) {
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

}
