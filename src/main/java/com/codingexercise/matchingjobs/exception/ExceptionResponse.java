package com.codingexercise.matchingjobs.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Dto used for wrapping the exception into an user friendly response
 */
public class ExceptionResponse {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Data")
    private List<String> data;

    public ExceptionResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
