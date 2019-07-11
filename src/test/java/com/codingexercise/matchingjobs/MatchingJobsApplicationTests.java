package com.codingexercise.matchingjobs;

import com.codingexercise.matchingjobs.service.SwipeJobsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingJobsApplicationTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	private SwipeJobsService swipeJobsService;

	private TestUtils testUtils = new TestUtils();

	@Before
	public void setup () {
		this.mockMvc =  MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void findJobsByWorkerId_success() throws Exception{

		when(swipeJobsService.retrieveJobs()).thenReturn(testUtils.retrieveJobs());
		when(swipeJobsService.retrieveWorkers()).thenReturn(testUtils.retrieveWorkers());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/MatchJobs/48")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void findJobsByWorkerId_NotExists_fail() throws Exception{

		when(swipeJobsService.retrieveJobs()).thenReturn(testUtils.retrieveJobs());
		when(swipeJobsService.retrieveWorkers()).thenReturn(testUtils.retrieveWorkers());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/MatchJobs/100")
				.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("Status", is("success")))
				.andExpect(jsonPath("Message", is("Worker not found")));
	}

	@Test
	public void findJobsByWorkerId_InactiveUser() throws Exception{

		when(swipeJobsService.retrieveJobs()).thenReturn(testUtils.retrieveJobs());
		when(swipeJobsService.retrieveWorkers()).thenReturn(testUtils.retrieveWorkers());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/MatchJobs/1")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("Status", is("success")))
				.andExpect(jsonPath("Message", is("User is inactive")));
	}

	@Test
	public void findJobsByWorkerId_NoMatchingJobs() throws Exception{

		when(swipeJobsService.retrieveJobs()).thenReturn(testUtils.retrieveJobs());
		when(swipeJobsService.retrieveWorkers()).thenReturn(testUtils.retrieveWorkers());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/MatchJobs/0")
				.contentType(APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("Status", is("success")))
				.andExpect(jsonPath("Message", is("No matching jobs found")));
	}

}
