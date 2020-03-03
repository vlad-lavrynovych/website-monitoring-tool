package com.demo.controller;

import com.demo.service.RequestService;
import com.demo.service.impl.RequestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/update"})
public class RefreshController extends HttpServlet {
    private RequestService requestService = new RequestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestService.processRefreshRequest(req, resp);
    }
}
