package com.dstsystems.hackathon.autotempo.tempo;

import com.dstsystems.hackathon.autotempo.models.UserProfileModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.IOUtils;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class TempoSubmitterTest {

    private TempoSubmitter tempoSubmitter;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8111);

    @Before
    public void setUp() throws Exception {
        // Set timezone for CI server
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));

        UserProfileModel userProfile = new UserProfileModel();
        userProfile.setURL("http://localhost:8111/");
        userProfile.setUserName("myjirauser");
        userProfile.setPassword("myjirapassword");

        tempoSubmitter = Mockito.spy(new TempoSubmitter(userProfile));
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
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("test response")));

        tempoSubmitter.submitWorklog(worklogModel);

        verify(postRequestedFor(urlEqualTo("/rest/tempo-timesheets/3/worklogs/"))
                .withHeader("Authorization", equalTo("Basic bXlqaXJhdXNlcjpteWppcmFwYXNzd29yZA=="))
                .withRequestBody(equalTo("test json")));
    }

    @Test(expected = IOException.class)
    public void testSubmitWorklogCaseError() throws Exception {
        WorklogModel worklogModel = new WorklogModel();
        doReturn("test json").when(tempoSubmitter).getWorklogJson(worklogModel);

        stubFor(post(urlEqualTo("/rest/tempo-timesheets/3/worklogs/"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("test response")));

        tempoSubmitter.submitWorklog(worklogModel);
    }

    @Test
    public void testGetRemainingEstimate() throws Exception {
        String issueKey = "TP-1";

        mockRemainingEstimate(issueKey);

        assertEquals(7200, tempoSubmitter.getRemainingEstimate(issueKey));
    }

    @Test
    public void testGetRemainingEstimateCaseNoEstimate() throws Exception {
        String issueKey = "INT-2";

        mockRemainingEstimate(issueKey);

        assertEquals(0, tempoSubmitter.getRemainingEstimate(issueKey));
    }

    @Test
    public void testGetNewRemainingEstimate() throws Exception {
        String issueKey = "INT-2";

        doReturn(1000L).when(tempoSubmitter).getRemainingEstimate(issueKey);

        assertEquals(900, tempoSubmitter.getNewRemainingEstimate(issueKey, 100));
    }

    @Test
    public void testGetNewRemainingEstimateCaseNegative() throws Exception {
        String issueKey = "INT-2";

        doReturn(1000L).when(tempoSubmitter).getRemainingEstimate(issueKey);

        assertEquals(0, tempoSubmitter.getNewRemainingEstimate(issueKey, 10000));
    }

    @Test
    public void testGetWorklogJsonInternal() throws Exception {
        WorklogModel worklogModel = new WorklogModel();
        worklogModel.setComment("My comment");
        worklogModel.setIssueKey("TP-1");
        worklogModel.setTimeSpent(3600);
        worklogModel.setDate(DateTestUtils.buildDate("2015-11-01"));
        worklogModel.setAccountKey("ATT01");

        doReturn(10L).when(tempoSubmitter).getNewRemainingEstimate("TP-1", 3600);

        String expectedJson = "{\n" +
                "    \"dateStarted\": \"2015-10-31T17:00:00.000Z\",\n" +
                "    \"timeSpentSeconds\": 3600,\n" +
                "    \"comment\": \"My comment\",\n" +
                "    \"author\": {\n" +
                "        \"name\": \"myjirauser\"\n" +
                "    },\n" +
                "    \"issue\": {\n" +
                "        \"key\": \"TP-1\",\n" +
                "        \"remainingEstimateSeconds\": 10\n" +
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

    private void mockRemainingEstimate(String issueKey) throws IOException {
        stubFor(get(urlEqualTo("/rest/api/2/issue/" + issueKey + "?fields=timetracking"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(IOUtils.toString(this.getClass().getResourceAsStream(issueKey + ".json")))));
    }

}