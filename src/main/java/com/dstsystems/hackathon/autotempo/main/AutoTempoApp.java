package com.dstsystems.hackathon.autotempo.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.dstsystems.hackathon.autotempo.exception.ConfigurationException;
import com.dstsystems.hackathon.autotempo.filter.AcceptedAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.filter.AppointmentListFilter;
import com.dstsystems.hackathon.autotempo.filter.ConflictAppointmentListFilter;
import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import com.dstsystems.hackathon.autotempo.rule.FirstMatchRuleSetProcessor;
import com.dstsystems.hackathon.autotempo.rule.RuleSetLoader;
import com.dstsystems.hackathon.autotempo.rule.WorklogHelper;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.service.*;
import com.dstsystems.hackathon.autotempo.tempo.TempoSubmitter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords;

public class AutoTempoApp {

    private CommandLineArguments parsedArgs;
    private AppointmentService appointmentService;
    private UserProfileService userProfileService;
    private RuleSet ruleSet;
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

            List<AppointmentModel> appointmentList = fetchAppointments();
            filterAppointments(appointmentList);

            logAppointments(appointmentList);
        } catch (ConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("An unknown error has occurred:");
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

    private void initServices() throws Exception {
        userProfileService = new UserProfileServiceImpl();

        if (StringUtils.isEmpty(parsedArgs.getJsonPath())) {
            ExchangeUserProfileModel exchangeUserProfile =
                    userProfileService.getExchangeUserProfile(parsedArgs.getProfilePath());
            appointmentService = new ExchangeAppointmentService(exchangeUserProfile);
            System.out.println("Loaded Exchange profile");
        } else {
            appointmentService = new JsonAppointmentService(parsedArgs.getJsonPath());
            System.out.println("Loaded JsonAppointmentService");
        }

        TempoUserProfileModel tempoUserProfile = userProfileService.getTempoUserProfile(parsedArgs.getProfilePath());
        tempoSubmitter = new TempoSubmitter(tempoUserProfile);
        System.out.println("Loaded Tempo profile");

        if (parsedArgs.getDry()) {
            tempoSubmitter.setDry(true);
            System.out.println("Performing dry run. Worklogs will not be created.");
        }

        RuleSetLoader ruleSetLoader = new RuleSetLoader();
        ruleSet = ruleSetLoader.getRuleSet(parsedArgs.getRulePath());
        System.out.println("Loaded rules");
        System.out.println();
    }

    private List<AppointmentModel> fetchAppointments() throws Exception {
        Date startDate = getStartDateTime(parsedArgs.getDate());
        Date endDate = getEndDateTime(parsedArgs.getDate());

        System.out.println("Fetching appointments from " + startDate + " to " + endDate);
        List<AppointmentModel> appointmentList = appointmentService.getAppointments(startDate, endDate);
        System.out.println(appointmentList.size() + " appointment(s) loaded");
        for (AppointmentModel appointmentModel : appointmentList) {
            System.out.println(" - " + appointmentModel);
        }
        System.out.println();

        return appointmentList;
    }

    private void filterAppointments(List<AppointmentModel> appointmentList) {
        for (AppointmentListFilter filter : appointmentListFilters) {
            filter.filter(appointmentList);
        }
        System.out.println("Appointments filtered: " + appointmentList.size() + " appointments remaining");
    }

    protected void logAppointments(List<AppointmentModel> appointmentList) throws IOException {
        for (AppointmentModel appointmentModel : appointmentList) {
            try {
                logAppointment(appointmentModel);
            } catch (IOException e) {
                System.out.println("Unable to log " + appointmentModel + ". " + e.getMessage());
            }
        }
    }

    protected void logAppointment(AppointmentModel appointmentModel) throws IOException {
        WorklogModel worklogModel = new WorklogModel();

        if (ruleSetProcessor.process(worklogModel, appointmentModel, ruleSet)) {
            WorklogHelper.populateCommon(worklogModel, appointmentModel);

            System.out.println("Got " + worklogModel);

            tempoSubmitter.submitWorklog(worklogModel);
            System.out.println(worklogModel.getIssueKey() + " has been logged on " + worklogModel.getDate()
                    + " for " + formatDurationWords(worklogModel.getTimeSpent() * 1000, true, true) + ".");
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
