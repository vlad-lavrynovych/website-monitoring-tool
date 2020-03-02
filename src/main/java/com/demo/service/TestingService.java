package com.demo.service;

import com.demo.data.domain.CheckResultsEntity;

public interface TestingService {
    CheckResultsEntity performCheck(long configId);
}
