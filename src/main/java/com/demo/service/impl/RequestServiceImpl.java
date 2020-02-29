package com.demo.service.impl;

import com.demo.data.domain.ConfigEntity;
import com.demo.service.ConfigService;
import com.demo.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestServiceImpl implements RequestService {
    private ConfigService configService = new ConfigServiceImpl();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processCreatingRequest(HttpServletRequest request, HttpServletResponse response) {
        ConfigEntity configEntity = configService.addNewConfig(request.getParameterMap());
        List<ConfigEntity> configs = (List<ConfigEntity>) request.getSession().getAttribute("configs");
        if (configs == null) {
            configs = new ArrayList<>();
        }
        configs.add(configEntity);
        try {
            request.getSession().setAttribute("configs", configs);
            response.sendRedirect("/test");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }

    }

    @Override
    public void processDeletingRequest(HttpServletRequest request, HttpServletResponse response, long configId) {
        configService.deleteConfig(configId);
        List<ConfigEntity> list = (List<ConfigEntity>) request.getSession().getAttribute("configs");
        list.remove(list.stream().filter(s -> s.getId() == configId).findFirst().get());
        request.setAttribute("configs", list);
        try {
            response.sendRedirect("/test");
        } catch (IOException e) {
            logger.error("Redirection error ", e);
        }
    }
}
