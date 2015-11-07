package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.exception.ConfigurationException;
import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

import java.io.IOException;

public interface UserProfileService {

    ExchangeUserProfileModel getExchangeUserProfile(String pathFileName) throws ConfigurationException, IOException;

    TempoUserProfileModel getTempoUserProfile(String pathFileName) throws ConfigurationException, IOException;

}
