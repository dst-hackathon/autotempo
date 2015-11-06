package com.dstsystems.hackathon.autotempo.models;

import java.util.Calendar;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class WorklogHelper {
    public static void populateCommon(WorklogModel worklog, AppointmentModel appointment) {

        Calendar c = Calendar.getInstance();
        c.setTime(appointment.getStart());
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        worklog.setDate(c.getTime());
        worklog.setTimeSpent((appointment.getEnd().getTime() - appointment.getStart().getTime()) / 1000);
    }


}
