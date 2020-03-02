package com.demo.service.impl;

import com.demo.data.domain.CheckResultsEntity;
import com.demo.data.domain.ConfigEntity;
import com.demo.service.*;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestServiceImpl implements RequestService {
    private static Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);
    private ConfigService configService = new ConfigServiceImpl();

    // FIXME: 02.03.2020 WHEN DELETE CONFIG STILL GET RESULT FROM DATABASE12
    static {
        BasicConfigurator.configure();
    }

    private TimerService timerService = new TimerServiceImpl();
    private CheckResultsService checkResultsService = new CheckResultsServiceImpl();

    private TestingService testingService = new TestingServiceImpl();

    @Override
    public void processCreatingRequest(HttpServletRequest request, HttpServletResponse response) {
        ConfigEntity configEntity = configService.addNewConfig(request.getParameterMap());
        List<CheckResultsEntity> data = ((List<CheckResultsEntity>) request.getSession().getAttribute("data"));
        if (data == null) {
            data = new ArrayList<>();
        }
        checkResultsService.addResult(testingService.performCheck(configEntity.getId()));
        List<CheckResultsEntity> list = checkResultsService.findAll();
        data.addAll(list);
        timerService.runTimer(configEntity.getId(), configEntity.getQueryingInterval());
        request.getSession().setAttribute("data", data);
        try {
            response.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }

    }

    @Override
    public void processDeletingRequest(HttpServletRequest request, HttpServletResponse response, long configId) {
        configService.deleteConfig(configId);
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
}
