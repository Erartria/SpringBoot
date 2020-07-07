package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.Log;
import com.leonid.springboot.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByChangedTimeAfterAndStatus_StatusValue(long time, String status);

    Optional<Log> findFirstByProfile_ProfileId(int id);
}
