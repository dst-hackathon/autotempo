package com.dstsystems.hackathon.autotempo.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.dstsystems.hackathon.autotempo.filter.AppointmentListFilter;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AutoTempoApp {

    private CommandLineArguments parsedArgs;
    private AppointmentService appointmentService;
    private UserProfileService userProfileService;
    private ExchangeUserProfileModel exchangeUserProfile;
    private FirstMatchRuleSetProcessor ruleSetProcessor;
    private TempoSubmitter tempoSubmitter;

    private List<AppointmentListFilter> appointmentListFilters;

    public static void main(String[] args) {
        new AutoTempoApp().run(args);
    }

    public AutoTempoApp() {
        ruleSetProcessor = new FirstMatchRuleSetProcessor();

        appointmentListFilters = new ArrayList<>();
        appointmentListFilters.add(new AcceptedAppointmentListFilter());
        appointmentListFilters.add(new ConflictAppointmentListFilter());
    }

    public void run(String[] args) {
        parsedArgs = parseArguments(args);

        try {
            initServices();
            loadProfiles();

            List<AppointmentModel> appointmentList = fetchAppointments();
            filterAppointments(appointmentList);

            RuleSetLoader ruleSetLoader = new RuleSetLoader();

            RuleSet ruleSet = ruleSetLoader.getRuleSet(parsedArgs.getRulePath());
            System.out.println("Loaded rule set");

            logAppointments(appointmentList, ruleSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CommandLineArguments parseArguments(String[] args) {
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

        return parsedArgs;
    }

    private void initServices() {
        if (StringUtils.isEmpty(parsedArgs.getJsonPath())) {
            appointmentService = new AppointmentServiceImpl();
        } else {
            appointmentService = new JsonAppointmentService(parsedArgs.getJsonPath());
        }

        userProfileService = new UserProfileServiceImpl();
    }

    private void loadProfiles() {
        exchangeUserProfile = userProfileService.getExchangeUserProfile(parsedArgs.getProfilePath());
        System.out.println("ExchangeUserProfile loaded");
        TempoUserProfileModel tempoUserProfile = userProfileService.getTempoUserProfile(parsedArgs.getProfilePath());
        tempoSubmitter = new TempoSubmitter(tempoUserProfile);
        tempoSubmitter.setDry(parsedArgs.getDry());
        System.out.println("TempoUserProfile loaded");
    }

    private List<AppointmentModel> fetchAppointments() throws Exception {
        Date startDate = getStartDateTime(parsedArgs.getDate());
        Date endDate = getEndDateTime(parsedArgs.getDate());

        System.out.println("Fetching appointments from " + startDate + " to " + endDate);
        List<AppointmentModel> appointmentList = appointmentService.downloadExchangeAppointments(exchangeUserProfile, startDate, endDate);
        System.out.println(appointmentList.size() + " Appointments Loaded");
        System.out.println(appointmentList);
        return appointmentList;
    }

    private void filterAppointments(List<AppointmentModel> appointmentList) {
        for (AppointmentListFilter filter : appointmentListFilters) {
            filter.filter(appointmentList);
        }
        System.out.println("Appointment filtered: " + appointmentList.size() + " Appointments remaining");
        System.out.println("No conflicts found");
    }

    private void logAppointments(List<AppointmentModel> appointmentList, RuleSet ruleSet) throws IOException {
        for (AppointmentModel appointmentModel : appointmentList) {
            WorklogModel worklogModel = new WorklogModel();

            if (ruleSetProcessor.process(worklogModel, appointmentModel, ruleSet)) {
                System.out.println(appointmentModel.getSubject() + " matches the rule set");
                WorklogHelper.populateCommon(worklogModel, appointmentModel);
                worklogModel.setComment(appointmentModel.getSubject());

                System.out.println(worklogModel);

                tempoSubmitter.submitWorklog(worklogModel);
                System.out.println(worklogModel.getIssueKey() + " has been logged on " + worklogModel.getDate().toString() + " with time = " + worklogModel.getTimeSpent() + ".");
            }
        }
    }

    protected Date getStartDateTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 1); // Note: we can't use 0 since it will pick up yesterday's "all day"
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    protected Date getEndDateTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
