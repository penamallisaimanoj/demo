package com.example.demo.services;

import com.example.demo.entities.BuildDetails;
import com.example.demo.repositories.BuildDetailsRepository;
import com.example.demo.utils.CsvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.StringReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BuildDetailsServiceTest {

	@MockBean
	BuildDetailsRepository buildDetailsRepository;

	BuildDetailsService buildDetailsService;

	private final String buildDetailsCsv = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
			"1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
			"3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
			"1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
			"3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

	private final List<BuildDetails> buildDetailsList = CsvUtils.readBuildDetails(new StringReader(buildDetailsCsv));

	@BeforeEach
	void setUp() {
		mockBuildDetailsRepository();
		buildDetailsService =new BuildDetailsService(buildDetailsRepository);
	}

	private void mockBuildDetailsRepository() {
		when(buildDetailsRepository.findAll()).thenReturn(buildDetailsList);
	}

	@Test
	void getCustomersForContractTest() {
		List<Long> customersForContract = buildDetailsService.getCustomersForContract(2345L);
		assertThat(customersForContract, hasSize(3));
		assertThat(customersForContract, contains(2343225L, 1223456L, 1233456L));
	}

	@Test
	void getCustomersCountForGeoZoneTest() {
		Long customersCountForGeoZone = buildDetailsService.getCustomersCountForGeoZone("eu_west");
		assertThat(customersCountForGeoZone, is(2L));
	}

	@Test
	void getCustomersForGeoZoneTest() {
		List<Long> customersForContract = buildDetailsService.getCustomersForGeoZone("eu_west");
		assertThat(customersForContract, hasSize(2));
		assertThat(customersForContract, contains(3244332L, 3244132L));
	}

	@Test
	void averageBuildDurationForGeoZoneTest() {
		Double averageBuildDurationForGeoZone = buildDetailsService.averageBuildDurationForGeoZone("eu_west");
		assertThat(averageBuildDurationForGeoZone, is(4222D));
	}
}
