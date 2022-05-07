package com.mindtree.covidAnalysis.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mindtree.covidAnalysis.entity.CovidData;
import com.mindtree.covidAnalysis.exception.InvalidStateCodeException;
import com.mindtree.covidAnalysis.exception.NoDataFoundException;
import com.mindtree.covidAnalysis.repository.CovidDataRepository;
import com.mindtree.covidAnalysis.service.CovidDataService;

@Service
public class CovidDataServiceImpl implements CovidDataService {

	@Autowired
	private CovidDataRepository covidDataRepo;

	Predicate<List<CovidData>> predicateListCheck = list -> (CollectionUtils.isEmpty(list));

	public List<CovidData> getAllStates() {
		List<CovidData> stateList = covidDataRepo.findAll();

		List<CovidData> distinctStateList = stateList.stream().filter(distinctByKey(CovidData::getState))
				.collect(Collectors.toList());

		return distinctStateList;
	}

	public List<CovidData> getDistricts(String state) throws InvalidStateCodeException {

		List<CovidData> districtList = covidDataRepo.findByState(state);

		if (predicateListCheck.negate().test(districtList)) {

			List<CovidData> sortedDistrictList = districtList.stream().filter(distinctByKey(CovidData::getDistrict))
					.sorted(Comparator.comparing(CovidData::getDistrict)).collect(Collectors.toList());
			return sortedDistrictList;

		} else {

			throw new InvalidStateCodeException("Invalid State Code, Please check your input");

		}
	}

	public List<CovidData> displayDataWithinDateRange(LocalDate startDate, LocalDate endDate)
			throws NoDataFoundException {
		List<CovidData> list = covidDataRepo.findDataByDateRange(startDate, endDate);

		BiFunction<LocalDate, LocalDate, List<CovidData>> biFunction = (startDt, endDt) -> {
			return list;
		};

		List<CovidData> dataList = biFunction.apply(startDate, endDate);

		if (predicateListCheck.test(dataList)) {
			throw new NoDataFoundException("No Data Present");
		}

		return dataList;

	}

	public List<CovidData> displayConfirmedCases(LocalDate startDate, LocalDate endDate, String firstState,
			String secondState) throws NoDataFoundException {

		List<CovidData> confirmedCasesList = covidDataRepo.findByDateAndState(startDate, endDate, firstState,
				secondState);

		if (predicateListCheck.test(confirmedCasesList)) {
			throw new NoDataFoundException("No Data Present");
		}

		return confirmedCasesList;
	}

	public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return predicateObject -> seen.putIfAbsent(keyExtractor.apply(predicateObject), Boolean.TRUE) == null;
	}

}
