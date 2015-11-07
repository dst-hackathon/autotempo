package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonAppointmentServiceTest {

    @Test
    public void testDownloadExchangeAppointments() throws Exception {
        String jsonPath = "src/test/resources/test_appointments.json";
        List<AppointmentModel> appointmentModels = new JsonAppointmentService(jsonPath).getAppointments(null, null);

        assertEquals(2, appointmentModels.size());
        assertEquals("DST Hackathon", appointmentModels.get(0).getSubject());
        assertEquals("Project", appointmentModels.get(1).getCategories().get(0));
    }

}