package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.TimeZone;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.doReturn;

public class TempoSubmitterTest {

    private TempoSubmitter tempoSubmitter;
    private TempoConfig tempoConfig;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8111);

    @Before
    public void setUp() throws Exception {
        // Set timezone for CI server
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));

        tempoConfig = new TempoConfig();
        tempoConfig.setUrl("http://localhost:8111/");
        tempoConfig.setUsername("myjirauser");
        tempoConfig.setPassword("myjirapassword");

        tempoSubmitter = Mockito.spy(new TempoSubmitter(tempoConfig));
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(tempoSubmitter);
    }

    @Test
    public void testSubmitWorklog() throws Exception {
        WorklogModel worklogModel = new WorklogModel();
        doReturn("test json").when(tempoSubmitter).getWorklogJson(worklogModel);

        stubFor(post(urlEqualTo("/rest/tempo-timesheets/3/worklogs/"))
                .withRequestBody(equalTo("test json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("test response")));

        tempoSubmitter.submitWorklog(worklogModel);
    }

    @Test(expected = IOException.class)
    public void testSubmitWorklogCaseError() throws Exception {
        WorklogModel worklogModel = new WorklogModel();
        doReturn("test json").when(tempoSubmitter).getWorklogJson(worklogModel);

        stubFor(post(urlEqualTo("/rest/tempo-timesheets/3/worklogs/"))
                .withRequestBody(equalTo("test json"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("test response")));

        tempoSubmitter.submitWorklog(worklogModel);
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