package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.service.CheckResultsService;
import com.demo.service.ConfigService;
import com.demo.service.TestingService;
import com.demo.service.TimerService;

import java.util.Date;
import java.util.TimerTask;

public class TimerServiceImpl implements TimerService {
    private TestingService testingService = new TestingServiceImpl();
    private ConfigService configService = new ConfigServiceImpl();
    private CheckResultsService checkResultsService = new CheckResultsServiceImpl();

    @Override
    public void runTimer(long id, Integer queryingInterval) {
        Timer timer = new Timer(id);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConfigEntity configEntity = configService.getConfigById(timer.getConfigId());
                if (configEntity.isMonitored()) {
                    checkResultsService.update(testingService.performCheck(id).setLastCheck(new Date()));
                } else {
                    cancel();
                }
            }
        }, queryingInterval, queryingInterval);
    }
}
