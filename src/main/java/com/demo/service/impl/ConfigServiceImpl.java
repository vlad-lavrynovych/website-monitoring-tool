package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.data.repository.ConfigRepository;
import com.demo.data.repository.ConfigRepositoryImpl;
import com.demo.service.ConfigService;

import java.util.Map;

public class ConfigServiceImpl implements ConfigService {
    private ConfigRepository configRepository = new ConfigRepositoryImpl();

    @Override
    public ConfigEntity addNewConfig(Map<String, String[]> parameters) {
        ConfigEntity configEntity = mapParametersToConfigEntity(parameters);
        return configRepository.save(configEntity).orElse(null);
    }

    @Override
    public void updateMonitoringStatus(long id, boolean status) {
        configRepository.updateMonitoringStatus(id, status);
    }

    @Override
    public void deleteConfig(long id) {
        configRepository.delete(id);
    }

    private ConfigEntity mapParametersToConfigEntity(Map<String, String[]> parameters) {
        return new ConfigEntity()
                .setUrl(parameters.get("url")[0])
                .setQueryingInterval(Integer.valueOf(parameters.get("queryingInterval")[0]))
                .setResponseTimeOk(Integer.valueOf(parameters.get("responseTimeOk")[0]))
                .setResponseTimeWarning(Integer.valueOf(parameters.get("responseTimeWarning")[0]))
                .setResponseTimeCritical(Integer.valueOf(parameters.get("responseTimeCritical")[0]))
                .setExpectedHttpResponseCode(Integer.valueOf(parameters.get("expectedHttpCode")[0]))
                .setMinExpectedResponseSize(Integer.valueOf(parameters.get("minExpectedResponseSize")[0]))
                .setMaxExpectedResponseSize(Integer.valueOf(parameters.get("maxExpectedResponseSize")[0]));
    }
}
