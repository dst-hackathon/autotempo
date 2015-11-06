package com.dstsystems.hackathon.autotempo.models;

/**
 * Created by dst on 11/6/2015 AD.
 */
public interface Rule {
    /**
     * Determine if the given appointment object applicable with this rule
     * @param model
     * @return true if applicable
     */
    boolean isMatch(AppointmentModel model);

    /**
     * Do any post processing to the appointment model
     *
     * @param working
     * @param appointment
     */
    void populateWorkingModel(WorklogModel working, AppointmentModel appointment);
}
