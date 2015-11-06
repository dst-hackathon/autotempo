package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.UserProfileModel;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.apache.commons.collections4.IteratorUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public List<AppointmentModel> downloadExchangeAppointments(UserProfileModel userProfile, Date start, Date end)
            throws Exception {

        WebCredentials webCredentials = new WebCredentials(userProfile.getUserName(), userProfile.getPassword());

        ExchangeService exchangeService = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        exchangeService.setUrl(new URI(userProfile.getURL()));

        CalendarFolder calendarFolder = CalendarFolder.bind(exchangeService, WellKnownFolderName.Calendar);

        FindItemsResults<Appointment> findItemsResults = calendarFolder.findAppointments(new CalendarView(start, end));

        List<AppointmentModel> appointmentModels = new ArrayList<>();

        for (Appointment appointment: findItemsResults.getItems()) {
            AppointmentModel appointmentModel = new AppointmentModel();

            appointmentModel.setSubject(appointment.getSubject());
            appointmentModel.setStart(appointment.getStart());
            appointmentModel.setEnd(appointment.getEnd());
            appointmentModel.setCategories(getCategoryStrings(appointment));

            appointmentModels.add(appointmentModel);
        }

        return appointmentModels;
    }

    private List<String> getCategoryStrings(Appointment appointment) throws ServiceLocalException {
        return IteratorUtils.toList(appointment.getCategories().getIterator());
    }
}
