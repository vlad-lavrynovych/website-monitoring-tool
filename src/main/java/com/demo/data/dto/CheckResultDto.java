package com.demo.data.dto;

import java.util.Date;
import java.util.Objects;

public class CheckResultDto {
    private Long configId;
    private String serviceName;
    private Date lastCheck;
    private Integer duration;
    private String details;

    public CheckResultDto() {
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckResultDto)) return false;
        CheckResultDto that = (CheckResultDto) o;
        return Objects.equals(getConfigId(), that.getConfigId()) &&
                Objects.equals(getServiceName(), that.getServiceName()) &&
                Objects.equals(getLastCheck(), that.getLastCheck()) &&
                Objects.equals(getDuration(), that.getDuration()) &&
                Objects.equals(getDetails(), that.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConfigId(), getServiceName(), getLastCheck(), getDuration(), getDetails());
    }


    @Override
    public String toString() {
        return "CheckResultDto{" +
                "configId=" + configId +
                ", serviceName='" + serviceName + '\'' +
                ", lastCheck=" + lastCheck +
                ", duration=" + duration +
                ", details='" + details + '\'' +
                '}';
    }
}
