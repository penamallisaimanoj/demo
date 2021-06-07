package com.example.demo.services;

import com.example.demo.entities.BuildDetails;
import com.example.demo.repositories.BuildDetailsRepository;
import com.example.demo.utils.CsvUtils;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BuildDetails Service Class which interacts with repository and
 * offers methods based on Business logic
 */
@Service
public class BuildDetailsService {

	final BuildDetailsRepository buildDetailsRepository;

	public BuildDetailsService(BuildDetailsRepository buildDetailsRepository) {
		this.buildDetailsRepository = buildDetailsRepository;
	}

	/**
	 * Return a distinct list of customerIds for given contractId
	 * of all existing BuildDetails
	 * @param  contractId - Long The contractId with which the existing
	 *         be filtered
	 * @return List<Long> - List of distinct customerIds
	 * */
	public List<Long> getCustomersForContract(Long contractId){
		return buildDetailsRepository.findAll().stream()
				.filter(buildDetails -> buildDetails.getContractId().equals(contractId))
				.map(BuildDetails::getCustomerId)
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Return a distinct count of customerIds for given geoZone
	 * of all existing BuildDetails
	 * @param  geoZone - String The geoZone with which the existing
	 *         be filtered
	 * @return Long - Number of distinct customerIds
	 * */
	public Long getCustomersCountForGeoZone(String geoZone){
		return buildDetailsRepository.findAll().stream()
				.filter(buildDetails -> buildDetails.getGeoZone().equals(geoZone))
				.distinct()
				.count();
	}

	/**
	 * Return a distinct list of customerIds for given geoZone
	 * of all existing BuildDetails
	 * @param  geoZone - String The geoZone with which the existing
	 *         be filtered
	 * @return List<Long> - List of distinct customerIds
	 * */
	public List<Long> getCustomersForGeoZone(String geoZone){
		return buildDetailsRepository.findAll().stream()
				.filter(buildDetails -> buildDetails.getGeoZone().equals(geoZone))
				.map(BuildDetails::getCustomerId)
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Return the average of the build times for given geoZone of
	 * all existing BuildDetails
	 * @param  geoZone - String The geoZone with which the existing
	 *         be filtered
	 * @return Double - Average of the build times
	 * */
	public Double averageBuildDurationForGeoZone(String geoZone){
		return buildDetailsRepository.findAll().stream()
				.filter(buildDetails -> buildDetails.getGeoZone().equals(geoZone))
				.mapToLong(BuildDetails::getBuildDuration)
				.average()
				.orElse(0.0); //Can be changed based on design
	}

	/**
	 * Parses the csvData and creates the BuildDetails record for each
	 * line in the csvData
	 * @param  csvData - CSV formatted string of BuildDetails data
	 * @return List<BuildDetails> - List of the BuildDetails records
	 *         created in the repository
	 * */
	public List<BuildDetails> createBuildDetails(String csvData) {
		return buildDetailsRepository.saveAll(CsvUtils.readBuildDetails(new StringReader(csvData)));
	}

	/**
	 * Returns all the BuildDetails records present in the repository
	 * @return List<BuildDetails> - List of the BuildDetails records
	 *         present in the repository
	 * */
	public List<BuildDetails> getBuildDetails() {
		return buildDetailsRepository.findAll();
	}
}
