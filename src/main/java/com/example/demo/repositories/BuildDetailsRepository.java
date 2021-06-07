package com.example.demo.repositories;

import com.example.demo.entities.BuildDetails;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import java.util.List;

/**
 * Repository for BuildDetails.
 */
@Repository
public interface BuildDetailsRepository extends JpaRepository<BuildDetails, Long> {
//	Methods for performing operations in database
//	@Query("SELECT DISTINCT customerId FROM BuildDetails WHERE contractId = :contractId")
//	List<Long> getCustomersForContract(@Param("contractId") Long contractId);
//
//	@Query("SELECT DISTINCT customerId FROM BuildDetails WHERE geoZone = :geoZone")
//	List<Long> getCustomersForGeoZone(@Param("geoZone") String geoZone);
//
//	@Query("SELECT avg(buildDuration) FROM BuildDetails WHERE geoZone = :geoZone")
//	Long averageBuildDurationForGeoZone(@Param("geoZone") String geoZone);
}
