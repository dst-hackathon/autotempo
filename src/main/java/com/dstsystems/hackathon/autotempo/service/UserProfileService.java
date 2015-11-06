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
    @Override
    public UserProfileModel getUserProfile(String pathFileName) {

        UserProfileModel userProfile = new UserProfileModel();
        try {
            File file = new File(pathFileName);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userProfile;
    }
}
