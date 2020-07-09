package com.leonid.springboot.controller;

import com.leonid.springboot.controllers.DataBaseController;
import com.leonid.springboot.controllers.LogController;
import com.leonid.springboot.dto.LogDTO;
import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Log;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.service.LogServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(LogController.class)
public class LogControllerTests {
    @MockBean
    private LogServiceImpl service;
    @Autowired
    private LogController controller;

    final List<Gender> genderList = new ArrayList<>() {{
        add(new Gender(1, "male"));
        add(new Gender(2, "female"));
    }};

    final List<Status> statusList = new ArrayList<>() {{
        add(new Status(1, "online"));
        add(new Status(2, "offline"));
    }};

    final List<Profile> profileList = new ArrayList<>() {{
        add(new Profile(1, "user1", "email1", statusList.get(0), genderList.get(0)));
        add(new Profile(2, "user2", "email2", statusList.get(1), genderList.get(1)));
        add(new Profile(3, "user1", "email1", statusList.get(0), genderList.get(1)));
        add(new Profile(4, "user1", "email1", statusList.get(1), genderList.get(0)));
    }};

    @Test
    public void whenRequestGetAll_thenReturnDTO() {
        List<LogModel> logModel = new ArrayList<>() {{
            add(new LogModel(1, profileList.get(0).getProfileId(), 1L, statusList.get(1).getStatusValue()));
            add(new LogModel(2, profileList.get(0).getProfileId(), 2L, statusList.get(0).getStatusValue()));
            add(new LogModel(3, profileList.get(1).getProfileId(), 3L, statusList.get(0).getStatusValue()));
            add(new LogModel(4, profileList.get(2).getProfileId(), 4L, statusList.get(1).getStatusValue()));
            add(new LogModel(5, profileList.get(3).getProfileId(), 5L, statusList.get(0).getStatusValue()));
        }};
        List<LogDTO> logDTO = new ArrayList<>() {{
            add(new LogDTO(1, profileList.get(0).getProfileId(), 1L, "offline"));
            add(new LogDTO(2, profileList.get(0).getProfileId(), 2L, "online"));
            add(new LogDTO(3, profileList.get(1).getProfileId(), 3L, "online"));
            add(new LogDTO(4, profileList.get(2).getProfileId(), 4L, "offline"));
            add(new LogDTO(5, profileList.get(3).getProfileId(), 5L, "online"));
        }};

        Mockito.when(service.getAll()).thenReturn(logModel);
        Assertions.assertEquals(logDTO, controller.getLogs());
    }
}
