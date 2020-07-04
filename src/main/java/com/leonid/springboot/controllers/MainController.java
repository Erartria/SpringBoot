package com.leonid.springboot.controllers;


import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    private final ProfileService profileService;

    @Autowired
    public MainController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Profile profile) {
        profileService.add(profile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/profiles")
    public ResponseEntity<List<Profile>> get() {
        final List<Profile> profiles = profileService.getAll();

        return (profiles != null && !profiles.isEmpty())
                ? new ResponseEntity<>(profiles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/profiles/{id}")
    public ResponseEntity<Profile> read(@PathVariable(name = "id") int id) {
        final Profile profile = profileService.getById(id);

        return (profile!=null)
                ? new ResponseEntity<>(profile, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
