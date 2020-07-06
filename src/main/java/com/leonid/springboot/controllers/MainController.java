package com.leonid.springboot.controllers;


import com.leonid.springboot.dto.LogDTO;
import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.LogServiceImpl;
import com.leonid.springboot.service.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

;

@RestController
public class MainController {
    private final
    ProfileServiceImpl profileService;
    final
    LogServiceImpl logService;

    public MainController(ProfileServiceImpl profileService, LogServiceImpl logService) {
        this.profileService = profileService;
        this.logService = logService;
    }

    @GetMapping("/profiles")
    public List<ProfileDTO> getProfiles() {
        List<ProfileModel> profileModelList = profileService.getAll();
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (ProfileModel pm :
                profileModelList) {
            ProfileDTO profileDTO = new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
            profileDTOList.add(profileDTO);
        }
        return profileDTOList;
    }

    @GetMapping("/logs")
    public List<LogDTO> getLogs() {
        List<LogModel> logModelList = logService.getAll();
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log :
                logModelList) {
            LogDTO logdto = new LogDTO(log.getProfileId(), log.getChangedTime(), log.getNewStatus());
            logDTOList.add(logdto);
        }
        return logDTOList;
    }

    @PostMapping("/create")
    public String createProfile(@RequestBody ProfileDTO profile) {
        ProfileModel pm = new ProfileModel(profile.getUserName(), profile.getEmail(), profile.getGender(), profile.getStatus());
        return String.format("ID пользователя: %d", profileService.create(pm));
    }

    @GetMapping("/profile/{id}")
    public ProfileDTO getProfileById(@PathVariable(value = "id") Integer profileId) {
        ProfileModel pm = profileService.findById(profileId);
        return new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    @PostMapping("/profile/{id}/{status}")
    public boolean changeStatus(
            @PathVariable(value = "id") Integer profileId,
            @PathVariable(value = "status") String status) {
        logService.create(new LogModel(profileId,status));
        return profileService.changedStatus(profileId,status);
    }

}
