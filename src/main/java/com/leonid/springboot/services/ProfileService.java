package com.leonid.springboot.services;

import com.leonid.springboot.entities.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    void add(Profile profile);
    List<Profile> getAll();
    Profile getById(int id);
    boolean update(Profile profile, int id);
    boolean deleteById(int id);
}
