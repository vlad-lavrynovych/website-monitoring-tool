package com.demo.testing;

import com.demo.data.domain.CheckResultsEntity;

public interface TestingService {
    CheckResultsEntity performCheck(long configId);
}
