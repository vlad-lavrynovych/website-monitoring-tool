package com.demo.service;

import com.demo.data.domain.ConfigEntity;

public interface ConfigService {

    void deleteConfig(long id);

    ConfigEntity addNewConfig(ConfigEntity parameters);

    void updateMonitoringStatus(long id, boolean status);

    ConfigEntity getConfigById(long configId);
}
