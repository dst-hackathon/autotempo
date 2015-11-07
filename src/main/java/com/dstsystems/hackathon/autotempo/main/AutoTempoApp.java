package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.models.*;
import com.dstsystems.hackathon.autotempo.service.*;
import com.dstsystems.hackathon.autotempo.tempo.TempoSubmitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 06/11/2015.
 */
public class AutoTempoApp {

    public static final String DEFAULT_USER_PROFILE_PATH = "/user.profile";

    private static Date generateDateFromString(String dateInString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {

        //JsonAppointmentService appointmentService = new JsonAppointmentService();
        AppointmentService appointmentService = new AppointmentServiceImpl();
        UserProfileServiceImpl userProfileService = new UserProfileServiceImpl();

        String userProfilePath = "src/test/resources/" + DEFAULT_USER_PROFILE_PATH;

        if (args.length > 0 && args[0] != null) {
            userProfilePath = args[0];
        }

        ExchangeUserProfileModel exchangeUserProfile = userProfileService.getExchangeUserProfile(userProfilePath);
        System.out.println("ExchangeUserProfile loaded");
        TempoUserProfileModel tempoUserProfileModel = userProfileService.getTempoUserProfile(userProfilePath);
        System.out.println("TempoUserProfile loaded");
        Date startDate = generateDateFromString("11/07/2015 00:00:01");
        Date endDate = generateDateFromString("11/07/2015 23:59:59");

        try {
            List<AppointmentModel> appointmentList = appointmentService.downloadExchangeAppointments(exchangeUserProfile, startDate, endDate);
            System.out.println(appointmentList.size() + " Appointments Loaded");
            System.out.println(appointmentList);

            AcceptedAppointmentListFilter acceptedAppointmentListFilter = new AcceptedAppointmentListFilter();
            acceptedAppointmentListFilter.filter(appointmentList);
            System.out.println("Appointment filtered: " + appointmentList.size() + " Appointments remaining");

            ConflictAppointmentListFilter conflictAppointmentListFilter = new ConflictAppointmentListFilter();
            conflictAppointmentListFilter.filter(appointmentList);
            System.out.println("No conflicts found");

            RuleSetLoader ruleSetLoader = new RuleSetLoader();
            RuleSet ruleSet = ruleSetLoader.getRuleSet("src/main/resources/Rules.xml");
            System.out.println("SimpleRule set");

            for (AppointmentModel appointmentModel : appointmentList) {
                WorklogModel worklogModel = new WorklogModel();
                if (new FirstMatchRuleSetProcessor().process(worklogModel, appointmentModel, ruleSet)) {
                    System.out.println(appointmentModel.getSubject() + " matches the rule set");
                    WorklogHelper.populateCommon(worklogModel, appointmentModel);
                    worklogModel.setComment(appointmentModel.getSubject());

                    System.out.println(worklogModel);

                    new TempoSubmitter(tempoUserProfileModel).submitWorklog(worklogModel);
                    System.out.println(worklogModel.getIssueKey() + " has been logged on " + worklogModel.getDate().toString() + " with time = " + worklogModel.getTimeSpent() + ".");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
