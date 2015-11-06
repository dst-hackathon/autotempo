package com.dstsystems.hackathon.autotempo.models;

import java.util.List;
/**
 * Encapsulate the list of Rules
 */
public class RuleSet {
    private List<Rule> ruleList;

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
}
