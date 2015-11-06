package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.ExchangeUserProfileModel;
import com.dstsystems.hackathon.autotempo.models.TempoUserProfileModel;

/**
 * Created by user on 06/11/2015.
 */
public interface UserProfileService {

    ExchangeUserProfileModel getExchangeUserProfile(String pathFileName);

    TempoUserProfileModel getTempoUserProfile(String pathFileName);
}
