package com.dstsystems.hackathon.autotempo.services;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;
import com.dstsystems.hackathon.autotempo.service.UserProfileService;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 06/11/2015.
 */
public class UserProfileServiceTest {

    ExchangeUserProfileModel xchangeUserProfileModel;
    TempoUserProfileModel tempoUserProfileModel;

    @Before
    public void setupExchangeUserProfile() {
        UserProfileService service = new UserProfileService();

        xchangeUserProfileModel = service.getExChangeUserProfile("src/test/resources/user.profile");
    }

    @Before
    public void setupTempoUserProfile() {
        UserProfileService service = new UserProfileService();

        tempoUserProfileModel = service.getTempoUserProfile("src/test/resources/user.profile");
    }

    @Test
    public void testExChangeUserName() {
        org.junit.Assert.assertEquals("dt66277", xchangeUserProfileModel.getUserName());
    }

    @Test
    public void testExChangePassword() {
        org.junit.Assert.assertEquals("exchange password", xchangeUserProfileModel.getPassword());
    }

    @Test
    public void testExChangeURL() {
        org.junit.Assert.assertEquals("exchange URL", xchangeUserProfileModel.getURL());
    }

    @Test
    public void testTempoUserName() {
        org.junit.Assert.assertEquals("admin2", tempoUserProfileModel.getUserName());
    }

    @Test
    public void testTempoPassword() {
        org.junit.Assert.assertEquals("dsthackathon#999", tempoUserProfileModel.getPassword());
    }

    @Test
    public void testTempoURL() {
        org.junit.Assert.assertEquals("https://autotempotest.atlassian.net/", tempoUserProfileModel.getURL());
    }

}
