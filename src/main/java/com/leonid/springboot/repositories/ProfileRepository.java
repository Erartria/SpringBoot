package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.Profile;
import com.leonid.springboot.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Modifying
    @Transactional
    @Query("update Profile p set p.status=:status where p.profileId=:id")
    int setStatusById(@Param("status") Status s, @Param("id") int id);

    boolean existsByUsername(String username);
}
