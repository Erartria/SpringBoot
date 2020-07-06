package com.leonid.springboot.service;

import com.leonid.springboot.entities.Log;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.EntityException;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.repositories.LogRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.repositories.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LogServiceImpl implements DataBaseServiceInterface<LogModel, Integer> {

    private final LogRepository logRepository;
    private final StatusRepository statusRepository;
    private final ProfileRepository profileRepository;

    @Override
    public List<LogModel> getAll() {
        List<Log> logList = logRepository.findAll();
        List<LogModel> logModels = new ArrayList<>();
        for (Log log :
                logList) {
            LogModel logModel = new LogModel(
                    log.getLogId(),
                    log.getProfile().getProfileId(),
                    log.getChangedTime(),
                    log.getStatus().getStatusValue()
            );
            logModels.add(logModel);
        }
        return logModels;
    }

    @Override
    public LogModel findById(Integer integer) throws EntityException {
        Log log = logRepository.findById(integer)
                .orElseThrow(() -> new EntityException("Log with ID " + integer + " is not exists"));
        return new LogModel(
                log.getLogId(),
                log.getProfile().getProfileId(),
                log.getChangedTime(),
                log.getStatus().getStatusValue()
        );
    }

    @Override
    public Integer create(LogModel logModel) {
        Profile profile = profileRepository.findById(logModel.getProfileId())
                .orElseThrow(() ->
                        new EntityException("Profile with id " + logModel.getProfileId() + " is not exists")
                );
        Log log = new Log(
                profile, statusRepository.findFirstByStatusValue(logModel.getNewStatus().toLowerCase())
                .orElseGet(() -> {
                    return statusRepository.save(new Status(logModel.getNewStatus()));
                }), System.currentTimeMillis()
        );
        logRepository.save(log);
        return log.getLogId();
    }

    public List<LogModel> getAllByStatusAndTimestamp(long time, String statusValue) {
        List<LogModel> logModelList = new ArrayList<>();
        List<Log> logList = logRepository.findAllByChangedTimeBeforeAndStatus(time, statusValue);
        for (Log log :
                logList) {
            logModelList.add(new LogModel(log.getLogId(),log.getChangedTime(),log.getStatus().getStatusValue()));
        }
        return  logModelList;
    }
}
