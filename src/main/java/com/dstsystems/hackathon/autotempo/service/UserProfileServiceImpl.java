package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public ExchangeUserProfileModel getExchangeUserProfile(String pathFileName) throws IOException {
        Properties properties = readProperties(pathFileName);

        ExchangeUserProfileModel userProfile = new ExchangeUserProfileModel();
        userProfile.setUsername(properties.getProperty("exchange.userName"));
        userProfile.setPassword(properties.getProperty("exchange.password"));
        userProfile.setURL(properties.getProperty("exchange.URL"));

        if (userProfile.getUsername() == null) {
            throw new IOException("Unable to load Exchange username from configuration file");
        }
        if (userProfile.getPassword() == null) {
            throw new IOException("Unable to load Exchange password from configuration file");
        }
        if (userProfile.getURL() == null) {
            throw new IOException("Unable to load Exchange URL from configuration file");
        }

        return userProfile;
    }

    @Override
    public TempoUserProfileModel getTempoUserProfile(String pathFileName) throws IOException {
        Properties properties = readProperties(pathFileName);

        TempoUserProfileModel userProfile = new TempoUserProfileModel();
        userProfile.setUsername(properties.getProperty("tempo.userName"));
        userProfile.setPassword(properties.getProperty("tempo.password"));
        userProfile.setURL(properties.getProperty("tempo.URL"));

        if (userProfile.getUsername() == null) {
            throw new IOException("Unable to load Tempo username from configuration file");
        }
        if (userProfile.getPassword() == null) {
            throw new IOException("Unable to load Tempo password from configuration file");
        }
        if (userProfile.getURL() == null) {
            throw new IOException("Unable to load Tempo URL from configuration file");
        }

        return userProfile;
    }

    private Properties readProperties(String pathFileName) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fileInput = new FileInputStream(pathFileName)) {
            properties.load(fileInput);
            return properties;
        }
    }

}
