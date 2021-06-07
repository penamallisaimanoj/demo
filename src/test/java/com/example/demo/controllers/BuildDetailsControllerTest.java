package com.example.demo.controllers;

import com.example.demo.entities.BuildDetails;
import com.example.demo.services.BuildDetailsService;
import com.example.demo.utils.CsvUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BuildDetailsController.class)
class BuildDetailsControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	BuildDetailsService buildDetailsService;

	private final String buildDetailsCsv = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";
	private final List<BuildDetails> buildDetailsList = CsvUtils.readBuildDetails(new StringReader(buildDetailsCsv));
	private final BuildDetails buildDetails = buildDetailsList.get(0);

	@Test
	void getCustomersForContract() throws Exception {
		when(buildDetailsService.getCustomersForContract(buildDetails.getContractId())).thenReturn(Collections.singletonList(buildDetails.getCustomerId()));
		mvc.perform(get("/buildDetails/customersForContract/" + buildDetails.getContractId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]", is(buildDetails.getCustomerId()), Long.class));
	}

	@Test
	void getCustomersCountForGeoZone() throws Exception {
		when(buildDetailsService.getCustomersCountForGeoZone("us_east")).thenReturn(1L);
		mvc.perform(get("/buildDetails/customersCountForGeoZone/" + buildDetails.getGeoZone()))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));
	}

	@Test
	void getCustomersForGeoZone() throws Exception {
		when(buildDetailsService.getCustomersForGeoZone(buildDetails.getGeoZone())).thenReturn(Collections.singletonList(buildDetails.getCustomerId()));
		mvc.perform(get("/buildDetails/customersForGeoZone/us_east"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]", is(buildDetails.getCustomerId()), Long.class));
	}

	@Test
	void averageBuildDurationForGeoZone() throws Exception {
		when(buildDetailsService.averageBuildDurationForGeoZone(buildDetails.getGeoZone())).thenReturn(buildDetails.getBuildDuration().doubleValue());
		mvc.perform(get("/buildDetails/averageBuildDurationForGeoZone/" + buildDetails.getGeoZone()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(buildDetails.getBuildDuration().doubleValue()), Double.class));
	}

	@Test
	void createBuildDetails() throws Exception {
		when(buildDetailsService.createBuildDetails(buildDetailsCsv)).thenReturn(buildDetailsList);
		mvc.perform(post("/buildDetails").content(buildDetailsCsv))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].customerId", is(buildDetails.getCustomerId()), Long.class));
	}

	@Test
	void getBuildDetails() throws Exception {
		when(buildDetailsService.getBuildDetails()).thenReturn(buildDetailsList);
		mvc.perform(get("/buildDetails"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].customerId", is(buildDetails.getCustomerId()), Long.class));
	}
}
