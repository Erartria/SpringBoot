package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("select p From Profile p join fetch p.gender g where g.genderValue = ?1")
    List<Profile> getProfileByGender_GenderValue(String genderValue);

    Optional<Profile> getFirstByGender_GenderValue(String genderValue);
}
