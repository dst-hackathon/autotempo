package com.dstsystems.hackathon.autotempo.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.dstsystems.hackathon.autotempo.models.*;
import com.dstsystems.hackathon.autotempo.rule.FirstMatchRuleSetProcessor;
import com.dstsystems.hackathon.autotempo.rule.RuleSetLoader;
import com.dstsystems.hackathon.autotempo.rule.WorklogHelper;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.service.*;
import com.dstsystems.hackathon.autotempo.filter.AcceptedAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.filter.ConflictAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.tempo.TempoSubmitter;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 06/11/2015.
 */
public class AutoTempoApp {

    public static void main(String[] args) {
        CommandLineArguments parsedArgs = new CommandLineArguments();
        JCommander jCommander = new JCommander(parsedArgs);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            jCommander.usage();
            System.exit(1);
        }

        AppointmentService appointmentService;
        if (StringUtils.isEmpty(parsedArgs.getJsonPath())) {
            appointmentService = new AppointmentServiceImpl();
        } else {
            appointmentService = new JsonAppointmentService(parsedArgs.getJsonPath());
        }

        UserProfileServiceImpl userProfileService = new UserProfileServiceImpl();

        ExchangeUserProfileModel exchangeUserProfile = userProfileService.getExchangeUserProfile(parsedArgs.getProfilePath());
        System.out.println("ExchangeUserProfile loaded");
        TempoUserProfileModel tempoUserProfileModel = userProfileService.getTempoUserProfile(parsedArgs.getProfilePath());
        System.out.println("TempoUserProfile loaded");

        Date startDate = getStartDateTime(parsedArgs.getDate());
        Date endDate = getEndDateTime(parsedArgs.getDate());

        try {
            System.out.println("Fetching appointments from " + startDate + " to " + endDate);
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

            RuleSet ruleSet = ruleSetLoader.getRuleSet(parsedArgs.getRulePath());
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

    private static Date getStartDateTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 1);
        return c.getTime();
    }

    private static Date getEndDateTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
