package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by user on 06/11/2015.
 */
public class UserProfileService implements IUserProfileService {

    private Properties readProperties( String pathFileName ) {

        Properties properties = new Properties();
        try {
            File file = new File(pathFileName);
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public ExchangeUserProfileModel getExChangeUserProfile(String pathFileName) {

        ExchangeUserProfileModel userProfile = new ExchangeUserProfileModel();

        Properties properties = readProperties( pathFileName );
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "exchange.userName":
                    userProfile.setUserName(value);
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
    public TempoUserProfileModel getTempoUserProfile(String pathFileName) {

        TempoUserProfileModel userProfile = new TempoUserProfileModel();

        Properties properties = readProperties( pathFileName );
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "tempo.userName":
                    userProfile.setUserName(value);
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
}
