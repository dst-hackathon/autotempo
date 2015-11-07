package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.rule.models.Rule;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class FirstMatchRuleSetProcessor {
    public boolean process(WorklogModel worklog, AppointmentModel appointment, RuleSet ruleSet ) {
        for (Rule rule : ruleSet.getRuleList()) {
            if (rule.isMatch(appointment)) {
                rule.populateWorkingModel( worklog, appointment);

                return true;
            }
        }
        return false;
    }
}
