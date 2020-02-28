package com.demo.data.domain;

import java.io.Serializable;
import java.util.Objects;

public class ConfigEntity implements Serializable {
    private static final long serialVersionUID = -143068844559486110L;
    private Long id;
    // all intervals are in millis
    private Integer queryingInterval;
    private Integer responseTimeOk;
    private Integer responseTimeWarning;
    private Integer responseTimeCritical;
    private Integer expectedHttpResponseCode;
    // response size in bytes
    private Integer minExpectedResponseSize;
    private Integer maxExpectedResponseSize;
    private Boolean monitored = true;

    //    private String expectedSubstringInResponse;

    public ConfigEntity() {
    }

    public ConfigEntity(Long id, Integer queryingInterval, Integer responseTimeOk, Integer responseTimeWarning, Integer responseTimeCritical, Integer expectedHttpResponseCode, Integer minExpectedResponseSize, Integer maxExpectedResponseSize
//            , String expectedSubstringInResponse
    ) {
        this.id = id;
        this.queryingInterval = queryingInterval;
        this.responseTimeOk = responseTimeOk;
        this.responseTimeWarning = responseTimeWarning;
        this.responseTimeCritical = responseTimeCritical;
        this.expectedHttpResponseCode = expectedHttpResponseCode;
        this.minExpectedResponseSize = minExpectedResponseSize;
        this.maxExpectedResponseSize = maxExpectedResponseSize;
//        this.expectedSubstringInResponse = expectedSubstringInResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQueryingInterval() {
        return queryingInterval;
    }

    public void setQueryingInterval(Integer queryingInterval) {
        this.queryingInterval = queryingInterval;
    }

    public Integer getResponseTimeOk() {
        return responseTimeOk;
    }

    public void setResponseTimeOk(Integer responseTimeOk) {
        this.responseTimeOk = responseTimeOk;
    }

    public Integer getResponseTimeWarning() {
        return responseTimeWarning;
    }

    public void setResponseTimeWarning(Integer responseTimeWarning) {
        this.responseTimeWarning = responseTimeWarning;
    }

    public Integer getResponseTimeCritical() {
        return responseTimeCritical;
    }

    public void setResponseTimeCritical(Integer responseTimeCritical) {
        this.responseTimeCritical = responseTimeCritical;
    }

    public Integer getExpectedHttpResponseCode() {
        return expectedHttpResponseCode;
    }

    public void setExpectedHttpResponseCode(Integer expectedHttpResponseCode) {
        this.expectedHttpResponseCode = expectedHttpResponseCode;
    }

    public Integer getMinExpectedResponseSize() {
        return minExpectedResponseSize;
    }

    public void setMinExpectedResponseSize(Integer minExpectedResponseSize) {
        this.minExpectedResponseSize = minExpectedResponseSize;
    }

    public Integer getMaxExpectedResponseSize() {
        return maxExpectedResponseSize;
    }

    public void setMaxExpectedResponseSize(Integer maxExpectedResponseSize) {
        this.maxExpectedResponseSize = maxExpectedResponseSize;
    }


    public Boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(Boolean monitored) {
        this.monitored = monitored;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigEntity)) return false;
        ConfigEntity that = (ConfigEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getQueryingInterval(), that.getQueryingInterval()) &&
                Objects.equals(getResponseTimeOk(), that.getResponseTimeOk()) &&
                Objects.equals(getResponseTimeWarning(), that.getResponseTimeWarning()) &&
                Objects.equals(getResponseTimeCritical(), that.getResponseTimeCritical()) &&
                Objects.equals(getExpectedHttpResponseCode(), that.getExpectedHttpResponseCode()) &&
                Objects.equals(getMinExpectedResponseSize(), that.getMinExpectedResponseSize()) &&
                Objects.equals(getMaxExpectedResponseSize(), that.getMaxExpectedResponseSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQueryingInterval(), getResponseTimeOk(), getResponseTimeWarning(), getResponseTimeCritical(), getExpectedHttpResponseCode(), getMinExpectedResponseSize(), getMaxExpectedResponseSize());
    }
}
