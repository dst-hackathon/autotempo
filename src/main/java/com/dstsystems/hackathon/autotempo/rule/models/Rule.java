package com.dstsystems.hackathon.autotempo.rule.models;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

/**
 * Created by dst on 11/6/2015 AD.
 */
public abstract class Rule {
    /**
     * Determine if the given appointment object applicable with this rule
     * @param model
     * @return true if applicable
     */
    public abstract boolean isMatch(AppointmentModel model);

    /**
     * Do any post processing to the appointment model
     *
     * @param working
     * @param appointment
     */
    public abstract void populateWorkingModel(WorklogModel working, AppointmentModel appointment);
}
