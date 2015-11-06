package com.dstsystems.hackathon.autotempo.models;

/**
 * Created by dst on 11/6/2015 AD.
 */
public interface Rule {
    boolean isMatch(AppointmentModel model);
    void populateWorkingModel(WorklogModel working, AppointmentModel appointMent);
}
