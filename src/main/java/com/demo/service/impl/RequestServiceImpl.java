package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.data.dto.ConfigCheckInfoDto;
import com.demo.service.ConfigService;
import com.demo.service.RequestService;
import com.demo.service.TestingService;
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

    static {
        BasicConfigurator.configure();
    }

    private TestingService testingService = new TestingServiceImpl();

    @Override
    public void processCreatingRequest(HttpServletRequest request, HttpServletResponse response) {
        ConfigEntity configEntity = configService.addNewConfig(request.getParameterMap());
        List<ConfigCheckInfoDto> data = (List<ConfigCheckInfoDto>) request.getSession().getAttribute("data");
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(testingService.performCheck(configEntity));
        // FIXME: 01.03.2020 if check throws exception, entity still saved to database
        try {
            request.getSession().setAttribute("data", data);
            response.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }

    }

    @Override
    public void processDeletingRequest(HttpServletRequest request, HttpServletResponse response, long configId) {
        configService.deleteConfig(configId);
        List<ConfigCheckInfoDto> list = (List<ConfigCheckInfoDto>) request.getSession().getAttribute("data");
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
        List<ConfigCheckInfoDto> list = (List<ConfigCheckInfoDto>) req.getSession().getAttribute("data");
        ConfigCheckInfoDto config = list.stream().filter(s -> s.getId() == id).findFirst().get();
        configService.updateMonitoringStatus(id, !config.isMonitored());
        config.setMonitored(!config.isMonitored());
        req.setAttribute("data", list);
        try {
            resp.sendRedirect("/app");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }
    }
}
