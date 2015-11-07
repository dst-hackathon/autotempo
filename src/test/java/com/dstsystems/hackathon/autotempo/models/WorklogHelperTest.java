package com.dstsystems.hackathon.autotempo.models;

import com.dstsystems.hackathon.autotempo.rule.WorklogHelper;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class WorklogHelperTest {
    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
    }
    @Test
    public void testPopulateCommon() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setStart(fmt.parse("2013-10-10T00:00:10"));
        appointmentModel.setEnd(fmt.parse("2013-10-10T00:00:20"));

        WorklogModel worklog = new WorklogModel();
        WorklogHelper.populateCommon(worklog, appointmentModel);

        assertEquals(10, worklog.getTimeSpent());

        assertEquals("2013-10-10T00:00:00", fmt.format(worklog.getDate()) );

    }
}