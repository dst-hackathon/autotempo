package com.dstsystems.hackathon.autotempo.filter;

import com.dstsystems.hackathon.autotempo.exception.AppointmentInputException;
import com.dstsystems.hackathon.autotempo.models.AppointmentModel;

import java.util.List;

public interface AppointmentListFilter {

    void filter(List<AppointmentModel> appointments) throws AppointmentInputException;

}
