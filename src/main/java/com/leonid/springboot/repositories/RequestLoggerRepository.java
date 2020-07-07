package com.leonid.springboot.repositories;

import com.leonid.springboot.entities.RequestLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLoggerRepository extends JpaRepository<RequestLogger, Integer> {
}
