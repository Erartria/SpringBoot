package com.leonid.springboot.service;

import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import com.leonid.springboot.exception.NotFoundEntityWithID;
import com.leonid.springboot.models.ProfileModel;
import com.leonid.springboot.repositories.GenderRepository;
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

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(ProfileServiceImpl.class)
public class ProfileServiceTests {
    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private GenderRepository genderRepository;
    @MockBean
    private StatusRepository statusRepository;
    @MockBean
    private RequestLoggerRepository requestLoggerRepository;
    @MockBean
    private LogServiceImpl logService;

    @Autowired
    private ProfileServiceImpl service;

    @Test
    public void whenProfileIDIsNotExists_throwNotFoundEntityWithID() {
        Mockito.when(profileRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        try {
            service.findById(i);
            Assertions.fail("exception wasn't thrown");
        } catch (Exception e) {
            Assertions.assertEquals(NotFoundEntityWithID.class, e.getClass(), "wrong exception type");
            Assertions.assertEquals("Profile with id " + i + " not found", e.getMessage(), "wrong msg");
        }
        Mockito.verify(profileRepository).findById(Mockito.eq(i));
        Mockito.verifyNoMoreInteractions(profileRepository);
    }

    @Test
    public void whenProfileIdExists_thenReturnProfileModel() {
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        final Profile profile = new Profile(i, "", "", new Status(""), new Gender(""));


        Mockito.when(profileRepository.findById(i)).thenReturn(Optional.of(profile));

        ProfileModel res = service.findById(i);
        Assertions.assertEquals(res, new ProfileModel(i, "", "", "", ""), "profile has modified");

        Mockito.verify(profileRepository).findById(i);
        Mockito.verifyNoMoreInteractions(profileRepository);
    }

    @Test
    public void whenCreateProfileWithNotExistedGenderAndStatus_theyWillCreated() {
        final int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        final Status status = new Status(1, "online");
        final Gender gender = new Gender(1, "male");
        final Profile profile = new Profile(i, "", "", new Status("online"), new Gender("male"));


        ProfileModel profileModel = new ProfileModel(profile.getProfileId(), profile.getUsername(), profile.getEmail(), profile.getGender().getGenderValue(), profile.getStatus().getStatusValue());

        Mockito.when(statusRepository.findFirstByStatusValue(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(genderRepository.findFirstByGenderValue(Mockito.anyString())).thenReturn(Optional.empty());

        Mockito.when(statusRepository.save(Mockito.any(Status.class))).thenReturn(status);
        Mockito.when(genderRepository.save(Mockito.any(Gender.class))).thenReturn(gender);
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);

        Integer res = service.create(profileModel);

        Assertions.assertEquals(res, i, "Status and gender has been created");

        Mockito.verify(statusRepository).findFirstByStatusValue(Mockito.anyString());
        Mockito.verify(genderRepository).findFirstByGenderValue(Mockito.anyString());
        Mockito.verify(statusRepository).save(Mockito.any(Status.class));
        Mockito.verify(genderRepository).save(Mockito.any(Gender.class));
        Mockito.verify(profileRepository).save(Mockito.any(Profile.class));
        Mockito.verifyNoMoreInteractions(statusRepository, genderRepository, profileRepository);
    }

    @Test
    public void whenCreateProfileWithNULLStatus_itWillCreated() {
        int i = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        final Status status = new Status(1, null);
        final Gender gender = new Gender(1, "male");
        final Profile profile = new Profile(i, "", "", new Status(null), new Gender("male"));
        final ProfileModel profileModel = new ProfileModel(profile.getProfileId(), profile.getUsername(), profile.getEmail(), profile.getGender().getGenderValue(), profile.getStatus().getStatusValue());

        Mockito.when(statusRepository.findFirstByStatusValue(null)).thenReturn(Optional.empty());
        Mockito.when(genderRepository.findFirstByGenderValue(Mockito.anyString())).thenReturn(Optional.of(gender));

        Mockito.when(statusRepository.save(Mockito.any(Status.class))).thenReturn(status);
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(profile);

        Integer res = service.create(profileModel);

        Assertions.assertEquals(res, i, "Status and gender has been created");

        Mockito.verify(statusRepository).findFirstByStatusValue(null);
        Mockito.verify(genderRepository).findFirstByGenderValue(Mockito.anyString());
        Mockito.verify(statusRepository).save(Mockito.any(Status.class));
        Mockito.verify(profileRepository).save(Mockito.any(Profile.class));
        Mockito.verifyNoMoreInteractions(statusRepository, genderRepository, profileRepository);
    }
}
