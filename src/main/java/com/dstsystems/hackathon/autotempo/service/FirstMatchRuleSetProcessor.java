package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.Rule;
import com.dstsystems.hackathon.autotempo.models.RuleSet;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class FirstMatchRuleSetProcessor {
    public void process(WorklogModel worklog, AppointmentModel appointment, RuleSet ruleSet ) {
        for (Rule rule : ruleSet.getRuleList()) {
            if (rule.isMatch(appointment)) {
                rule.populateWorkingModel( worklog, appointment);

                return;
            }
        }
    }
}