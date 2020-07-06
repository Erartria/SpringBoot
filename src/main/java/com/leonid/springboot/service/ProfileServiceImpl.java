package com.leonid.springboot.service;

import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.EntityException;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.GenderRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.repositories.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements MyServiceInterface<ProfileModel, Integer> {
    private final ProfileRepository repository;
    private final GenderRepository genderRepository;
    private final StatusRepository statusRepository;

    public ProfileServiceImpl(ProfileRepository repository, GenderRepository genderRepository, StatusRepository statusRepository) {
        this.repository = repository;
        this.genderRepository = genderRepository;
        this.statusRepository = statusRepository;
    }


    @Override
    public List<ProfileModel> getAll() {
        List<ProfileModel> pms = new ArrayList<>();
        List<Profile> ps = repository.findAll();
        for (Profile p :
                ps) {
            ProfileModel pm = new ProfileModel();
            pm.setGender(p.getGender().getGenderValue());
            pm.setEmail(p.getEmail());
            pm.setUserName(p.getUsername());
            pm.setStatus(p.getStatus().getStatusValue());
            pm.setId(p.getProfileId());
            pms.add(pm);
        }
        return pms;
    }

    @Override
    public ProfileModel findById(Integer integer) {
        Profile profile = repository.findById(integer)
                .orElseThrow(() ->
                        new EntityException("Profile with id " + integer + " not found")
                );
        return new ProfileModel(
                profile.getUsername().toLowerCase(),
                profile.getEmail().toLowerCase(),
                profile.getGender().getGenderValue().toLowerCase(),
                profile.getStatus().getStatusValue().toLowerCase()
        );
    }

    @Override
    public Integer create(ProfileModel profileModel) {
        return this.repository.save(new Profile(
                statusRepository.findFirstByStatusValue(profileModel.getStatus().toLowerCase())
                        .orElseGet(() -> {
                            return statusRepository.save(new Status(profileModel.getStatus().toLowerCase()));
                        }),
                profileModel.getUserName().toLowerCase(),
                profileModel.getEmail().toLowerCase(),
                genderRepository.findFirstByGenderValue(profileModel.getGender().toLowerCase())
                        .orElseGet(() -> {
                            return genderRepository.save(new Gender(profileModel.getGender().toLowerCase()));
                        })
        ))
                .getProfileId();
    }
}
