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

    private static Date generateDateFromString( String dateInString ) {
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

        // To get appointments.
        ExchangeUserProfileModel xchangeUserProfile = userProfileService.getExChangeUserProfile("src/test/resources/user.profile");
        Date startDate = generateDateFromString( "11/07/2015" );
        Date endDate = generateDateFromString( "11/07/2015" );
        try {
            List<AppointmentModel> appointmetList = appointmentService.downloadExchangeAppointments(xchangeUserProfile, startDate, endDate);
            SimpleCategoryMappingRule rule = new SimpleCategoryMappingRule();
            rule.setComment("Test comment");
            rule.setIssueKey("INT-1");
            rule.setAccountKey("ATT02");
            rule.setCategory("INTERNAL");

            RuleSet ruleSet = new RuleSet();
            ruleSet.setRuleList(Arrays.asList((Rule) rule));

            for (int i = 0; i < appointmetList.size(); i++ )
            {
                AppointmentModel appointmentModel = appointmetList.get(i);

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


}
