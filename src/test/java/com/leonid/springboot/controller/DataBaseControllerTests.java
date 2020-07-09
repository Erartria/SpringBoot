package com.leonid.springboot.controller;


import com.leonid.springboot.controllers.DataBaseController;
import com.leonid.springboot.dto.ProfileDTO;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.service.ProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(DataBaseController.class)
public class DataBaseControllerTests {
    @MockBean
    private ProfileServiceImpl service;
    @Autowired
    DataBaseController controller;

    @Test
    public void whenProfileWithIdIsNotExists_throwEntityExistsException() {
        final Integer integer = 10;
        final EntityExistsException ex = new EntityExistsException("Profile with id " + integer + " not found");
        Mockito.when(service.findById(Mockito.anyInt())).thenThrow(ex);

        try {
            controller.getProfileById(integer);
            Assertions.fail("exception wasn't thrown");
        } catch (Exception e) {
            Assertions.assertEquals(ex.getClass(), e.getClass(), "wrong exception was thrown");
            Assertions.assertEquals(ex.getMessage(), e.getMessage(), "wrong exception msg");
        }

        Mockito.verify(service).findById(10);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    public void whenGetByIdThatExists_thenReturnProfileDTO() {
        final Integer id = 25;
        final ProfileDTO profileDTO = new ProfileDTO("user", "email", "gender", "status");
        ProfileModel profileModel = new ProfileModel(id, "user", "email", "gender", "status");
        Mockito.when(service.findById(Mockito.anyInt()))
                .thenReturn(profileModel);
        Assertions.assertEquals(profileDTO, controller.getProfileById(id), "profileDTO is not equals");

        Mockito.verify(service).findById(id);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    public void whenPutStatusWithIllegalId_thenTrowEntityExistsException() {
        final int id = 40;
        final EntityExistsException ent = new EntityExistsException("Profile with id " + id + " not found");
        final ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, ent.getMessage(), ent);

        Mockito.when(service.changedStatus(Mockito.anyInt(), Mockito.anyString()))
                .thenThrow(ex);

        try {
            service.changedStatus(id, "online");
            Assertions.fail("exception wasn't thrown");
        } catch (Exception e) {
            Assertions.assertEquals(ResponseStatusException.class, e.getClass()
                    , "Exception should link to ResponseStatusException");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException) e).getStatus()
                    , "Exception status must be NOT_FOUND");
            Assertions.assertEquals(ex.getMessage(), e.getMessage(), "wrong exception message");
        }
    }
}
