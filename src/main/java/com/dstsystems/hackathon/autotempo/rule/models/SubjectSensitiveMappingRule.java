package com.dstsystems.hackathon.autotempo.rule.models;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Appointment Subject based Worklog mapping rule
 * <p>
 * The rule can map the given word within appointment subject to the configured worklog issue id, account and fixed comment
 *
 * @author Tank
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubjectSensitiveMappingRule extends Rule {
    private String subjectWord;
    private String issueKey;
    private String accountKey;
    private String comment;

    @Override
    public String toString() {
        return "SubjectSensitiveMappingRule{" +
                "subjectWord='" + subjectWord + '\'' +
                ", issueKey='" + issueKey + '\'' +
                ", accountKey='" + accountKey + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean isMatch(AppointmentModel appointment) {
        if (appointment.getSubject() == null || getSubjectWord() == null || getSubjectWord().trim().isEmpty() ) {
            return false;
        }
        return appointment.getSubject().toUpperCase().contains(getSubjectWord().toUpperCase());
    }

    @Override
    public void populateWorkingModel(WorklogModel worklog, AppointmentModel appointment) {
        worklog.setAccountKey(this.getAccountKey());
        worklog.setIssueKey(this.getIssueKey());
        worklog.setComment(this.getComment());
    }


    public String getSubjectWord() {
        return subjectWord;
    }

    public void setSubjectWord(String subjectWord) {
        this.subjectWord = subjectWord;
    }


    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
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
