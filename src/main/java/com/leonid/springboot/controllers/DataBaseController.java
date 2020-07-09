package com.leonid.springboot.controllers;


import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.ProfileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController()
@AllArgsConstructor
public class DataBaseController {
    private final ProfileServiceImpl profileService;

    @GetMapping("/profile")
    public List<ProfileDTO> getProfiles() {
        return fromServiceToControllerProfileList(profileService.getAll());
    }

    @PostMapping("/profile")
    public int postProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.create(fromControllerToServiceProfile(profileDTO));
    }

    @GetMapping("/profile/{id}")
    public ProfileDTO getProfileById(@PathVariable(value = "id") Integer profileId) {
        try {
            return fromServiceToControllerProfile(profileService.findById(profileId));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/profile/{id}")
    public Map<String, Object> putStatusForId(
            @PathVariable(value = "id") Integer profileId,
            @RequestParam(value = "status") String status) {
        try {
            return profileService.changedStatus(profileId, status);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private static ProfileDTO fromServiceToControllerProfile(ProfileModel pm) {
        return new ProfileDTO(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
    }

    private static ProfileModel fromControllerToServiceProfile(ProfileDTO pm) {
        return new ProfileModel(pm.getUserName(), pm.getEmail(), pm.getGender(), pm.getStatus());
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
