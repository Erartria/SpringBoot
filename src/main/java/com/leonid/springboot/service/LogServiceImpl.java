package com.leonid.springboot.service;

import com.leonid.springboot.entities.Log;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.EntityException;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.LogRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements MyServiceInterface<LogModel, Integer> {

    private final LogRepository logRepository;
    private final StatusRepository statusRepository;
    private final ProfileRepository profileRepository;

    public LogServiceImpl(LogRepository logRepository,
                          ProfileRepository profileRepository,
                          StatusRepository statusRepository) {
        this.logRepository = logRepository;
        this.statusRepository = statusRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public List<LogModel> getAll() {
        List<Log> logList = logRepository.findAll();
        List<LogModel> logModels = new ArrayList<>();
        for (Log log :
                logList) {
            LogModel logModel = new LogModel();
            logModel.setNewStatus(log.getStatus().getStatusValue());
            logModel.setChangedTime(log.getChangedTime());
            logModel.setProfileId(log.getProfile().getProfileId());
            logModels.add(logModel);
        }
        return logModels;
    }

    @Override
    public LogModel findById(Integer integer) throws EntityException {
        Log log = logRepository.findById(integer)
                .orElseThrow(()->new EntityException("Log with ID " + integer + " is not exists"));
        return new LogModel(log.getProfile().getProfileId(),
                log.getStatus().getStatusValue(),
                log.getChangedTime());
    }

    @Override
    public Integer create(LogModel logModel) {
        Profile profile = profileRepository.findById(logModel.getProfileId())
                .orElseThrow(()->
                        new EntityException("Profile with id " + logModel.getProfileId() + " is not exists")
                        );
        this.logRepository.save(new Log(
                profile, statusRepository.findFirstByStatusValue(logModel.getNewStatus().toLowerCase())
                .orElseGet(()->{
                        return statusRepository.save(new Status(logModel.getNewStatus()));
                }),System.currentTimeMillis()
        ));
        return 1;
    }
}
