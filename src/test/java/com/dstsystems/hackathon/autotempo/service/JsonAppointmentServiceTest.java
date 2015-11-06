package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class JsonAppointmentServiceTest {

    @Test
    public void testDownloadExchangeAppointments() throws Exception {
        // Just verify that we can read it for now
        List<AppointmentModel> appointmentModels =
                new JsonAppointmentService().downloadExchangeAppointments(null, null, null);
        assertNotNull(appointmentModels);
    }

}