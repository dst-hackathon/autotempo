package com.dstsystems.hackathon.autotempo.services;

import com.dstsystems.hackathon.autotempo.models.UserProfileModel;
import com.dstsystems.hackathon.autotempo.service.UserProfileService;
import org.junit.Test;

/**
 * Created by user on 06/11/2015.
 */
public class UserProfileServiceTest {

    @Test
    public void getUserProfile() {
        UserProfileService service = new UserProfileService();

        UserProfileModel userProfileModel = service.getUserProfile("src/main/resources/dt66277.profile");
        System.out.println("user = " + userProfileModel.getUserName());
        System.out.println("password = " + userProfileModel.getPassword());
        System.out.println("URL = " + userProfileModel.getURL());
    }
}
