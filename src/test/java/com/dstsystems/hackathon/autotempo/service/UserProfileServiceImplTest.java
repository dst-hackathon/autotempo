package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.exception.ConfigurationException;
import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UserProfileServiceImplTest {

    private UserProfileServiceImpl userProfileService;
    private ExchangeUserProfileModel exchangeUserProfileModel;
    private TempoUserProfileModel tempoUserProfileModel;

    @Before
    public void setUp() throws Exception {
        userProfileService = new UserProfileServiceImpl();
        exchangeUserProfileModel = userProfileService.getExchangeUserProfile("src/test/resources/user.profile");
        tempoUserProfileModel = userProfileService.getTempoUserProfile("src/test/resources/user.profile");
    }

    @Test
    public void testExchangeUsername() {
        assertEquals("exchange user", exchangeUserProfileModel.getUsername());
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
    public void testTempoUsername() {
        assertEquals("tempo user", tempoUserProfileModel.getUsername());
    }

    @Test
    public void testTempoPassword() {
        assertEquals("tempo password", tempoUserProfileModel.getPassword());
    }

    @Test
    public void testTempoURL() {
        assertEquals("tempo URL", tempoUserProfileModel.getURL());
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingExchangeUsername() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_username.profile");
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingExchangePassword() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_password.profile");
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingExchangeURL() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_url.profile");
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingTempoUsername() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_username.profile");
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingTempoPassword() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_password.profile");
    }

    @Test(expected = ConfigurationException.class)
    public void testMissingTempoURL() throws Exception {
        userProfileService.getExchangeUserProfile("src/test/resources/missing_url.profile");
    }

}
