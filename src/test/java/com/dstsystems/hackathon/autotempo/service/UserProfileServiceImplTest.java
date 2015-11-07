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

    private ExchangeUserProfileModel exchangeUserProfileModel;
    private TempoUserProfileModel tempoUserProfileModel;

    @Before
    public void setUp() {
        UserProfileServiceImpl service = new UserProfileServiceImpl();
        exchangeUserProfileModel = service.getExchangeUserProfile("src/test/resources/user.profile");
        tempoUserProfileModel = service.getTempoUserProfile("src/test/resources/user.profile");
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

}
