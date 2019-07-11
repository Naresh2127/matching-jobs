package com.codingexercise.matchingjobs.handlers;

import com.codingexercise.matchingjobs.dtos.Job;
import com.codingexercise.matchingjobs.dtos.JobSearchAddress;
import com.codingexercise.matchingjobs.dtos.Worker;
import com.codingexercise.matchingjobs.dtos.WorkerMatchingJobs;
import com.codingexercise.matchingjobs.exception.MatchJobsException;
import com.codingexercise.matchingjobs.service.SwipeJobsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MatchingJobsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingJobsHandler.class);

    @Autowired
    private SwipeJobsService swipeJobsService;

    public ResponseEntity<WorkerMatchingJobs> findJobsById(String workerId) throws MatchJobsException {

        List<Job> filteredJobList;
        try{
            List<Worker> workerList = swipeJobsService.retrieveWorkers();
            List<Job> jobList = swipeJobsService.retrieveJobs();

            //lookup for the worker
            Optional<Worker> worker = workerList.stream().filter(worker1 -> workerId.equals(worker1.getUserId().toString())).findFirst();
            if(worker.isPresent()){
                Worker workerDetails = worker.get();

                //check if the worker is active
                if(workerDetails.getIsActive()){
                    //Filter jobs in the order of skills, required certificates for the job opportunity, driverlicense requirement and if the job is within the maximum distance, and then returns only first 3 matching jobs as a List.
                    filteredJobList = jobList.stream()
                            .filter(job -> (workerDetails.getSkills().contains(job.getJobTitle()) && job.getRequiredCertificates().stream().allMatch(s -> workerDetails.getCertificates().contains(s))
                                    && (!job.getDriverLicenseRequired() || job.getDriverLicenseRequired() == workerDetails.getHasDriversLicense()) && isJobWithinMaxDistance(workerDetails, job))).limit(3).collect(Collectors.toList());
                } else
                    return new ResponseEntity<>(new WorkerMatchingJobs("success", "User is inactive"), HttpStatus.OK);
                if(filteredJobList == null || filteredJobList.size() == 0)
                    return new ResponseEntity<>(new WorkerMatchingJobs("success", "No matching jobs found"), HttpStatus.NOT_FOUND);

                WorkerMatchingJobs workerMatchingJobs = new WorkerMatchingJobs();
                workerMatchingJobs.setStatus("success");
                workerMatchingJobs.setData(filteredJobList);
                return new ResponseEntity<>(workerMatchingJobs, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new WorkerMatchingJobs("success", "Worker not found"), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isJobWithinMaxDistance(Worker worker, Job job){
        JobSearchAddress jobSearchAddress = worker.getJobSearchAddress();
        if(calculateDistanceInKilometer(Double.parseDouble(jobSearchAddress.getLatitude()), Double.parseDouble(jobSearchAddress.getLongitude()), Double.parseDouble(job.getLocation().getLatitude()), Double.parseDouble(job.getLocation().getLongitude())) <= jobSearchAddress.getMaxJobDistance()){
            return true;
        }
        return false;
    }

    //This function calculates the distance between two given latitudes and longitudes
    private int calculateDistanceInKilometer(double workerLat, double wokerLng,
                                             double jobLat, double jobLng) {

        final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
        double latDistance = Math.toRadians(workerLat - jobLat);
        double lngDistance = Math.toRadians(wokerLng - jobLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(workerLat)) * Math.cos(Math.toRadians(jobLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

}
