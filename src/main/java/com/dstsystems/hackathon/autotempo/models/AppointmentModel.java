package com.dstsystems.hackathon.autotempo.models;

import microsoft.exchange.webservices.data.core.enumeration.property.MeetingResponseType;

import java.util.Date;
import java.util.List;

public class AppointmentModel {

    String subject;

    private Date start;

    private Date end;

    List<String> categories;

    private MeetingResponseType myResponseType;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public MeetingResponseType getMyResponseType() {
        return myResponseType;
    }

    public void setMyResponseType(MeetingResponseType myResponseType) {
        this.myResponseType = myResponseType;
    }

}
