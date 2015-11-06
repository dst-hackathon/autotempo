package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.models.*;
import com.dstsystems.hackathon.autotempo.service.*;
import com.dstsystems.hackathon.autotempo.tempo.TempoSubmitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 06/11/2015.
 */
public class AutoTempoApp {

    public static final String DEFAULT_USER_PROFILE_PATH = "/user.profile";

    private static Date generateDateFromString(String dateInString ) {
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
        UserProfileService userProfileService = new UserProfileService();

        String userProfilePath = "src/test/resources/" + DEFAULT_USER_PROFILE_PATH;

        if (args.length > 0 && args[0] != null) {
            userProfilePath = args[0];
        }

        ExchangeUserProfileModel xchangeUserProfile = userProfileService.getExChangeUserProfile(userProfilePath);
        System.out.println("ExchangeUserProfile loaded");
        TempoUserProfileModel tempoUserProfileModel = userProfileService.getTempoUserProfile(userProfilePath);
        System.out.println("TempoUserProfile loaded");
        Date startDate = generateDateFromString( "11/07/2015 00:00:01" );
        Date endDate = generateDateFromString( "11/07/2015 23:59:59" );
        try {

            List<AppointmentModel> appointmentList = appointmentService.downloadExchangeAppointments(xchangeUserProfile, startDate, endDate);
            System.out.println(appointmentList.size() + " Appointments Loaded");

            AcceptedAppointmentListFilter acceptedAppointmentListFilter = new AcceptedAppointmentListFilter();
            acceptedAppointmentListFilter.filter(appointmentList);
            System.out.println("Appointment filtered: " + appointmentList.size() + " Appointments remaining");

            RuleSet ruleSet = getSimpleRuleSet();
            System.out.println("SimpleRule set");

            for (int i = 0; i < appointmentList.size(); i++ )
            {
                AppointmentModel appointmentModel = appointmentList.get(i);

                WorklogModel worklogModel = new WorklogModel();
                if ( new FirstMatchRuleSetProcessor().process(worklogModel, appointmentModel, ruleSet) ) {
                    System.out.println( appointmentModel.getSubject() + " matches the rule set");
                    WorklogHelper.populateCommon( worklogModel, appointmentModel );
                    worklogModel.setComment(appointmentModel.getSubject());

                    System.out.println( worklogModel.getAccountKey() );
                    System.out.println( worklogModel.getComment() );
                    System.out.println( worklogModel.getIssueKey() );
                    System.out.println( worklogModel.getDate() );
                    System.out.println( worklogModel.getTimeSpent() );

                    new TempoSubmitter(tempoUserProfileModel).submitWorklog(worklogModel);
                    System.out.println( worklogModel.getIssueKey() + " has been logged on " + worklogModel.getDate().toString() + " with time = " + worklogModel.getTimeSpent() + "." );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static RuleSet getSimpleRuleSet() {
        RuleSet ruleSet = new RuleSet();

        SimpleCategoryMappingRule rule = new SimpleCategoryMappingRule();
        rule.setIssueKey("INT-1");
        rule.setAccountKey("ATT02");
        rule.setCategory("Training");

        SimpleCategoryMappingRule rule2 = new SimpleCategoryMappingRule();
        rule2.setIssueKey("TP-1");
        rule2.setAccountKey("ATT01");
        rule2.setCategory("Project");

        ruleSet.setRuleList(Arrays.asList((Rule) rule, rule2));
        return ruleSet;
    }


}
