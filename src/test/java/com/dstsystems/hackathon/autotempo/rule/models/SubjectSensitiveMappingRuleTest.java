package com.dstsystems.hackathon.autotempo.rule.models;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SubjectSensitiveMappingRuleTest {
    SubjectSensitiveMappingRule rule;
    @Before
    public void setUp() throws Exception {
        rule = new SubjectSensitiveMappingRule();
        rule.setComment("Test comment");
        rule.setIssueKey("issue1");
        rule.setAccountKey("account1");
        rule.setSubjectWord("Meeting");
    }
    @Test
    public void thatRuleMatchSubjectContainingWord() {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setSubject("A very important Meeting");
        assertTrue(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatRuleMatchSubjectContainingWordIgnoreCase() {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setSubject("Another meeting");

        assertTrue(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatRuleDoNotMatchEmptyAppointmentSubject() {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setSubject(null);

        assertFalse(rule.isMatch(appointmentModel));
        appointmentModel.setSubject("");

        assertFalse(rule.isMatch(appointmentModel));
        appointmentModel.setSubject("   ");

        assertFalse(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatRuleWithEmptySubjectWordDoesNotMatchAppointment() {

        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setSubject("Test subject");

        rule.setSubjectWord(null);
        assertFalse(rule.isMatch(appointmentModel));

        rule.setSubjectWord("");
        assertFalse(rule.isMatch(appointmentModel));

        rule.setSubjectWord("   ");
        assertFalse(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatRuleWithWildcardSubjectShouldMatch() {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setSubject("[Important] AMS/BS Weekly PL - meeting xxxxxxx");

        rule.setSubjectWord("AMS*BS*PL - Meeting");
        assertTrue(rule.isMatch(appointmentModel));

        rule.setSubjectWord("AMS*BS*PL - Meetings");
        assertFalse(rule.isMatch(appointmentModel));

    }
}