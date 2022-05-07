package com.mindtree.covidAnalysis.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mindtree.covidAnalysis.entity.CovidData;
import com.mindtree.covidAnalysis.exception.InvalidStateCodeException;
import com.mindtree.covidAnalysis.exception.NoDataFoundException;

@Service
public interface CovidDataService {
		
	public List<CovidData> getAllStates();
	public List<CovidData> getDistricts(String state) throws InvalidStateCodeException;
	public List<CovidData> displayDataWithinDateRange(LocalDate startDate, LocalDate endDate) throws NoDataFoundException;
	public List<CovidData> displayConfirmedCases(LocalDate startDate, LocalDate endDate, String firstState,String secondState) throws NoDataFoundException;

}

	
	