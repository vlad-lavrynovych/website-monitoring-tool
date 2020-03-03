package com.demo.testing.impl;

import com.demo.data.domain.CheckResultsEntity;
import com.demo.data.domain.ConfigEntity;
import com.demo.data.dto.CheckResultDto;
import com.demo.data.enums.StatusEnum;
import com.demo.service.ConfigService;
import com.demo.service.impl.ConfigServiceImpl;
import com.demo.testing.TestingService;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestingServiceImpl implements TestingService {
    private static Logger logger = LoggerFactory.getLogger(TestingServiceImpl.class);
    private ConfigService configService = new ConfigServiceImpl();

    static {
        BasicConfigurator.configure();
    }

    @Override
    public CheckResultsEntity performCheck(long configId) {
        ConfigEntity config = configService.getConfigById(configId);
        HttpURLConnection conn;
        List<CheckResultDto> results;
        long startTime;
        int code;
        int size = 0;
        long duration;
        startTime = System.currentTimeMillis();
        try {
            URL url = new URL(config.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            code = conn.getResponseCode();
            conn.getInputStream();
            size = conn.getContentLength();
            duration = System.currentTimeMillis() - startTime;
            results = getResults(config, code, duration, size);
            logger.info("Finished testing url, size: {}, code: {}, duration: {}", size, code, duration);
        } catch (IOException e) {
            duration = System.currentTimeMillis() - startTime;
            logger.error("Connection url error:", e);
            Matcher exMsgStatusCodeMatcher = Pattern.compile("^Server returned HTTP response code: (\\d{3})").matcher(e.getMessage());
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                // 404 is a special case because it will throw a FileNotFoundException instead of having "404" in the message
                code = 404;
            } else {
                code = Integer.parseInt(exMsgStatusCodeMatcher.group(1));
            }
            results = getResults(config, code, duration, size);
        }
        CheckResultsEntity checkResultsEntity = mapResultsToResponse(results, config);
        checkResultsEntity.setDuration(duration);
        checkResultsEntity.setResponseCode(code);
        checkResultsEntity.setResponseSize(size);
        return checkResultsEntity;
    }

    private List<CheckResultDto> getResults(ConfigEntity config, int code, long duration, int size) {
        return Arrays.asList(
                checkHttpResponseCode(config.getExpectedHttpResponseCode(), code),
                checkResponseSize(config.getMinExpectedResponseSize(),
                        config.getMaxExpectedResponseSize(),
                        size),
                checkResponseTime(
                        config.getResponseTimeOk(),
                        config.getResponseTimeWarning(),
                        config.getResponseTimeCritical(),
                        duration
                )
        );
    }

    private CheckResultDto checkHttpResponseCode(int expected, int actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (expected == actual) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: Invalid Http response code");
        }
        return checkResultDto;
    }

    private CheckResultDto checkResponseSize(int min, int max, int actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (actual >= min && actual <= max) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: Invalid response size");
        }
        return checkResultDto;
    }

    private CheckResultDto checkResponseTime(long ok, long warning, long critical, long actual) {
        CheckResultDto checkResultDto = new CheckResultDto();
        if (actual <= ok) {
            checkResultDto.setStatus(StatusEnum.OK);
        } else if (actual <= warning) {
            checkResultDto.setStatus(StatusEnum.WARNING);
            checkResultDto.setDetails("WARNING: response time is longer than requested");
        } else {
            checkResultDto.setStatus(StatusEnum.CRITICAL);
            checkResultDto.setDetails("CRITICAL: response time is critically long");
        }
        return checkResultDto;
    }

    private CheckResultsEntity mapResultsToResponse(List<CheckResultDto> list, ConfigEntity configEntity) {
        CheckResultsEntity checkResultsEntity = new CheckResultsEntity();
        checkResultsEntity.setId(configEntity.getId());
        checkResultsEntity.setLastCheck(new Date());
        checkResultsEntity.setUrl(configEntity.getUrl());
        checkResultsEntity.setMonitored(configEntity.isMonitored());

        StringBuilder description = new StringBuilder();
        String status = getGeneralStatus(list);
        checkResultsEntity.setStatus(status);

        list.forEach(s -> {
            if (!s.getStatus().equals(StatusEnum.OK)) {
                description.append(s.getDetails()).append("\n");
            }
        });

        if (description.toString().isEmpty()) {
            checkResultsEntity.setDetails("Site is available");
        } else {
            checkResultsEntity.setDetails(description.toString());
        }

        return checkResultsEntity;
    }

    private String getGeneralStatus(List<CheckResultDto> list) {
        return Objects.requireNonNull(list.stream().map(CheckResultDto::getStatus)
                .filter(s -> s.equals(StatusEnum.CRITICAL))
                .findFirst()
                .orElseGet(() ->
                        list.stream().map(CheckResultDto::getStatus)
                                .filter(s -> s.equals(StatusEnum.WARNING))
                                .findFirst()
                                .orElseGet(() ->
                                        list.stream().map(CheckResultDto::getStatus)
                                                .filter(s -> s.equals(StatusEnum.OK))
                                                .findFirst()
                                                .orElse(null)))).getValue();
    }

}
