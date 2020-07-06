package com.leonid.springboot.service;

import com.leonid.springboot.entities.Log;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.exception.EntityException;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.LogRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements MyServiceInterface<LogModel, Integer> {

    private final LogRepository logRepository;
    private final ProfileServiceImpl profileService;

    public LogServiceImpl(LogRepository logRepository, ProfileRepository profileRepository, ProfileServiceImpl profileService) {
        this.logRepository = logRepository;
        this.profileService = profileService;
    }

    @Override
    public List<LogModel> getAll() {
        return null;
    }

    @Override
    public LogModel findById(Integer integer) throws EntityException {
        Log log = logRepository.findById(integer)
                .orElseThrow(()->new EntityException("Log with ID " + integer + " is not exists"));
        return new LogModel(log.getProfile().getProfileId(),
                log.getProfile().getUsername(),
                System.currentTimeMillis(),
                log.getStatus().getStatusValue());
    }

    @Override
    public Integer create(LogModel logModel) {
        ProfileModel pm = profileService.findById(logModel.getProfile_id());
        this.logRepository.save(
                profileService.convertFromModelToEntity(pm),
                pm.getStatus(),
                ),
                logModel.
        ))
        return null;
    }
}
