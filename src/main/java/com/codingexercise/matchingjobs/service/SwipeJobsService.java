package com.codingexercise.matchingjobs.service;

import com.codingexercise.matchingjobs.dtos.Job;
import com.codingexercise.matchingjobs.dtos.Worker;
import com.codingexercise.matchingjobs.exception.MatchJobsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class SwipeJobsService {

    RestTemplate swipeJobsServiceRestTemplate = new RestTemplate();

    public List<Job> retrieveJobs() throws MatchJobsException{

        try{
            //calling jobs api
            ResponseEntity<Job[]> responseEntity= swipeJobsServiceRestTemplate.exchange("http://test.swipejobs.com/api/jobs", HttpMethod.GET, null, Job[].class);
            return Arrays.asList(responseEntity.getBody());
        }catch(Exception e){
            throw new MatchJobsException("backend error", e);
        }


    }

    public List<Worker> retrieveWorkers() throws  MatchJobsException{

        try{
            //calling workers api
            ResponseEntity<Worker[]> workersResponseEntity= swipeJobsServiceRestTemplate.exchange("http://test.swipejobs.com/api/workers", HttpMethod.GET, null, Worker[].class);
            return Arrays.asList(workersResponseEntity.getBody());
        }catch(Exception e){
            throw new MatchJobsException("backend error", e);
        }

    }
}
