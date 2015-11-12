package com.dstsystems.hackathon.autotempo.tempo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TempoUrlServiceTest {

    private TempoUrlService tempoUrlService;

    @Before
    public void setUp() throws Exception {
        tempoUrlService = new TempoUrlService("http://jira");
    }

    @Test
    public void testGetIssueTimetrackingUrl() throws Exception {
        assertEquals("http://jira/rest/api/2/issue/INT-1234?fields=timetracking",
                tempoUrlService.getIssueTimetrackingUrl("INT-1234"));
    }

    @Test
    public void testGetWorklogUrl() throws Exception {
        assertEquals("http://jira/rest/tempo-timesheets/3/worklogs/", tempoUrlService.getWorklogUrl());
    }

    @Test
    public void testGetWorklogUrlWithTrailingSlash() throws Exception {
        tempoUrlService = new TempoUrlService("http://jira/");
        assertEquals("http://jira/rest/tempo-timesheets/3/worklogs/", tempoUrlService.getWorklogUrl());
    }

}