package com.dstsystems.hackathon.autotempo.tempo;

import java.io.IOException;

public class HttpStatusException extends IOException {

    private int statusCode;

    public HttpStatusException(String message, int statusCode) {
        super(message);

        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
