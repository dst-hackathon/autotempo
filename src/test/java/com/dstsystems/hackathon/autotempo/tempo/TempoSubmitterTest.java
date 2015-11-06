package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.TimeZone;

public class TempoSubmitterTest {

    private TempoSubmitter tempoSubmitter;
    private TempoConfig tempoConfig;

    @Before
    public void setUp() throws Exception {
        // Set timezone for CI server
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));

        tempoConfig = new TempoConfig();
        tempoConfig.setUrl("http://localhost/");
        tempoConfig.setUsername("myjirauser");
        tempoConfig.setPassword("myjirapassword");

        tempoSubmitter = new TempoSubmitter(tempoConfig);
    }

    @Test
    public void testGetWorklogJsonInternal() throws Exception {
        WorklogModel worklogModel = new WorklogModel();
        worklogModel.setComment("My comment");
        worklogModel.setIssueKey("INT-1");
        worklogModel.setTimeSpent(3600);
        worklogModel.setDate(DateTestUtils.buildDate("2015-11-01"));
        worklogModel.setAccountKey("ATT01");

        String expectedJson = "{\n" +
                "    \"dateStarted\": \"2015-10-31T17:00:00.000Z\",\n" +
                "    \"timeSpentSeconds\": 3600,\n" +
                "    \"comment\": \"My comment\",\n" +
                "    \"author\": {\n" +
                "        \"name\": \"myjirauser\"\n" +
                "    },\n" +
                "    \"issue\": {\n" +
                "        \"key\": \"INT-1\",\n" +
                "        \"remainingEstimateSeconds\": 0\n" +
                "    },\n" +
                "    \"worklogAttributes\": [\n" +
                "        {\n" +
                "            \"key\": \"_account_\",\n" +
                "            \"value\": \"ATT01\"\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        String json = tempoSubmitter.getWorklogJson(worklogModel);
        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }



}