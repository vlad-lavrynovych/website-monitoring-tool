package com.demo.data.dto;

import java.util.Date;
import java.util.Objects;

public class ConfigCheckInfoDto {
    private Long id;
    private String url;
    private Integer queryingInterval;
    private boolean monitored;
    private String status;
    private String details;
    private Date lastCheck;
    private Long duration;
    private Integer responseCode;
    private Integer responseSize;

    public ConfigCheckInfoDto() {
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(Integer responseSize) {
        this.responseSize = responseSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigCheckInfoDto)) return false;
        ConfigCheckInfoDto that = (ConfigCheckInfoDto) o;
        return isMonitored() == that.isMonitored() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getQueryingInterval(), that.getQueryingInterval()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getLastCheck(), that.getLastCheck()) &&
                Objects.equals(getDuration(), that.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQueryingInterval(), isMonitored(), getStatus(), getDetails(), getLastCheck(), getDuration());
    }

}
