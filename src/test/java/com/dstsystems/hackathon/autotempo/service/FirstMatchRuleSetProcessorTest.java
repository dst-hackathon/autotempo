package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.rule.FirstMatchRuleSetProcessor;
import com.dstsystems.hackathon.autotempo.rule.models.Rule;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FirstMatchRuleSetProcessorTest {
    private RuleSet ruleSet = new RuleSet();
    private FirstMatchRuleSetProcessor processor = new FirstMatchRuleSetProcessor();

    @Mock
    Rule mockRule1;
    @Mock
    Rule mockRule2;
    @Mock
    Rule mockRule3;

    @Mock
    WorklogModel worklog;

    @Mock
    AppointmentModel appointmentModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Rule> ruleList = new ArrayList<Rule>();
        ruleList.add(mockRule1);
        ruleList.add(mockRule2);
        ruleList.add(mockRule3);

        ruleSet.setRuleList(ruleList);
    }

    @Test
    public void thatRuleStopAtFirst() {
        doReturn(false).when(mockRule1).isMatch(any(AppointmentModel.class));
        doReturn(true).when(mockRule2).isMatch(any(AppointmentModel.class));
        doReturn(true).when(mockRule3).isMatch(any(AppointmentModel.class));
        assertTrue( processor.process(worklog, appointmentModel, ruleSet) );
        verify(mockRule1, never()).populateWorkingModel(worklog, appointmentModel);
        verify(mockRule2).populateWorkingModel(worklog, appointmentModel);
        verify(mockRule3, never()).populateWorkingModel(worklog, appointmentModel);
    }
}