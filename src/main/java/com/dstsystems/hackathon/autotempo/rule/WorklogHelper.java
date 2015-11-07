package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

import java.util.Calendar;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class WorklogHelper {

    public static void populateCommon(WorklogModel worklog, AppointmentModel appointment) {
        Calendar c = Calendar.getInstance();
        c.setTime(appointment.getStart());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        worklog.setDate(c.getTime());
        worklog.setTimeSpent((appointment.getEnd().getTime() - appointment.getStart().getTime()) / 1000);
        if (null == worklog.getComment() || worklog.getComment().length() == 0) {
            String comment = "";
            if (null != appointment.getSubject() && appointment.getSubject().length() > 0 )
                comment = appointment.getSubject();
            else
                comment = "Working on issue " + worklog.getIssueKey();
            worklog.setComment( comment );
        }
    }

}
