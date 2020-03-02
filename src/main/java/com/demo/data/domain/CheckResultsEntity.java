package com.demo.data.domain;

import java.util.Date;
import java.util.Objects;

public class CheckResultsEntity {
    private Long id;
    private String url;
    private boolean monitored;
    private String status;
    private String details;
    private Date lastCheck;
    private Long duration;
    private Integer responseCode;
    private Integer responseSize;

    public CheckResultsEntity() {
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public CheckResultsEntity setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public Integer getResponseSize() {
        return responseSize;
    }

    public CheckResultsEntity setResponseSize(Integer responseSize) {
        this.responseSize = responseSize;
        return this;

    }

    public String getUrl() {
        return url;
    }

    public CheckResultsEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CheckResultsEntity setId(Long id) {
        this.id = id;
        return this;

    }

    public boolean isMonitored() {
        return monitored;
    }

    public CheckResultsEntity setMonitored(boolean monitored) {
        this.monitored = monitored;
        return this;

    }

    public String getStatus() {
        return status;
    }

    public CheckResultsEntity setStatus(String status) {
        this.status = status;
        return this;

    }

    public String getDetails() {
        return details;
    }

    public CheckResultsEntity setDetails(String details) {
        this.details = details;
        return this;

    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public CheckResultsEntity setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
        return this;

    }

    public Long getDuration() {
        return duration;
    }

    public CheckResultsEntity setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckResultsEntity)) return false;
        CheckResultsEntity that = (CheckResultsEntity) o;
        return isMonitored() == that.isMonitored() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getLastCheck(), that.getLastCheck()) &&
                Objects.equals(getDuration(), that.getDuration()) &&
                Objects.equals(getResponseCode(), that.getResponseCode()) &&
                Objects.equals(getResponseSize(), that.getResponseSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), isMonitored(), getStatus(), getDetails(), getLastCheck(), getDuration(), getResponseCode(), getResponseSize());
    }
}
