package com.codingexercise.matchingjobs.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("fail");
        response.setMessage("An error occurred while processing the request");
        response.setData(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ExceptionResponse> handleHttpClientErrorExceptionException(HttpClientErrorException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("fail");
        response.setMessage("An error occurred while processing the request");
        response.setData(Arrays.asList(ex.getResponseBodyAsString()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity<ExceptionResponse> handleHttpServerErrorException(HttpServerErrorException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("fail");
        response.setMessage("An error occurred while processing the request");
        response.setData(Arrays.asList(ex.getResponseBodyAsString()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = MatchJobsException.class)
    public ResponseEntity<ExceptionResponse> handleMatchJobsException(MatchJobsException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("fail");
        response.setMessage("An error occurred while processing the request");
        response.setData(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
