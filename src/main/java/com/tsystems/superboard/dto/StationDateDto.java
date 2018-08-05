package com.tsystems.superboard.dto;

import java.io.Serializable;

public class StationDateDto implements Serializable {
    private String station;
    private String dateTime;

    public StationDateDto() {
    }

    public StationDateDto(String station, String dateTime) {
        this.station = station;
        this.dateTime = dateTime;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
