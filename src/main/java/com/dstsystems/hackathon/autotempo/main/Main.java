package com.dstsystems.hackathon.autotempo.main;

import com.dstsystems.hackathon.autotempo.models.*;
import com.dstsystems.hackathon.autotempo.service.AppointmentServiceImpl;
import com.dstsystems.hackathon.autotempo.service.FirstMatchRuleSetProcessor;
import com.dstsystems.hackathon.autotempo.service.UserProfileService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 06/11/2015.
 */
public class Main {

    public static final String DEFAULT_USER_PROFILE_PATH = "/user.profile";

    private static Date generateDateFromString(String dateInString ) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {

        AppointmentServiceImpl appointmentService = new AppointmentServiceImpl();
        UserProfileService userProfileService = new UserProfileService();

        String userProfilePath = DEFAULT_USER_PROFILE_PATH;

        if (args.length > 0 && args[0] != null) {
            userProfilePath = args[0];
        }

        // To get appointments.
        ExchangeUserProfileModel xchangeUserProfile = userProfileService.getExChangeUserProfile(userProfilePath);
        Date startDate = generateDateFromString( "11/07/2015" );
        Date endDate = generateDateFromString( "11/07/2015" );
        try {
            List<AppointmentModel> appointmentList = appointmentService.downloadExchangeAppointments(xchangeUserProfile, startDate, endDate);
            RuleSet ruleSet = getSimpleRuleSet();

            for (int i = 0; i < appointmentList.size(); i++ )
            {
                AppointmentModel appointmentModel = appointmentList.get(i);

                WorklogModel worklogModel = new WorklogModel();
                new FirstMatchRuleSetProcessor().process(worklogModel, appointmentModel, ruleSet);

                System.out.println( worklogModel.getAccountKey() );

                System.out.println( worklogModel.getComment() );

                System.out.println( worklogModel.getIssueKey() );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static RuleSet getSimpleRuleSet() {
        SimpleCategoryMappingRule rule = new SimpleCategoryMappingRule();
        rule.setComment("Test comment");
        rule.setIssueKey("INT-1");
        rule.setAccountKey("ATT02");
        rule.setCategory("INTERNAL");

        RuleSet ruleSet = new RuleSet();
        ruleSet.setRuleList(Arrays.asList((Rule) rule));
        return ruleSet;
    }


}
