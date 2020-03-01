package com.demo.controller;

import com.demo.service.RequestService;
import com.demo.service.impl.RequestServiceImpl;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/*")
public class ConfigController extends HttpServlet {
    private RequestService requestService = new RequestServiceImpl();
    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    static {
        BasicConfigurator.configure();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String value = uri.substring(uri.lastIndexOf("/") + 1);
        if (value.equals("create")) {
            logger.info("Received creation request");
            requestService.processCreatingRequest(req, resp);
        } else if (uri.contains("changeMonitoringStatus")) {
            requestService.processChangeMonitoringStatusRequest(req, resp, Long.parseLong(value));
        } else {
            logger.info("Received deletion request, id :: {}", value);
            requestService.processDeletingRequest(req, resp, Long.parseLong(value));
        }
    }

}
