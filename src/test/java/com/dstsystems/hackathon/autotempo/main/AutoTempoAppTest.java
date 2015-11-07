package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AutoTempoAppTest {

    private AutoTempoApp autoTempoApp;

    @Before
    public void setUp() throws Exception {
        autoTempoApp = new AutoTempoApp();
    }

    @Test
    public void testGetStartDateTime() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02");
        Date startDateTime = autoTempoApp.getStartDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 00:00:00"), startDateTime);
    }

    @Test
    public void testGetEndDateTime() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02");
        Date endDateTime = autoTempoApp.getEndDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 23:59:59"), endDateTime);
    }

    @Test
    public void testGetStartDateTimePm() throws Exception {
        Date date = DateTestUtils.buildDateTime("2015-11-02 13:00");
        Date startDateTime = autoTempoApp.getStartDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 00:00:00"), startDateTime);
    }

    @Test
    public void testGetEndDateTimePm() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02 15:00");
        Date endDateTime = autoTempoApp.getEndDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 23:59:59"), endDateTime);
    }

}