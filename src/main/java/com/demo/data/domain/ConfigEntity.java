package com.demo.data.domain;

import java.io.Serializable;
import java.util.Objects;

public class ConfigEntity implements Serializable {
    private static final long serialVersionUID = -143068844559486110L;
    private Long id;
    private String url;
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

    public String getUrl() {
        return url;
    }

    public ConfigEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ConfigEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getQueryingInterval() {
        return queryingInterval;
    }

    public ConfigEntity setQueryingInterval(Integer queryingInterval) {
        this.queryingInterval = queryingInterval;
        return this;
    }

    public Integer getResponseTimeOk() {
        return responseTimeOk;
    }

    public ConfigEntity setResponseTimeOk(Integer responseTimeOk) {
        this.responseTimeOk = responseTimeOk;
        return this;
    }

    public Integer getResponseTimeWarning() {
        return responseTimeWarning;
    }

    public ConfigEntity setResponseTimeWarning(Integer responseTimeWarning) {
        this.responseTimeWarning = responseTimeWarning;
        return this;
    }

    public Integer getResponseTimeCritical() {
        return responseTimeCritical;
    }

    public ConfigEntity setResponseTimeCritical(Integer responseTimeCritical) {
        this.responseTimeCritical = responseTimeCritical;
        return this;
    }

    public Integer getExpectedHttpResponseCode() {
        return expectedHttpResponseCode;
    }

    public ConfigEntity setExpectedHttpResponseCode(Integer expectedHttpResponseCode) {
        this.expectedHttpResponseCode = expectedHttpResponseCode;
        return this;
    }

    public Integer getMinExpectedResponseSize() {
        return minExpectedResponseSize;
    }

    public ConfigEntity setMinExpectedResponseSize(Integer minExpectedResponseSize) {
        this.minExpectedResponseSize = minExpectedResponseSize;
        return this;
    }

    public Integer getMaxExpectedResponseSize() {
        return maxExpectedResponseSize;
    }

    public ConfigEntity setMaxExpectedResponseSize(Integer maxExpectedResponseSize) {
        this.maxExpectedResponseSize = maxExpectedResponseSize;
        return this;
    }


    public Boolean isMonitored() {
        return monitored;
    }

    public ConfigEntity setMonitored(Boolean monitored) {
        this.monitored = monitored;
        return this;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", queryingInterval=" + queryingInterval +
                ", responseTimeOk=" + responseTimeOk +
                ", responseTimeWarning=" + responseTimeWarning +
                ", responseTimeCritical=" + responseTimeCritical +
                ", expectedHttpResponseCode=" + expectedHttpResponseCode +
                ", minExpectedResponseSize=" + minExpectedResponseSize +
                ", maxExpectedResponseSize=" + maxExpectedResponseSize +
                ", monitored=" + monitored +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigEntity)) return false;
        ConfigEntity that = (ConfigEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getQueryingInterval(), that.getQueryingInterval()) &&
                Objects.equals(getResponseTimeOk(), that.getResponseTimeOk()) &&
                Objects.equals(getResponseTimeWarning(), that.getResponseTimeWarning()) &&
                Objects.equals(getResponseTimeCritical(), that.getResponseTimeCritical()) &&
                Objects.equals(getExpectedHttpResponseCode(), that.getExpectedHttpResponseCode()) &&
                Objects.equals(getMinExpectedResponseSize(), that.getMinExpectedResponseSize()) &&
                Objects.equals(getMaxExpectedResponseSize(), that.getMaxExpectedResponseSize()) &&
                Objects.equals(monitored, that.monitored);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getQueryingInterval(), getResponseTimeOk(), getResponseTimeWarning(), getResponseTimeCritical(), getExpectedHttpResponseCode(), getMinExpectedResponseSize(), getMaxExpectedResponseSize(), monitored);
    }
}
