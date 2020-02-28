package com.demo.data.repository;

import com.demo.data.domain.ConfigEntity;

import java.util.Optional;

public interface ConfigRepository {

    Optional<ConfigEntity> save(ConfigEntity configEntity);

    void updateMonitoringStatus(Long id, boolean monitoringStatus);

    void delete(Long id);

    Optional<ConfigEntity> findById(long id);
}
