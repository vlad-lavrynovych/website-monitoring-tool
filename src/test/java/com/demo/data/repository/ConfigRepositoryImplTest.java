package com.demo.data.repository;

import com.demo.data.domain.ConfigEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class ConfigRepositoryImplTest {
    private static ConfigEntity configEntity;
    private ConfigRepository configRepository = new ConfigRepositoryImpl();

    @BeforeAll
    static void init() {
        configEntity = new ConfigEntity();
        configEntity.setQueryingInterval(2000);
        configEntity.setResponseTimeOk(2000);
        configEntity.setResponseTimeWarning(3000);
        configEntity.setResponseTimeCritical(4000);
        configEntity.setExpectedHttpResponseCode(403);
        configEntity.setMinExpectedResponseSize(1000);
        configEntity.setMaxExpectedResponseSize(10000);
    }

    @Test
    void shouldPassWhenSavedRetrievedAndDeletedSuccessfully() {
        ConfigEntity conf = configRepository.save(configEntity).orElse(null);
        assertNotNull(conf);
        assertTrue(configRepository.findById(conf.getId()).isPresent());
        configRepository.delete(conf.getId());
        assertFalse(configRepository.findById(conf.getId()).isPresent());
    }

    @Test
    void shouldPassWhenUpdateMonitoringStatusSuccessfully() {
        ConfigEntity conf = configRepository.save(configEntity).get();
        configRepository.updateMonitoringStatus(conf.getId(), false);
        assertFalse(configRepository.findById(conf.getId()).get().isMonitored());
        configRepository.delete(conf.getId());
    }

}