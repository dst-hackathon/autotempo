package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.rule.models.Rule;
import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;
import com.dstsystems.hackathon.autotempo.rule.models.SimpleCategoryMappingRule;
import com.dstsystems.hackathon.autotempo.rule.models.SubjectSensitiveMappingRule;
import com.dstsystems.hackathon.autotempo.rule.RuleSetLoader;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RuleSetLoaderTest {

    @Test
    public void testLoadRuleSet() throws Exception {
        RuleSetLoader ruleSetLoader = new RuleSetLoader();
        RuleSet ruleSet = ruleSetLoader.getRuleSet("src/test/resources/test_rules.xml");
        List<Rule> ruleList = ruleSet.getRuleList();

        assertEquals(3, ruleList.size());
        assertEquals(SimpleCategoryMappingRule.class, ruleList.get(0).getClass());
        SimpleCategoryMappingRule categoryRule = (SimpleCategoryMappingRule) ruleList.get(0);
        assertEquals("Training", categoryRule.getCategory());
        assertEquals("INT-1", categoryRule.getIssueKey());
        assertEquals("account1", categoryRule.getAccountKey());
        assertEquals("Training", categoryRule.getComment());

        assertEquals(SimpleCategoryMappingRule.class, ruleList.get(1).getClass());
        categoryRule = (SimpleCategoryMappingRule) ruleList.get(1);
        assertEquals("training2", categoryRule.getCategory());
        assertEquals("INT-2", categoryRule.getIssueKey());
        assertEquals("account2", categoryRule.getAccountKey());
        assertEquals("Training2", categoryRule.getComment());

        assertEquals(SubjectSensitiveMappingRule.class, ruleList.get(2).getClass());
        SubjectSensitiveMappingRule subjectRule = (SubjectSensitiveMappingRule) ruleList.get(2);
        assertEquals("important", subjectRule.getSubjectWord());
        assertEquals("INT-3", subjectRule.getIssueKey());
        assertEquals("account3", subjectRule.getAccountKey());
        assertEquals("Important Stuff", subjectRule.getComment());
    }

    @Test(expected = JAXBException.class)
    public void testLoadRuleSetNotFound() throws JAXBException {
        RuleSetLoader ruleSetLoader = new RuleSetLoader();
        ruleSetLoader.getRuleSet("not found");
    }

}