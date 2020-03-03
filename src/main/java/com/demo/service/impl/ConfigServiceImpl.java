package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.data.repository.ConfigRepository;
import com.demo.data.repository.ConfigRepositoryImpl;
import com.demo.service.ConfigService;

public class ConfigServiceImpl implements ConfigService {
    private ConfigRepository configRepository = new ConfigRepositoryImpl();

    @Override
    public ConfigEntity addNewConfig(ConfigEntity configEntity) {
        return configRepository.save(configEntity).orElse(null);
    }

    @Override
    public void updateMonitoringStatus(long id, boolean status) {
        configRepository.updateMonitoringStatus(id, status);
    }

    @Override
    public ConfigEntity getConfigById(long configId) {
        return configRepository.findById(configId).orElse(null);
    }

    @Override
    public void deleteConfig(long id) {
        configRepository.delete(id);
    }

}
