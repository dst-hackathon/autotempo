package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.List;

/**
 * A filter to remove unaccepted/declined appointments
 */
public class AcceptedAppointmentListFilter implements AppointmentListFilter {

    @Override
    public void filter(List<AppointmentModel> appointments) {
        CollectionUtils.filter(appointments, new Predicate<AppointmentModel>() {
            @Override
            public boolean evaluate(AppointmentModel appointment) {
                switch (appointment.getMyResponseType()) {
                    case Organizer:
                    case Accept:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
