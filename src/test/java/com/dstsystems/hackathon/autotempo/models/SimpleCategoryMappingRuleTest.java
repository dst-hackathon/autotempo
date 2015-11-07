package com.dstsystems.hackathon.autotempo.models;

import com.dstsystems.hackathon.autotempo.rule.models.SimpleCategoryMappingRule;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class SimpleCategoryMappingRuleTest {
    SimpleCategoryMappingRule rule;
    @Before
    public void setUp() throws Exception {
        rule = new SimpleCategoryMappingRule();
        rule.setComment("Test comment");
        rule.setIssueKey("issue1");
        rule.setAccountKey("account1");
        rule.setCategory("training");
    }

    @Test
    public void thatValidCategoryShouldMatch() throws Exception {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setCategories(Arrays.asList("training"));

        assertTrue(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatValidCategoryShouldMatchCaseInsensitive() throws Exception {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setCategories(Arrays.asList("Training"));

        assertTrue(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatInvalidCatagotyShouldNotMatch() throws Exception {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setCategories(Arrays.asList("meeting"));

        assertFalse(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatAppointmentWithNoCategoryShouldNotMatch() throws Exception {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setCategories(Collections.<String>emptyList());

        assertFalse(rule.isMatch(appointmentModel));
    }

    @Test
    public void thatRuleShouldPopulateWorkingModel() throws Exception {
        WorklogModel worklog = new WorklogModel();
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setCategories(Arrays.asList("training"));
        rule.populateWorkingModel(worklog, appointmentModel);
        assertEquals("account1", worklog.getAccountKey());
        assertEquals("issue1", worklog.getIssueKey());
        assertEquals("Test comment", worklog.getComment());
    }
}