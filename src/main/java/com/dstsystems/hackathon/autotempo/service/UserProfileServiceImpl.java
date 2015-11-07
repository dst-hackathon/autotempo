package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by user on 06/11/2015.
 */
public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public ExchangeUserProfileModel getExchangeUserProfile(String pathFileName) throws IOException {
        ExchangeUserProfileModel userProfile = new ExchangeUserProfileModel();

        Properties properties = readProperties(pathFileName);
        Enumeration enuKeys = properties.keys();

        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "exchange.userName":
                    userProfile.setUsername(value);
                    break;
                case "exchange.password":
                    userProfile.setPassword(value);
                    break;
                case "exchange.URL":
                    userProfile.setURL(value);
                    break;
                default:
                    break;
            }
        }

        return userProfile;
    }

    @Override
    public TempoUserProfileModel getTempoUserProfile(String pathFileName) throws IOException {
        TempoUserProfileModel userProfile = new TempoUserProfileModel();

        Properties properties = readProperties(pathFileName);
        Enumeration enuKeys = properties.keys();

        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "tempo.userName":
                    userProfile.setUsername(value);
                    break;
                case "tempo.password":
                    userProfile.setPassword(value);
                    break;
                case "tempo.URL":
                    userProfile.setURL(value);
                    break;
                default:
                    break;
            }
        }

        return userProfile;
    }

    private Properties readProperties(String pathFileName) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fileInput = new FileInputStream(pathFileName);) {
            properties.load(fileInput);
            return properties;
        }
    }

}
