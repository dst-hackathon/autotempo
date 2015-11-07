package com.dstsystems.hackathon.autotempo.filter;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConflictAppointmentListFilter implements AppointmentListFilter {

    @Override
    public void filter(List<AppointmentModel> appointments) {
        // Sort by start time
        List<AppointmentModel> sortedAppointments = new ArrayList<>();
        sortedAppointments.addAll(appointments);
        Collections.sort(sortedAppointments, new Comparator<AppointmentModel>() {
            @Override
            public int compare(AppointmentModel o1, AppointmentModel o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        // Check for conflict
        for (int i = 0; i < sortedAppointments.size() - 1; i++) {
            AppointmentModel cur = sortedAppointments.get(i);
            AppointmentModel next = sortedAppointments.get(i + 1);

            if (next.getStart().compareTo(cur.getEnd()) < 0) {
                throw new RuntimeException("Appointment " + cur + " conflicts with " + next);
            }
        }
    }

}
