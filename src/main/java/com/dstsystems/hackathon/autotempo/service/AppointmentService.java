package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<AppointmentModel> downloadExchangeAppointments(Date start, Date end);
}
