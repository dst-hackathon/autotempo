package com.dstsystems.hackathon.autotempo.rule.models;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple Category based Worklog mapping rule
 *
 * The rule can map the given appointment category to the configured worklog issue id, account and fixed comment
 *
 * @author Tank
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleCategoryMappingRule extends Rule{
    private String category;
    private String issueKey;
    private String accountKey;

    @Override
    public String toString() {
        return "SimpleCategoryMappingRule{" +
                "category='" + category + '\'' +
                ", issueKey='" + issueKey + '\'' +
                ", accountKey='" + accountKey + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    private String comment;

    @Override
    public boolean isMatch(AppointmentModel appointment) {
        if (appointment != null && appointment.getCategories() != null && !appointment.getCategories().isEmpty()) {
            for (String appointmentCategory : appointment.getCategories()) {
                if (category.equalsIgnoreCase(appointmentCategory)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void populateWorkingModel(WorklogModel worklog, AppointmentModel appointment)
    {
        worklog.setAccountKey(this.getAccountKey());
        worklog.setIssueKey(this.getIssueKey());
        worklog.setComment(this.getComment());
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
