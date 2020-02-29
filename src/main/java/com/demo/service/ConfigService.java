package com.demo.service;

import com.demo.data.domain.ConfigEntity;

import java.util.Map;

public interface ConfigService {

    void deleteConfig(long id);

    ConfigEntity addNewConfig(Map<String, String[]> parameters);
}
