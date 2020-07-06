package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.Gender;
import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("select g From Gender g where g.genderValue = ?1")
    List<Gender> getGenderByGender_GenderValue(String genderValue);

    @Query("select s From Status s where s.statusValue = ?1")
    List<Status> getStatusByStatus_StatusValue(String statusValue);
}
