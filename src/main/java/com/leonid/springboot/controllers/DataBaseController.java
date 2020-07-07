package com.leonid.springboot.controllers;


import com.leonid.springboot.dto.LogDTO;
import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.LogServiceImpl;
import com.leonid.springboot.service.ProfileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class DataBaseController {
    private final
    ProfileServiceImpl profileService;
    private final
    LogServiceImpl logService;

    @GetMapping("/profile")
    public List<ProfileDTO> getProfiles() {
        return fromServiceToControllerProfileList(profileService.getAll());
    }

    @PostMapping("/profile")
    public int createProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.create(fromControllerToServiceProfile(profileDTO));
    }

    @GetMapping("/profile/{id}")
    public ProfileDTO getProfileById(@PathVariable(value = "id") Integer profileId) {
        return fromServiceToControllerProfile(profileService.findById(profileId));
    }

    @PutMapping("/profile/{id}")
    public Map<String, Object> changeStatusForId(
            @PathVariable(value = "id") Integer profileId,
            @RequestParam(value = "status") String status) {
        return profileService.changedStatus(profileId, status);
    }

    @GetMapping("/logs")
    public List<LogDTO> getLogs() {
        return fromServiceToControllerLogList(logService.getAll());
    }

        @GetMapping("/logs/{status}")
    public List<LogDTO> getLogsByTimestampAndStatus(
            @PathVariable(value = "status") String status,
            @RequestParam(value = "timestamp", required = false, defaultValue = "0") long time) {
        return fromServiceToControllerLogList(logService.getByStatusAndTimestamp(time, status));
    }


    private static LogDTO fromLogModelToLogDTO(LogModel log) {
        return new LogDTO(log.getId(), log.getProfileId(), log.getChangedTime(), log.getNewStatus());
    }

    private static ProfileDTO fromServiceToControllerProfile(ProfileModel pm) {
        return new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    private static ProfileModel fromControllerToServiceProfile(ProfileDTO pm) {
        return new ProfileModel(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    private static List<LogDTO> fromServiceToControllerLogList(List<LogModel> logModelList) {
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log :
                logModelList) {
            logDTOList.add(fromLogModelToLogDTO(log));
        }
        return logDTOList;
    }

    private static List<ProfileDTO> fromServiceToControllerProfileList(List<ProfileModel> profileModelList) {
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (ProfileModel pm :
                profileModelList) {
            ProfileDTO profileDTO = new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
            profileDTOList.add(profileDTO);
        }
        return profileDTOList;
    }

}
