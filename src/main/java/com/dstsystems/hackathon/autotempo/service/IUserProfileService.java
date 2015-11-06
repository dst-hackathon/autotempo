package com.dstsystems.hackathon.autotempo.service;

import com.dstsystems.hackathon.autotempo.models.UserProfileModel;

/**
 * Created by user on 06/11/2015.
 */
public interface IUserProfileService {

    UserProfileModel getUserProfile(String pathFileName);
}
