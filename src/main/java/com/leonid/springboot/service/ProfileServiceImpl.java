package com.leonid.springboot.service;

import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.EntityException;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements MyServiceInterface<ProfileModel, Integer> {
    final
    ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
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
        return null;
    }

    @Override
    public Integer create(ProfileModel profileModel) {
        List<Status> ls = repository.getStatusByStatus_StatusValue(profileModel.getStatus());
        List<Gender> lg = repository.getGenderByGender_GenderValue(profileModel.getGender());
        if (ls.isEmpty() && ls == null) {
            new EntityException("Status " + profileModel.getStatus() + "is not exists");
        } else if (lg.isEmpty() && lg == null) {
            new EntityException("Gender " + profileModel.getGender() + "is not exists");
        }
        Status s = ls.get(0);
        Gender g = lg.get(0);
        return this.repository.save(new Profile(
                s,
                profileModel.getUserName(),
                profileModel.getEmail(),
                g
        ))
                .getProfileId();
    }
}
