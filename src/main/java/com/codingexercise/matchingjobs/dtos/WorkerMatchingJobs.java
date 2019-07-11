package com.codingexercise.matchingjobs.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WorkerMatchingJobs {

    @JsonProperty("Status")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Data")
    private List<Job> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Message")
    private String message;

    public WorkerMatchingJobs() {}

    public WorkerMatchingJobs(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Job> getData() {
        return data;
    }

    public void setData(List<Job> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
