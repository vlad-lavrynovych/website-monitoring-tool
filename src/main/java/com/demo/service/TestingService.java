package com.demo.service;

import com.demo.data.domain.ConfigEntity;
import com.demo.data.dto.ConfigCheckInfoDto;

public interface TestingService {
    ConfigCheckInfoDto performCheck(ConfigEntity configEntity);
}
