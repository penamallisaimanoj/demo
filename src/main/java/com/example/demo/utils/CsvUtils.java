package com.example.demo.utils;

import com.example.demo.entities.BuildDetails;
import com.opencsv.CSVReader;
import lombok.SneakyThrows;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class CsvUtils {

	@SneakyThrows
	public static List<BuildDetails> readBuildDetails(Reader reader){
		CSVReader csvReader = new CSVReader(reader);
		List<String[]> list;
		list = csvReader.readAll();
		reader.close();
		csvReader.close();
		return list.stream()
				.map(CsvUtils::mapToBuildDetails)
				.collect(Collectors.toList());
	}

	private static BuildDetails mapToBuildDetails(String[] data){
		return BuildDetails.builder()
				.customerId(Long.parseLong(data[0]))
				.contractId(Long.parseLong(data[1]))
				.geoZone(data[2])
				.teamCode(data[3])
				.projectCode(data[4])
				.buildDuration(Long.parseLong(data[5].substring(0, data[5].length() - 1)))
				.build();
	}
}
