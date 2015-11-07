package com.dstsystems.hackathon.autotempo.rule.models;

import com.dstsystems.hackathon.autotempo.models.AppointmentModel;
import com.dstsystems.hackathon.autotempo.models.WorklogModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dst on 11/6/2015 AD.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubjectSensitiveMappingRule extends Rule {
    private String subjectWord;
    @Override
    public boolean isMatch(AppointmentModel model) {
        return false;
    }

    @Override
    public void populateWorkingModel(WorklogModel working, AppointmentModel appointment) {

    }

    @Override
    public String toString() {
        return "SubjectSensitiveMappingRule{" +
                "subjectWord='" + subjectWord + '\'' +
                '}';
    }

    public String getSubjectWord() {
        return subjectWord;
    }

    public void setSubjectWord(String subjectWord) {
        this.subjectWord = subjectWord;
    }


}
