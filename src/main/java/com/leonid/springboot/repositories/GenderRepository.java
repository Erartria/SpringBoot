package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Optional<Gender> findFirstByGenderValue(String genderValue);

    boolean existsByGenderValue(String genderValue);
}
