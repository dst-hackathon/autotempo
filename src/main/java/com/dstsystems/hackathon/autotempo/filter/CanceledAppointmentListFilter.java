package com.dstsystems.hackathon.autotempo.filter;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.List;

/**
 * A filter to remove unaccepted/declined appointments
 */
public class CanceledAppointmentListFilter implements AppointmentListFilter {

    public static final String CANCELED_PREFIX = "Canceled: ";

    @Override
    public void filter(List<AppointmentModel> appointments) {
        CollectionUtils.filter(appointments, new Predicate<AppointmentModel>() {
            @Override
            public boolean evaluate(AppointmentModel appointment) {
                if (appointment.getSubject() == null) {
                    // Ignore if subject is null
                    return true;
                }

                return !appointment.getSubject().startsWith(CANCELED_PREFIX);
            }
        });
    }

}
