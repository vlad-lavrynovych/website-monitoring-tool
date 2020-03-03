package com.demo.data.repository;

import com.demo.data.domain.CheckResultsEntity;

import java.util.List;
import java.util.Optional;

public interface CheckResultsRepository {
    Optional<CheckResultsEntity> save(CheckResultsEntity resultEntity);

    void updateMonitoringStatus(Long id, boolean monitoringStatus);

    void delete(Long id);

    Optional<CheckResultsEntity> findById(long id);

    List<CheckResultsEntity> findAll();

    Optional<CheckResultsEntity> updateCheckResult(CheckResultsEntity c);
}
