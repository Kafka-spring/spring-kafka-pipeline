package com.elfn.consumer.repository;

import com.elfn.consumer.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Elimane
 */
public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
