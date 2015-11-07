package com.dstsystems.hackathon.autotempo.filter;

import com.dstsystems.hackathon.autotempo.exception.AppointmentInputException;
import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConflictAppointmentListFilterTest {

    @Test
    public void testFilterCaseConflict() throws Exception {
        List<AppointmentModel> appointmentModels = Arrays.asList(
                buildAppointment("A", "2015-01-01 07:00", "2015-01-01 09:00"),
                buildAppointment("B", "2015-01-01 16:00", "2015-01-01 18:00"),
                buildAppointment("C", "2015-01-01 08:30", "2015-01-01 09:30")
        );

        try {
            new ConflictAppointmentListFilter().filter(appointmentModels);
        } catch (AppointmentInputException e) {
            assertEquals("Appointment \"A\" conflicts with \"C\". Please resolve the conflict and try again.",
                    e.getMessage());
        }
    }

    @Test
    public void testFilterCaseNoConflict() throws Exception {
        List<AppointmentModel> appointmentModels = Arrays.asList(
                buildAppointment("A", "2015-01-01 07:00", "2015-01-01 09:00"),
                buildAppointment("B", "2015-01-01 16:00", "2015-01-01 18:00"),
                buildAppointment("C", "2015-01-01 09:00", "2015-01-01 09:30")
        );

        // Should not throw exception
        new ConflictAppointmentListFilter().filter(appointmentModels);
    }

    private AppointmentModel buildAppointment(String subject, String start, String end) throws Exception {
        AppointmentModel appointment = new AppointmentModel();
        appointment.setSubject(subject);
        appointment.setStart(DateTestUtils.buildDateTime(start));
        appointment.setEnd(DateTestUtils.buildDateTime(end));
        return appointment;
    }

}