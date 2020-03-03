package com.demo.data.repository;

import com.demo.data.domain.CheckResultsEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckResultsRepositoryImplTest {
    private static CheckResultsEntity checkResultsEntity;
    private CheckResultsRepository checkResultsRepository = new CheckResultsRepositoryImpl();

    @BeforeAll
    static void init() {
        checkResultsEntity = new CheckResultsEntity()
                .setId(1L)
                .setUrl("https://www.wikipedia.org/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000);
    }

    @Test
    void shouldPassWhenSavedRetrievedAndDeletedSuccessfully() {
        CheckResultsEntity result = checkResultsRepository.save(checkResultsEntity.setId(1L)).orElse(null);
        assertNotNull(result);
        assertTrue(checkResultsRepository.findById(result.getId()).isPresent());
        checkResultsRepository.delete(result.getId());
        assertFalse(checkResultsRepository.findById(result.getId()).isPresent());
    }

    @Test
    void shouldPassWhenUpdateMonitoringStatusSuccessfully() {
        CheckResultsEntity result = checkResultsRepository.save(checkResultsEntity).get();
        checkResultsRepository.updateMonitoringStatus(result.getId(), false);
        assertFalse(checkResultsRepository.findById(result.getId()).get().isMonitored());
        checkResultsRepository.delete(result.getId());
    }

    @Test
    void shouldPassWhenAddedAndSelectedAllResultsSuccessfully() {
        CheckResultsEntity result1 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(1L)
                .setStatus("WARNING")
                .setUrl("https://www.wikipedia.org/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        CheckResultsEntity result2 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(2L)
                .setStatus("WARNING")
                .setUrl("https://www.wikipedia.org/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        CheckResultsEntity result3 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(3L)
                .setStatus("WARNING")
                .setUrl("https://www.wikipedia.org/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        List<CheckResultsEntity> actual = checkResultsRepository.findAll();
        assertTrue(actual.contains(result1));
        assertTrue(actual.contains(result2));
        assertTrue(actual.contains(result3));
        checkResultsRepository.delete(result1.getId());
        checkResultsRepository.delete(result2.getId());
        checkResultsRepository.delete(result3.getId());
    }
}