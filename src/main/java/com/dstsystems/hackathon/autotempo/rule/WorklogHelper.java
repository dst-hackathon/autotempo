package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import org.apache.commons.lang3.StringUtils;

public class WorklogHelper {

    public static void populateCommon(WorklogModel worklog, AppointmentModel appointment) {
        worklog.setDate(appointment.getStart());
        worklog.setTimeSpent((appointment.getEnd().getTime() - appointment.getStart().getTime()) / 1000);

        if (StringUtils.isEmpty(worklog.getComment())) {
            String comment = appointment.getSubject();

            if (StringUtils.isEmpty(comment)) {
                comment = "Working on issue " + worklog.getIssueKey();
            }

            worklog.setComment(comment);
        }
    }

}
