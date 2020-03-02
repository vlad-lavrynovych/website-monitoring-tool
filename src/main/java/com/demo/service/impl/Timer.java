package com.demo.service.impl;

public class Timer extends java.util.Timer {
    private long configId;

    public Timer(long configId) {
        this.configId = configId;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }
}
