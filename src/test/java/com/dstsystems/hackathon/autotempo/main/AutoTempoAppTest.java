package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.exception.HttpStatusException;
import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.utils.DateTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AutoTempoAppTest {

    @InjectMocks
    @Spy
    private AutoTempoApp autoTempoApp;

    @Test
    public void testLogAppointmentsKnownException() throws Exception {
        AppointmentModel appointment1 = new AppointmentModel();
        AppointmentModel appointment2 = new AppointmentModel();
        List<AppointmentModel> appointments = Arrays.asList(appointment1, appointment2);

        doThrow(new HttpStatusException("", 400)).when(autoTempoApp).logAppointment(appointment1);
        doNothing().when(autoTempoApp).logAppointment(appointment2);

        autoTempoApp.logAppointments(appointments);

        verify(autoTempoApp).logAppointment(appointment1);
        verify(autoTempoApp).logAppointment(appointment2);
    }

    @Test(expected = WorklogException.class)
    public void testLogAppointmentsUnknownException() throws Exception {
        AppointmentModel appointment1 = new AppointmentModel();
        AppointmentModel appointment2 = new AppointmentModel();
        List<AppointmentModel> appointments = Arrays.asList(appointment1, appointment2);

        doThrow(new HttpStatusException("", 404)).when(autoTempoApp).logAppointment(appointment1);
        doNothing().when(autoTempoApp).logAppointment(appointment2);

        autoTempoApp.logAppointments(appointments);
    }

    @Test
    public void testGetStartDateTime() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02");
        Date startDateTime = autoTempoApp.getStartDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 00:00:01"), startDateTime);
    }

    @Test
    public void testGetEndDateTime() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02");
        Date endDateTime = autoTempoApp.getEndDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 23:59:59"), endDateTime);
    }

    @Test
    public void testGetStartDateTimePm() throws Exception {
        Date date = DateTestUtils.buildDateTime("2015-11-02 13:00");
        Date startDateTime = autoTempoApp.getStartDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 00:00:01"), startDateTime);
    }

    @Test
    public void testGetEndDateTimePm() throws Exception {
        Date date = DateTestUtils.buildDate("2015-11-02 15:00");
        Date endDateTime = autoTempoApp.getEndDateTime(date);

        assertEquals(DateTestUtils.buildDateTime("2015-11-02 23:59:59"), endDateTime);
    }

}