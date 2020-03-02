package com.demo.service;

import com.demo.data.domain.CheckResultsEntity;

import java.util.List;

public interface CheckResultsService {
    CheckResultsEntity addResult(CheckResultsEntity checkResultsEntity);

    CheckResultsEntity update(CheckResultsEntity checkResultsEntity);

    void deleteById(long id);

    List<CheckResultsEntity> findAll();
}
