package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Alternative AppointmentService to test tempo rules without connecting to Exchange
 */
public class JsonAppointmentService implements AppointmentService {

    @Override
    public List<AppointmentModel> downloadExchangeAppointments(
            ExchangeUserProfileModel userProfile, Date start, Date end) throws Exception {
        File jsonFile = new File("src/main/resources/test_appointments.json");
        return new ObjectMapper().readValue(jsonFile, new TypeReference<List<AppointmentModel>>(){});
    }

}
