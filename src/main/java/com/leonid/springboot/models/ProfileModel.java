package com.leonid.springboot.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileModel {
    private int id;
    private String userName;
    private String email;
    private String gender;
    private String status;

    public ProfileModel(String userName, String email, String gender, String status) {
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }
}
