package com.leonid.springboot.service;

import com.leonid.springboot.entities.Profile;

import java.util.List;

public interface ProfileServiceInterface {
    List<Profile> getAll();

    Profile findById(int id);

    int create(Profile Profile);

    void update(Profile Profile);

    void delete(int id);

}
