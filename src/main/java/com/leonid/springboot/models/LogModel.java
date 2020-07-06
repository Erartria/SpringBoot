package com.leonid.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogModel {
    private int id;
    private int profileId;
    private long changedTime;
    private String newStatus;

    public LogModel(int profileId, String newStatus) {
        this.profileId = profileId;
        this.newStatus = newStatus;
    }

    public LogModel(int profileId, long changedTime, String newStatus) {
        this.profileId = profileId;
        this.changedTime = changedTime;
        this.newStatus = newStatus;
    }

}
