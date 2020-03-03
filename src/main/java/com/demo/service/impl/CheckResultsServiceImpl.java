package com.demo.service.impl;

import com.demo.data.domain.CheckResultsEntity;
import com.demo.data.repository.CheckResultsRepository;
import com.demo.data.repository.CheckResultsRepositoryImpl;
import com.demo.service.CheckResultsService;

import java.util.List;

public class CheckResultsServiceImpl implements CheckResultsService {
    private CheckResultsRepository checkResultsRepository = new CheckResultsRepositoryImpl();

    @Override
    public CheckResultsEntity addResult(CheckResultsEntity checkResultsEntity) {
        return checkResultsRepository.save(checkResultsEntity).orElse(null);
    }

    @Override
    public CheckResultsEntity update(CheckResultsEntity checkResultsEntity) {
        return checkResultsRepository.updateCheckResult(checkResultsEntity).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        checkResultsRepository.delete(id);
    }

    @Override
    public List<CheckResultsEntity> findAll() {
        return checkResultsRepository.findAll();
    }
}
