package com.dstsystems.hackathon.autotempo.rule;

import com.dstsystems.hackathon.autotempo.rule.models.RuleSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by dst on 11/6/2015 AD.
 */
public class RuleSetLoader {

    public RuleSet getRuleSet(String path) {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(RuleSet.class);

            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

            return (RuleSet) jaxbMarshaller.unmarshal(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
