package com.leonid.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LogModel {
    private int profileId;
    private long changedTime;
    private String newStatus;

    public LogModel(int profileId, String newStatus) {
        this.profileId = profileId;
        this.newStatus = newStatus;
    }

    public LogModel(int profileId, String newStatus, long changedTime) {
        this.profileId = profileId;
        this.newStatus = newStatus;
        this.changedTime = changedTime;
    }

    public long getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(long changedTime) {
        this.changedTime = changedTime;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
