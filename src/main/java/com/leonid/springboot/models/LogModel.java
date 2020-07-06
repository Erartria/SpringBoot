package com.leonid.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogModel {
    private int profile_id;
    private String userName;
    private long changedTime;
    private String newStatus;

    public LogModel(int profile_id, String userName, long changedTime, String newStatus) {
        this.profile_id = profile_id;
        this.userName = userName;
        this.changedTime = changedTime;
        this.newStatus = newStatus;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LogModel(String userName, long changedTime, String newStatus) {
        this.userName = userName;
        this.changedTime = changedTime;
        this.newStatus = newStatus;
    }
}
