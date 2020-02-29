package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.data.repository.ConfigRepository;
import com.demo.data.repository.ConfigRepositoryImpl;
import com.demo.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ConfigServiceImpl implements ConfigService {
    private ConfigRepository configRepository = new ConfigRepositoryImpl();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ConfigEntity addNewConfig(Map<String, String[]> parameters) {
        ConfigEntity configEntity = mapParametersToConfigEntity(parameters);
        return configRepository.save(configEntity).orElse(null);
    }

    @Override
    public void deleteConfig(long id) {
        configRepository.delete(id);
    }

    private ConfigEntity mapParametersToConfigEntity(Map<String, String[]> parameters) {
        return new ConfigEntity()
                .setUrl(parameters.get("url")[0])
                .setQueryingInterval(parseStringTimeToSeconds(parameters.get("queryingInterval")[0]))
                .setResponseTimeOk(parseStringTimeToSeconds(parameters.get("responseTimeOk")[0]))
                .setResponseTimeWarning(parseStringTimeToSeconds(parameters.get("responseTimeWarning")[0]))
                .setResponseTimeCritical(parseStringTimeToSeconds(parameters.get("responseTimeCritical")[0]))
                .setExpectedHttpResponseCode(Integer.valueOf(parameters.get("expectedHttpCode")[0]))
                .setMinExpectedResponseSize(Integer.valueOf(parameters.get("minExpectedResponseSize")[0]))
                .setMaxExpectedResponseSize(Integer.valueOf(parameters.get("maxExpectedResponseSize")[0]));
    }

    private int parseStringTimeToSeconds(String value) {
        String[] values = value.split(":");
        return Integer.parseInt(values[0]) * 3600 + Integer.parseInt(values[1]) * 60 + Integer.parseInt(values[2]);
    }
}
