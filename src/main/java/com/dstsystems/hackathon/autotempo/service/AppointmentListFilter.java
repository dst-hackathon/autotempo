package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;

import java.util.List;

public interface AppointmentListFilter {

    void filter(List<AppointmentModel> appointments);

}
