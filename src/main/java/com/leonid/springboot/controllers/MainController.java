package com.leonid.springboot.controllers;


import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.MyServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

;

@RestController
public class MainController {
    final
    MyServiceInterface myServiceInterface;

    public MainController(MyServiceInterface myServiceInterface) {
        this.myServiceInterface = myServiceInterface;
    }

    @GetMapping("/profiles")
    public List<Profile> getProfiles() {
        return myServiceInterface.getAll();
    }

    @PostMapping("/create")
    public String createProfile(@RequestBody ProfileModel profile) {
        return String.format("ID пользователя: %d", myServiceInterface.create(profile));
    }
}
