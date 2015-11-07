package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.exception.AppointmentServiceException;
import com.dstsystems.hackathon.autotempo.models.AppointmentModel;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    List<AppointmentModel> getAppointments(Date start, Date end) throws AppointmentServiceException;

}
