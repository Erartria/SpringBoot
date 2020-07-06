package com.leonid.springboot.controllers;


import com.leonid.springboot.dto.LogDTO;
import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.LogServiceImpl;
import com.leonid.springboot.service.ProfileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;

@RestController
@AllArgsConstructor
public class DataBaseController {
    private final
    ProfileServiceImpl profileService;
    private final
    LogServiceImpl logService;

    @GetMapping("/profile")
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

    @PostMapping("/profile")
    public int createProfile(@RequestBody ProfileDTO profile) {
        return profileService.create(new ProfileModel(profile.getUserName(), profile.getEmail(), profile.getGender(), profile.getStatus()));
    }

    @GetMapping("/profile/{id}")
    public ProfileDTO getProfileById(@PathVariable(value = "id") Integer profileId) {
        ProfileModel pm = profileService.findById(profileId);
        return new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    @PutMapping("/profile/{id}")
    public Map<String, Object> changeStatusForId(
            @PathVariable(value = "id") Integer profileId,
            @RequestParam(value = "status") String status) {
        return profileService.changedStatus(profileId, status);
    }

    @GetMapping("/logs")
    public List<LogDTO> getLogs() {
        List<LogModel> logModelList = logService.getAll();
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log :
                logModelList) {
            LogDTO logdto = new LogDTO(log.getId(), log.getProfileId(), log.getChangedTime(), log.getNewStatus());
            logDTOList.add(logdto);
        }
        return logDTOList;
    }

    @GetMapping("/logs/{status}")
    public List<LogDTO> getLogsByTimestampAndStatus(
            @PathVariable(value = "status") String status,
            @RequestParam(value = "timestamp", required = false, defaultValue = "0") long time) {
        List<LogModel> logModelList = logService.getAllByStatusAndTimestamp(time,status);
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log :
                logModelList) {
            LogDTO logdto = new LogDTO(log.getId(), log.getProfileId(), log.getChangedTime(), log.getNewStatus());
            logDTOList.add(logdto);
        }
        return logDTOList;
    }
}
