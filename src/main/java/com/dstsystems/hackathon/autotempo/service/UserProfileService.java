package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.UserProfileModel;

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
    public UserProfileModel getExChangeUserProfile(String pathFileName) {

        UserProfileModel userProfile = new UserProfileModel();

        Properties properties = readProperties( pathFileName );
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "userName":
                    userProfile.setUserName(value);
                    break;
                case "password":
                    userProfile.setPassword(value);
                    break;
                case "URL":
                    userProfile.setURL(value);
                    break;
                default:
                    break;
            }
        }

        return userProfile;
    }

    @Override
    public UserProfileModel getTempoUserProfile(String pathFileName) {

        UserProfileModel userProfile = new UserProfileModel();

        Properties properties = readProperties( pathFileName );
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            switch (key) {
                case "userName":
                    userProfile.setUserName(value);
                    break;
                case "password":
                    userProfile.setPassword(value);
                    break;
                case "URL":
                    userProfile.setURL(value);
                    break;
                default:
                    break;
            }
        }

        return userProfile;
    }
}
