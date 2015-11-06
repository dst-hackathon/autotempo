package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 06/11/2015.
 */
public class UserProfileServiceImplTest {

    ExchangeUserProfileModel exchangeUserProfileModel;
    TempoUserProfileModel tempoUserProfileModel;

    @Before
    public void setupExchangeUserProfile() {
        UserProfileServiceImpl service = new UserProfileServiceImpl();

        exchangeUserProfileModel = service.getExchangeUserProfile("src/test/resources/user.profile");
    }

    @Before
    public void setupTempoUserProfile() {
        UserProfileServiceImpl service = new UserProfileServiceImpl();

        tempoUserProfileModel = service.getTempoUserProfile("src/test/resources/user.profile");
    }

    @Test
    public void testExchangeUserName() {
        assertEquals("dt66277", exchangeUserProfileModel.getUsername());
    }

    @Test
    public void testExchangePassword() {
        assertEquals("exchange password", exchangeUserProfileModel.getPassword());
    }

    @Test
    public void testExchangeURL() {
        assertEquals("exchange URL", exchangeUserProfileModel.getURL());
    }

    @Test
    public void testTempoUserName() {
        assertEquals("admin2", tempoUserProfileModel.getUsername());
    }

    @Test
    public void testTempoPassword() {
        assertEquals("dsthackathon#999", tempoUserProfileModel.getPassword());
    }

    @Test
    public void testTempoURL() {
        assertEquals("https://autotempotest.atlassian.net/", tempoUserProfileModel.getURL());
    }

}
