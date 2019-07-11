package com.codingexercise.matchingjobs.controller;

import com.codingexercise.matchingjobs.dtos.WorkerMatchingJobs;
import com.codingexercise.matchingjobs.exception.MatchJobsException;
import com.codingexercise.matchingjobs.handlers.MatchingJobsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MatchJobs")
public class MatchingJobsController  {

    @Autowired
    private MatchingJobsHandler matchingJobsHandler;

    @GetMapping(value = "/{workerid}")
    public ResponseEntity<WorkerMatchingJobs> findJobsById(@PathVariable("workerid") String workerId) throws MatchJobsException {

        return matchingJobsHandler.findJobsById(workerId);
    }
}
