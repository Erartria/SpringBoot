package com.leonid.springboot.controllers;


import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.ProfileServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

;

@RestController
public class MainController {
    final
    ProfileServiceImpl myServiceInterface;

    public MainController(ProfileServiceImpl myServiceInterface) {
        this.myServiceInterface = myServiceInterface;
    }

    @GetMapping("/profiles")
    public List<ProfileDTO> getProfiles() {
        List<ProfileModel> profileModelList = myServiceInterface.getAll();
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (ProfileModel pm :
                profileModelList) {
            ProfileDTO profileDTO = new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
            profileDTOList.add(profileDTO);
        }
        return profileDTOList;
    }

    @PostMapping("/create")
    public String createProfile(@RequestBody ProfileDTO profile) {
        ProfileModel pm = new ProfileModel(profile.getUserName(), profile.getEmail(), profile.getGender(), profile.getStatus());
        return String.format("ID пользователя: %d", myServiceInterface.create(pm));
    }

    @GetMapping("/profile/{id}")
    public ProfileDTO getProfileById(@PathVariable(value = "id") Integer profileId) {
        ProfileModel pm = myServiceInterface.findById(profileId);
        return new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    @PostMapping("/profile/{id}/{status}")
    public boolean changeStatus(
            @PathVariable(value = "id") Integer profileId,
            @PathVariable(value = "status") String status) {
        return myServiceInterface.changedStatus(profileId,status);
    }

}
