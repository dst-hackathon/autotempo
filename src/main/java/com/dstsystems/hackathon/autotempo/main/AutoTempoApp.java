package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.models.*;
import com.dstsystems.hackathon.autotempo.rule.FirstMatchRuleSetProcessor;
import com.dstsystems.hackathon.autotempo.rule.RuleSetLoader;
import com.dstsystems.hackathon.autotempo.rule.WorklogHelper;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.service.*;
import com.dstsystems.hackathon.autotempo.filter.AcceptedAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.filter.ConflictAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.tempo.TempoSubmitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 06/11/2015.
 */
public class AutoTempoApp {

    public static final String DEFAULT_USER_PROFILE_PATH = "/user.profile";

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
        Date startDate = getStartDate();
        Date endDate = getEndDate();

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

            String rulePath = "src/main/resources/Rules.xml";
            if (args.length > 1 && args[1] != null) {
                rulePath = args[1];
            }

            RuleSetLoader ruleSetLoader = new RuleSetLoader();

            RuleSet ruleSet = ruleSetLoader.getRuleSet(rulePath);
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

    private static Date getStartDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 1);
        return c.getTime();
    }

    private static Date getEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
