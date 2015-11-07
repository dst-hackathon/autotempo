package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
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

    @Test
    public void testPopulateCommonWithNoComment() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setStart(fmt.parse("2013-10-10T00:00:10"));
        appointmentModel.setEnd(fmt.parse("2013-10-10T00:00:20"));
        appointmentModel.setSubject("ELDS");

        WorklogModel worklog = new WorklogModel();
        WorklogHelper.populateCommon(worklog, appointmentModel);

        assertEquals(10, worklog.getTimeSpent());

        assertEquals("2013-10-10T00:00:00", fmt.format(worklog.getDate()) );
        assertEquals("ELDS", worklog.getComment());

    }

    @Test
    public void testPopulateCommonWithNoCommentNoSubject() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setStart(fmt.parse("2013-10-10T00:00:10"));
        appointmentModel.setEnd(fmt.parse("2013-10-10T00:00:20"));

        WorklogModel worklog = new WorklogModel();
        worklog.setIssueKey("INT-05");
        WorklogHelper.populateCommon(worklog, appointmentModel);

        assertEquals(10, worklog.getTimeSpent());

        assertEquals("2013-10-10T00:00:00", fmt.format(worklog.getDate()) );
        assertEquals("Working on issue INT-05", worklog.getComment());

    }
}