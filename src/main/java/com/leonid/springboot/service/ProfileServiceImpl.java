package com.leonid.springboot.service;

import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.NotFoundEntityWithID;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.GenderRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.repositories.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class    ProfileServiceImpl implements DataBaseServiceInterface<ProfileModel, Integer> {
    private final ProfileRepository profileRepository;
    private final GenderRepository genderRepository;
    private final StatusRepository statusRepository;
    private final LogServiceImpl logService;


    @Override
    public List<ProfileModel> getAll() {
        List<ProfileModel> pms = new ArrayList<>();
        List<Profile> ps = profileRepository.findAll();
        for (Profile p :
                ps) {
            ProfileModel pm = new ProfileModel(p.getProfileId(), p.getUsername(), p.getEmail(), p.getGender().getGenderValue(), p.getStatus().getStatusValue());
            pms.add(pm);
        }
        return pms;
    }

    @Override
    public ProfileModel findById(Integer integer) throws NotFoundEntityWithID {
        Profile profile = profileRepository.findById(integer)
                .orElseThrow(() ->
                        new NotFoundEntityWithID("Profile with id " + integer + " not found")
                );
        return new ProfileModel(
                profile.getProfileId(),
                profile.getUsername(),
                profile.getEmail().toLowerCase(),
                profile.getGender().getGenderValue().toLowerCase(),
                profile.getStatus().getStatusValue().toLowerCase());
    }

    @Override
    public Integer create(ProfileModel profileModel) {
        Profile profile = this.profileRepository.save(this.convertFromModelToEntity(profileModel));
        logService.create(new LogModel(profile.getProfileId(), profileModel.getStatus()));
        return profile.getProfileId();

    }


    public Map<String, Object> changedStatus(int profileId, String statusValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        Profile profile = this.profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new NotFoundEntityWithID("Profile " + profileId + " is not exists")
                );
        Status oldStatus = profile.getStatus();
        map.put("profileId", profile.getProfileId());
        map.put("old status", oldStatus);
        if (!oldStatus.getStatusValue().equals(statusValue.toLowerCase())) {
            logService.create(new LogModel(profileId, statusValue));
        }

        this.profileRepository.setStatusById(statusRepository.findFirstByStatusValue(statusValue)
                        .orElseGet(() -> {
                            return statusRepository.save(new Status(statusValue.toLowerCase()));
                        })
                , profileId);
        map.put("new status", statusRepository.findFirstByStatusValue(statusValue).get());
        return map;
    }


    private Profile convertFromModelToEntity(ProfileModel profileModel) {
        Status stat;
        try {
            stat = statusRepository.findFirstByStatusValue(profileModel.getStatus().toLowerCase())
                    .orElseGet(() -> {
                        return statusRepository.save(new Status(profileModel.getStatus().toLowerCase()));
                    });
        } catch (NullPointerException e) {
            stat = statusRepository.findFirstByStatusValue(null)
                    .orElseGet(() -> {
                        return statusRepository.save(new Status(null));
                    });
        }
        return new Profile(
                stat,
                profileModel.getUserName().toLowerCase(),
                profileModel.getEmail().toLowerCase(),
                genderRepository.findFirstByGenderValue(profileModel.getGender().toLowerCase())
                        .orElseGet(() -> {
                            return genderRepository.save(new Gender(profileModel.getGender().toLowerCase()));
                        })
        );
    }


}
