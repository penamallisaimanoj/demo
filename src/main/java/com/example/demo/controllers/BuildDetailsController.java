package com.example.demo.controllers;

import com.example.demo.entities.BuildDetails;
import com.example.demo.services.BuildDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buildDetails")
public class BuildDetailsController {
	final BuildDetailsService buildDetailsService;

	public BuildDetailsController(BuildDetailsService buildDetailsService) {
		this.buildDetailsService = buildDetailsService;
	}

	@GetMapping("/customersForContract/{contractId}")
	public List<Long> getCustomersForContract(@PathVariable("contractId") Long contractId){
		return buildDetailsService.getCustomersForContract(contractId);
	}

	@GetMapping("/customersCountForGeoZone/{geoZone}")
	public Long getCustomersCountForGeoZone(@PathVariable("geoZone") String geoZone){
		return buildDetailsService.getCustomersCountForGeoZone(geoZone);
	}

	@GetMapping("/customersForGeoZone/{geoZone}")
	public List<Long> getCustomersForGeoZone(@PathVariable("geoZone") String geoZone){
		return buildDetailsService.getCustomersForGeoZone(geoZone);
	}

	@GetMapping("/averageBuildDurationForGeoZone/{geoZone}")
	public Double averageBuildDurationForGeoZone(@PathVariable("geoZone") String geoZone){
		return buildDetailsService.averageBuildDurationForGeoZone(geoZone);
	}

	@PostMapping
	public List<BuildDetails> createBuildDetails(@RequestBody String csvData){
		return buildDetailsService.createBuildDetails(csvData);
	}

	@GetMapping
	public List<BuildDetails> getBuildDetails(){
		return buildDetailsService.getBuildDetails();
	}
}
