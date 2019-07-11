# MatchJobs

## MatchJobs[/{workerid}]
This resource will take a workerId as input and return no more than three appropriate jobs for that Worker.

It will internally invoke the below apis to retrieve the workers and jobs info.

http://test.swipejobs.com/api/workers
http://test.swipejobs.com/api/jobs


Assumptions: 
1. userId from workers api response is considered as the workerid to find the worker details
2. If worker's isActive property is false, then no match is performed.

Matching Criteria order:
1. worker's skill matches the title
2. all the required certificates for the job are matched against the certificates required for the job
3. drivers license check is done if the job requires driving license to be present for the job
4. check if the job location is within the maximum distance provided by the worker
5. A maximum of 3 jobs are returned after matching the above criteria.

+ Request - Retrieve matching jobs for workerid 

  + http://localhost:8080/MatchJobs/30
  
+ Response 200 (application/json)

  + 
        {
          "Status": "success",
          "Data": [
              {
                  "driverLicenseRequired": true,
                  "requiredCertificates": [
                      "The Asker of Good Questions",
                      "Outstanding Memory Award"
                  ],
                  "location": {
                      "longitude": "13.996818",
                      "latitude": "49.89112"
                  },
                  "billRate": "$8.14",
                  "workersRequired": 4,
                  "startDate": "2015-11-07T21:45:22.378Z",
                  "about": "Et reprehenderit dolor tempor in laborum dolore amet nostrud enim. Commodo labore est et aliquip sunt occaecat voluptate. Laboris nisi pariatur minim nisi cillum amet deserunt aute ex dolor. Nulla exercitation eu incididunt quis deserunt sunt occaecat deserunt. Sint consectetur veniam occaecat nisi quis officia sint ullamco cupidatat qui proident.",
                  "jobTitle": "Director of First Impressions",
                  "company": "Comtest",
                  "guid": "562f66aa3b30061f253f80ee",
                  "jobId": 17
              },
              {
                  "driverLicenseRequired": true,
                  "requiredCertificates": [
                      "Excellence in Humor and Entertainment",
                      "Outstanding Memory Award"
                  ],
                  "location": {
                      "longitude": "13.967485",
                      "latitude": "49.815181"
                  },
                  "billRate": "$14.45",
                  "workersRequired": 4,
                  "startDate": "2015-11-06T07:31:28.873Z",
                  "about": "Enim tempor aliqua aute Lorem enim proident dolore reprehenderit elit irure. Ipsum nisi deserunt ad excepteur veniam esse eiusmod elit reprehenderit minim qui in nostrud culpa. Labore consectetur anim officia tempor fugiat in exercitation voluptate tempor fugiat anim. Proident laborum magna fugiat duis deserunt veniam tempor nisi non aliquip deserunt voluptate dolore.",
                  "jobTitle": "Project Meanie",
                  "company": "Lotron",
                  "guid": "562f66aa95aa7edec308aa04",
                  "jobId": 28
              },
              {
                  "driverLicenseRequired": false,
                  "requiredCertificates": [
                      "Healthy Living Promoter"
                  ],
                  "location": {
                      "longitude": "14.017374",
                      "latitude": "50.075484"
                  },
                  "billRate": "$14.50",
                  "workersRequired": 5,
                  "startDate": "2015-11-11T20:45:02.016Z",
                  "about": "Reprehenderit aute in tempor commodo sint magna. Culpa et sit sint proident laborum consectetur. Esse commodo officia id incididunt adipisicing. Culpa incididunt eu culpa deserunt eiusmod. Proident aliqua id deserunt sint ea ea excepteur officia.",
                  "jobTitle": "Director of First Impressions",
                  "company": "Eplode",
                  "guid": "562f66aaabcc1195f72254b5",
                  "jobId": 33
              }
          ]
      }


+ Request - No matching jobs found for the given workerid 

  + http://localhost:8080/MatchJobs/0
  
+ Response 200 (application/json)

  + 
        {
          "Status": "success",
          "Message": "No matching jobs found"
        }


+ Request - Given workerId is inactive

  + http://localhost:8080/MatchJobs/1
  
+ Response 200 (application/json)

  + 
  
      {
        "Status": "success",
        "Message": "User is inactive"
      }
      
      
+ Request - Given workerId is not present in the workers response returned by workers api

  + http://localhost:8080/MatchJobs/1000
  
+ Response 500 (application/json)

  +
  
      {
      "Status": "fail",
      "Message": "An error occurred while processing the request",
      "Data": [
          "Worker not found"
      ]
  }
    
