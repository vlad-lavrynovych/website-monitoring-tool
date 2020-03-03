package com.demo.service.impl;

import com.demo.data.domain.CheckResultsEntity;
import com.demo.data.domain.ConfigEntity;
import com.demo.service.CheckResultsService;
import com.demo.service.ConfigService;
import com.demo.testing.TestingService;
import com.demo.testing.impl.TestingServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class TestingServiceImplTest {
    private TestingService testingService = new TestingServiceImpl();
    private ConfigService configService = new ConfigServiceImpl();
    private CheckResultsService checkResultsService = new CheckResultsServiceImpl();

    @Test
    void shouldPassWhenPerformCheckSuccessful() {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setUrl("https://www.wikipedia.org/");
        configEntity.setQueryingInterval(2000);
        configEntity.setResponseTimeOk(5000);
        configEntity.setResponseTimeWarning(6000);
        configEntity.setResponseTimeCritical(10000);
        configEntity.setExpectedHttpResponseCode(200);
        configEntity.setMinExpectedResponseSize(1000);
        configEntity.setMaxExpectedResponseSize(100000);
        configEntity = configService.addNewConfig(configEntity);
        CheckResultsEntity checkResultsEntity = testingService.performCheck(configEntity.getId());
        assertEquals(200, (int) checkResultsEntity.getResponseCode());
        assertEquals("OK", checkResultsEntity.getStatus());
        configService.deleteConfig(configEntity.getId());
        checkResultsService.deleteById(checkResultsEntity.getId());
    }
}