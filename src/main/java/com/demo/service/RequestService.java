package com.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestService {
    void processCreatingRequest(HttpServletRequest request, HttpServletResponse response);

    void processDeletingRequest(HttpServletRequest request, HttpServletResponse response, long configId);
}
