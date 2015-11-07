package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.filter.ConflictAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ConflictAppointmentListFilterTest {

    @Test(expected = RuntimeException.class)
    public void testFilterCaseConflict() throws Exception {
        List<AppointmentModel> appointmentModels = Arrays.asList(
                buildAppointment("2015-01-01 07:00", "2015-01-01 09:00"),
                buildAppointment("2015-01-01 16:00", "2015-01-01 18:00"),
                buildAppointment("2015-01-01 08:30", "2015-01-01 09:30")
        );

        new ConflictAppointmentListFilter().filter(appointmentModels);
    }

    @Test
    public void testFilterCaseNoConflict() throws Exception {
        List<AppointmentModel> appointmentModels = Arrays.asList(
                buildAppointment("2015-01-01 07:00", "2015-01-01 09:00"),
                buildAppointment("2015-01-01 16:00", "2015-01-01 18:00"),
                buildAppointment("2015-01-01 09:00", "2015-01-01 09:30")
        );

        // Should not throw exception
        new ConflictAppointmentListFilter().filter(appointmentModels);
    }

    private AppointmentModel buildAppointment(String start, String end) throws Exception {
        AppointmentModel appointment = new AppointmentModel();
        appointment.setStart(DateTestUtils.buildDateTime(start));
        appointment.setEnd(DateTestUtils.buildDateTime(end));
        return appointment;
    }

}