package com.demo.data.repository;

import com.demo.data.domain.CheckResultsEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
                .setUrl("localhost:8080/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000);
    }

    @Test
    void shouldPassWhenSavedRetrievedAndDeletedSuccessfully() {
        CheckResultsEntity conf = checkResultsRepository.save(checkResultsEntity.setId(1L)).orElse(null);
        assertNotNull(conf);
        assertTrue(checkResultsRepository.findById(conf.getId()).isPresent());
        checkResultsRepository.delete(conf.getId());
        assertFalse(checkResultsRepository.findById(conf.getId()).isPresent());
    }

    @Test
    void shouldPassWhenUpdateMonitoringStatusSuccessfully() {
        CheckResultsEntity conf = checkResultsRepository.save(checkResultsEntity).get();
        checkResultsRepository.updateMonitoringStatus(conf.getId(), false);
        assertFalse(checkResultsRepository.findById(conf.getId()).get().isMonitored());
        checkResultsRepository.delete(conf.getId());
    }

    @Test
    void shouldPassWhenAddedAndSelectedAllResultsSuccessfully() {
        CheckResultsEntity conf1 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(1L)
                .setStatus("WARNING")
                .setUrl("localhost:8080/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        CheckResultsEntity conf2 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(2L)
                .setStatus("WARNING")
                .setUrl("localhost:8080/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        CheckResultsEntity conf3 = checkResultsRepository.save(new CheckResultsEntity()
                .setId(3L)
                .setStatus("WARNING")
                .setUrl("localhost:8080/")
                .setDuration((long) 2000)
                .setLastCheck(new Date())
                .setMonitored(true)
                .setDetails("403")
                .setResponseSize(1000)
                .setResponseCode(10000)).get();
        List<CheckResultsEntity> actual = checkResultsRepository.findAll();
        List<CheckResultsEntity> expected = Arrays.asList(conf1, conf2, conf3);
        assertEquals(expected, actual);
        checkResultsRepository.delete(conf1.getId());
        checkResultsRepository.delete(conf2.getId());
        checkResultsRepository.delete(conf3.getId());
    }
}