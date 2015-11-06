package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.Rule;
import com.dstsystems.hackathon.autotempo.models.RuleSet;
import com.dstsystems.hackathon.autotempo.models.SimpleCategoryMappingRule;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;
import java.util.List;

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
