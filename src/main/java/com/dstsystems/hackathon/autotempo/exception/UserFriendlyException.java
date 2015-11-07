package com.dstsystems.hackathon.autotempo.exception;

/**
 * An exception which its message is meaningful to the end-user
 */
public class UserFriendlyException extends Exception {

    public UserFriendlyException(String message) {
        super(message);
    }

}
