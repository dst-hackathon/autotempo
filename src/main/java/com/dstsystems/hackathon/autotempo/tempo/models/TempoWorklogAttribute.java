package com.dstsystems.hackathon.autotempo.tempo.models;

public class TempoWorklogAttribute {

    private String key;
    private String value;

    public TempoWorklogAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
