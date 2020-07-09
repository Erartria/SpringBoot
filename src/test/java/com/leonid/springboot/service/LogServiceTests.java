package com.leonid.springboot.service;

import com.leonid.springboot.entities.*;
import com.leonid.springboot.exception.NotFoundEntityWithID;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.repositories.LogRepository;
import com.leonid.springboot.repositories.ProfileRepository;
import com.leonid.springboot.repositories.RequestLoggerRepository;
import com.leonid.springboot.repositories.StatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(LogServiceImpl.class)
public class LogServiceTests {
    @MockBean
    private LogRepository logRepository;
    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private RequestLoggerRepository requestLoggerRepository;
    @MockBean
    private StatusRepository statusRepository;
    @Autowired
    private LogServiceImpl service;

    @Test
    public void whenLogIDIsNotExists_throwNotFoundEntityWithID() {
        Mockito.when(logRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        try {
            service.findById(i);
            Assertions.fail("exception wasn't thrown");
        } catch (Exception e) {
            Assertions.assertEquals(NotFoundEntityWithID.class, e.getClass(), "wrong exception type");
            Assertions.assertEquals("Log with id " + i + " not found", e.getMessage(), "wrong msg");
        }
        Mockito.verify(logRepository).findById(Mockito.eq(i));
        Mockito.verifyNoMoreInteractions(logRepository);
    }

    @Test
    public void whenLogExists_thenReturnLogModel() {
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        long time = System.currentTimeMillis();
        final Profile profile = new Profile(2, "", "", new Status(""), new Gender(""));
        final Log log = new Log(i, profile, profile.getStatus(), time);
        LogModel logModel = new LogModel(log.getLogId(), log.getChangedTime(), log.getStatus().getStatusValue());
        Mockito.when(logRepository.findById(i)).thenReturn(Optional.of(log));

        final LogModel res = service.findById(i);
        Assertions.assertEquals(res, new LogModel(i, 2, time, ""), "profile has modified");

        Mockito.verify(logRepository).findById(i);
        Mockito.verifyNoMoreInteractions(logRepository);
    }

    @Test
    public void whenCreateWithProfileThatNotExists_throwNotFoundEntityWithID() {
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        long time = System.currentTimeMillis();
        final Status status = new Status(null);
        final LogModel logModel = new LogModel(i, time, status.getStatusValue());

        Mockito.when(profileRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(statusRepository.findFirstByStatusValue(Mockito.anyString())).thenReturn(Optional.of(status));


        try {
            service.create(logModel);
            Assertions.fail("no exception was thrown");
        } catch (Exception e) {
            Assertions.assertEquals(NotFoundEntityWithID.class, e.getClass(), "wrong exception was thrown");
            Assertions.assertEquals("Profile with id " + i + " is not exists", e.getMessage(), "wrong exception msg was thrown");
        }
    }

    @Test
    public void whenLogStatusIsNotExists_createNewStatus() {
        final long time = System.currentTimeMillis();
        final Profile profile = new Profile(100, "user", "@aaa", new Status("posted"), new Gender("alien"));
        final Status status = new Status(70, "in work");
        final Log log = new Log(25,
                profile,
                status,
                time);
        LogModel logModel = new LogModel(log.getLogId(), log.getProfile().getProfileId(), log.getChangedTime(), log.getStatus().getStatusValue());
        Mockito.when(profileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(profile));
        Mockito.when(statusRepository.findFirstByStatusValue(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(statusRepository.save(Mockito.any(Status.class))).thenReturn(status);

        Integer res = service.create(logModel);

        Assertions.assertEquals(res, 25, "wrong status id was given");

        Mockito.verify(profileRepository).findById(100);
        Mockito.verify(statusRepository).findFirstByStatusValue("in work");
        Mockito.verify(statusRepository).save(Mockito.any(Status.class));
        Mockito.verifyNoMoreInteractions(profileRepository, statusRepository);
    }

    @Test
    public void whenGetByStatusAndTimestamp_createRequestLog() {
        final long time = System.currentTimeMillis();
        final long reqTime = time - 71342;
        List<Log> empty = new ArrayList<>();
        final RequestLogger requestLogger = new RequestLogger(27, time, "online", reqTime);
        Mockito.when(requestLoggerRepository.save(Mockito.any(RequestLogger.class))).thenReturn(requestLogger);
        Mockito.when(logRepository.findAllByChangedTimeAfterAndStatus_StatusValue(Mockito.anyInt(), Mockito.anyString())).thenReturn(empty);

        List<LogModel> emptyModel = new ArrayList<>();
        List<LogModel> logModelList = service.getByStatusAndTimestamp(time, "online");
        Assertions.assertEquals(logModelList, emptyModel, "List must be empty");

        Mockito.verify(requestLoggerRepository).save(Mockito.any(RequestLogger.class));
        Mockito.verify(logRepository).findAllByChangedTimeAfterAndStatus_StatusValue(time, "online");
        Mockito.verifyNoMoreInteractions(requestLoggerRepository, logRepository);
    }
}
