package com.dstsystems.hackathon.autotempo.main;

import com.beust.jcommander.Parameter;

import java.util.Calendar;
import java.util.Date;

public class CommandLineArguments {

    @Parameter(names = "-profile", description = "Path to user profile")
    private String profilePath = "user.profile";

    @Parameter(names = "-rule", description = "Path to rule configuration")
    private String rulePath = "rules.xml";

    @Parameter(names = "-date", description = "Date to log (default: today's date)")
    private Date date = Calendar.getInstance().getTime();

    @Parameter(names = "-json", description = "Use JSON file instead of connecting to exchange (for debugging)", hidden = true)
    private String jsonPath;

    @Parameter(names = {"-h", "-help"}, description = "Display help message", help = true, hidden = true)
    private boolean help;

    public String getProfilePath() {
        return profilePath;
    }

    public String getRulePath() {
        return rulePath;
    }

    public Date getDate() {
        return date;
    }

    public String getJsonPath() {
        return jsonPath;
    }

}
