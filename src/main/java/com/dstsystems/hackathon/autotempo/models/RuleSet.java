package com.dstsystems.hackathon.autotempo.models;

import javax.xml.bind.annotation.*;
import java.util.List;
/**
 * Encapsulate the list of Rules
 */
@XmlRootElement(name="RuleSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleSet {
    @XmlElementWrapper(name = "ruleList")
    @XmlElements( {
            @XmlElement(
                    name="SimpleCategoryMappingRule", type=SimpleCategoryMappingRule.class) ,
            @XmlElement(
                    name="SubjectSensitiveMappingRule", type=SubjectSensitiveMappingRule.class) })

    private List<Rule> ruleList;


    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
}
