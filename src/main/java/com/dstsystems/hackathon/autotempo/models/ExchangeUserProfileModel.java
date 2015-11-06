package com.dstsystems.hackathon.autotempo.models;

/**
 * Created by user on 06/11/2015.
 */
public class ExchangeUserProfileModel {

    String userName;

    String password;

    String URL;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
