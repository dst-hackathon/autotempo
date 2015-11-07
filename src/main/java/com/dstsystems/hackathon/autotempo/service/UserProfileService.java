package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

import java.io.IOException;

/**
 * Created by user on 06/11/2015.
 */
public interface UserProfileService {

    ExchangeUserProfileModel getExchangeUserProfile(String pathFileName) throws IOException;

    TempoUserProfileModel getTempoUserProfile(String pathFileName) throws IOException;

}
