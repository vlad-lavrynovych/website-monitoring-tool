package com.demo.testing.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.service.CheckResultsService;
import com.demo.service.ConfigService;
import com.demo.service.impl.CheckResultsServiceImpl;
import com.demo.service.impl.ConfigServiceImpl;
import com.demo.testing.TestingService;
import com.demo.testing.TimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimerTask;

public class TimerServiceImpl implements TimerService {
    private TestingService testingService = new TestingServiceImpl();
    private ConfigService configService = new ConfigServiceImpl();
    private CheckResultsService checkResultsService = new CheckResultsServiceImpl();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void runTimer(long id, Integer queryingInterval) {
        Timer timer = new Timer(id);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConfigEntity configEntity = configService.getConfigById(timer.getConfigId());
                if (configEntity != null && configEntity.isMonitored()) {
                    checkResultsService.update(testingService.performCheck(id).setLastCheck(new Date()));
                    logger.info("Refreshed check results, config id :: {}", timer.getConfigId());
                } else {
                    logger.info("Task was cancelled, config id :: {}", timer.getConfigId());
                    cancel();
                }
            }
        }, queryingInterval, queryingInterval);
    }
}
