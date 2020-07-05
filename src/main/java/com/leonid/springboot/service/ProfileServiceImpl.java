package com.leonid.springboot.service;

import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.models.Gender;
import com.leonid.springboot.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileServiceInterface {
    @Autowired
    ProfileRepository repository;

    @Override
    public List<Profile> getAll() {
        return repository.findAll();
    }

    @Override
    public Profile findById(int id) {
        Optional<Profile> temp = repository.findById(id);
        return temp.isPresent()
                ? temp.get()
                : null;
    }

    @Override
    public int create(Profile profile) {
        repository.save(profile);
        return profile.getProfileId();
    }

    @Override
    public void update(Profile Profile) {

    }

    @Override
    public void delete(int id) {

    }

}
