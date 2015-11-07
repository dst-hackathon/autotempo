package com.dstsystems.hackathon.autotempo.filter;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CanceledAppointmentListFilterTest {

    @Test
    public void testFilter() throws Exception {
        AppointmentModel appointment1 = buildAppointment("ABC");
        AppointmentModel appointment2 = buildAppointment(null);
        AppointmentModel appointment3 = buildAppointment("");
        AppointmentModel appointment4 = buildAppointment("Canceled: ");
        AppointmentModel appointment5 = buildAppointment("Canceled: ABC");
        AppointmentModel appointment6 = buildAppointment("Meeting");

        List<AppointmentModel> appointments = new ArrayList<>();
        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        appointments.add(appointment4);
        appointments.add(appointment5);
        appointments.add(appointment6);

        new CanceledAppointmentListFilter().filter(appointments);

        List<AppointmentModel> expectedAppointments = Arrays.asList(appointment1, appointment2, appointment3,
                appointment6);
        assertEquals(expectedAppointments, appointments);
    }

    private AppointmentModel buildAppointment(String subject) {
        AppointmentModel appointment = new AppointmentModel();
        appointment.setSubject(subject);
        return appointment;
    }

}