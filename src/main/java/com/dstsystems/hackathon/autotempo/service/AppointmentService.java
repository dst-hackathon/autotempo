package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.UserProfileModel;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<AppointmentModel> downloadExchangeAppointments(UserProfileModel userProfile, Date start, Date end) throws Exception;
}
