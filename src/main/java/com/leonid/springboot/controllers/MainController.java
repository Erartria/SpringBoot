package com.leonid.springboot.controllers;


import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.models.Gender;
import com.leonid.springboot.repositories.GenderRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.service.ProfileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

;

@RestController
public class MainController {
    @Autowired
    ProfileServiceInterface profileServiceInterface;

    @GetMapping("/profiles")
    public List<Profile> getProfiles() {
        return profileServiceInterface.getAll();
    }

    @PostMapping("/create")
    public String createProfile(@RequestBody Profile profile) {
        return String.format("ID пользователя: %d",profileServiceInterface.create(profile));
    }
}
