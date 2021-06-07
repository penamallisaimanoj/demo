package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildDetails {
	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private Long customerId;
	@Column
	private Long contractId;
	@Column
	private String geoZone;
	@Column
	private String teamCode;
	@Column
	private String projectCode;
	@Column
	private Long buildDuration;
}
