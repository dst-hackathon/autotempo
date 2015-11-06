package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import microsoft.exchange.webservices.data.core.enumeration.property.MeetingResponseType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author pawitp
 */
public class AcceptedAppointmentListFilterTest {

    @Test
    public void testFilter() throws Exception {
        AppointmentModel appointment1 = buildAppointment(MeetingResponseType.Accept);
        AppointmentModel appointment2 = buildAppointment(MeetingResponseType.Decline);
        AppointmentModel appointment3 = buildAppointment(MeetingResponseType.NoResponseReceived);
        AppointmentModel appointment4 = buildAppointment(MeetingResponseType.Organizer);
        AppointmentModel appointment5 = buildAppointment(MeetingResponseType.Tentative);
        AppointmentModel appointment6 = buildAppointment(MeetingResponseType.Unknown);

        List<AppointmentModel> appointments = new ArrayList<>();
        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        appointments.add(appointment4);
        appointments.add(appointment5);
        appointments.add(appointment6);

        new AcceptedAppointmentListFilter().filter(appointments);

        List<AppointmentModel> expectedAppointments = Arrays.asList(appointment1, appointment4, appointment6);
        assertEquals(expectedAppointments, appointments);
    }

    private AppointmentModel buildAppointment(MeetingResponseType accept) {
        AppointmentModel appointment = new AppointmentModel();
        appointment.setMyResponseType(accept);
        return appointment;
    }

}