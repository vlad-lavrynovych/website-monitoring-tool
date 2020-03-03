package com.demo.service.impl;

import com.demo.data.domain.CheckResultsEntity;
import com.demo.data.domain.ConfigEntity;
import com.demo.service.CheckResultsService;
import com.demo.service.ConfigService;
import com.demo.service.RequestService;
import com.demo.testing.TestingService;
import com.demo.testing.TimerService;
import com.demo.testing.impl.TestingServiceImpl;
import com.demo.testing.impl.TimerServiceImpl;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RequestServiceImpl implements RequestService {
    private static Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);
    private ConfigService configService = new ConfigServiceImpl();

    static {
        BasicConfigurator.configure();
    }

    private TimerService timerService = new TimerServiceImpl();
    private CheckResultsService checkResultsService = new CheckResultsServiceImpl();

    private TestingService testingService = new TestingServiceImpl();

    @Override
    public void processCreatingRequest(HttpServletRequest request, HttpServletResponse response) {
        ConfigEntity configEntity = configService.addNewConfig(mapParametersToConfigEntity(request.getParameterMap()));
        checkResultsService.addResult(testingService.performCheck(configEntity.getId()));
        List<CheckResultsEntity> list = checkResultsService.findAll();
        timerService.runTimer(configEntity.getId(), configEntity.getQueryingInterval());
        request.getSession().setAttribute("data", list);
        try {
            response.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }

    }

    @Override
    public void processDeletingRequest(HttpServletRequest request, HttpServletResponse response, long configId) {
        configService.deleteConfig(configId);
        checkResultsService.deleteById(configId);
        List<CheckResultsEntity> list = (List<CheckResultsEntity>) request.getSession().getAttribute("data");
        list.remove(list.stream().filter(s -> s.getId() == configId).findFirst().get());
        request.setAttribute("data", list);
        try {
            response.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }
    }

    @Override
    public void processChangeMonitoringStatusRequest(HttpServletRequest req, HttpServletResponse resp, long id) {
        List<CheckResultsEntity> list = (List<CheckResultsEntity>) req.getSession().getAttribute("data");
        CheckResultsEntity checkResult = list.stream().filter(s -> s.getId() == id).findFirst().get();
        configService.updateMonitoringStatus(id, !checkResult.isMonitored());
        checkResult.setMonitored(!checkResult.isMonitored());
        if (checkResult.isMonitored())
            timerService.runTimer(checkResult.getId(), configService.getConfigById(id).getQueryingInterval());
        checkResultsService.update(checkResult);
        req.setAttribute("data", list);
        try {
            resp.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }
    }

    @Override
    public void processRefreshRequest(HttpServletRequest req, HttpServletResponse resp) {
        List<CheckResultsEntity> list = checkResultsService.findAll();
        req.getSession().setAttribute("data", list);
        try {
            resp.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }
    }

    private ConfigEntity mapParametersToConfigEntity(Map<String, String[]> parameters) {
        return new ConfigEntity()
                .setUrl(parameters.get("url")[0])
                .setQueryingInterval(Integer.valueOf(parameters.get("queryingInterval")[0]))
                .setResponseTimeOk(Integer.valueOf(parameters.get("responseTimeOk")[0]))
                .setResponseTimeWarning(Integer.valueOf(parameters.get("responseTimeWarning")[0]))
                .setResponseTimeCritical(Integer.valueOf(parameters.get("responseTimeCritical")[0]))
                .setExpectedHttpResponseCode(Integer.valueOf(parameters.get("expectedHttpCode")[0]))
                .setMinExpectedResponseSize(Integer.valueOf(parameters.get("minExpectedResponseSize")[0]))
                .setMaxExpectedResponseSize(Integer.valueOf(parameters.get("maxExpectedResponseSize")[0]));
    }
}
