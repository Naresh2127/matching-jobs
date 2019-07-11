package com.codingexercise.matchingjobs.exception;

public class MatchJobsException extends Exception{

    public MatchJobsException(String message) {
        super(message);
    }

    public MatchJobsException(String message, Exception e)  {
        super(message, e);
    }
}
