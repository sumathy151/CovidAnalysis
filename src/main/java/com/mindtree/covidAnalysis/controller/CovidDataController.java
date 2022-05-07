package com.mindtree.covidAnalysis.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindtree.covidAnalysis.entity.CovidData;
import com.mindtree.covidAnalysis.exception.InvalidDateException;
import com.mindtree.covidAnalysis.exception.InvalidDateRangeException;
import com.mindtree.covidAnalysis.exception.InvalidStateCodeException;
import com.mindtree.covidAnalysis.exception.NoDataFoundException;
import com.mindtree.covidAnalysis.service.CovidDataService;

@Controller
@RequestMapping(value = "/covidAnalysis")
public class CovidDataController {

	@Autowired
	private CovidDataService covidDataService;

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String displayHomePage(Model model) {
		model.addAttribute("covidData", new CovidData());

		return "covidPage";

	}

	@RequestMapping(value = "/states", method = RequestMethod.GET)

	public String getStates(Model model) {

		List<CovidData> stateList = covidDataService.getAllStates();

		model.addAttribute("stateList", stateList);

		return "covidPage";

	}

	@RequestMapping(value = "/state/districts", method = RequestMethod.GET)
	public String getDistrictsByState(@RequestParam("stateCode") String state, Model model)
			throws InvalidStateCodeException {

		List<CovidData> districtList = covidDataService.getDistricts(state);

		model.addAttribute("districtList", districtList);

		return "covidPage";

	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String displayDataWithinDateRange(@RequestParam("startDate") String startDateString,
			@RequestParam("endDate") String endDateString, Model model)
			throws InvalidDateException, InvalidDateRangeException, NoDataFoundException {

		dateValidation(startDateString, endDateString);

		LocalDate startDate = LocalDate.parse(startDateString);
		LocalDate endDate = LocalDate.parse(endDateString);

		dateRangeValidation(startDate, endDate);

		List<CovidData> dataList = covidDataService.displayDataWithinDateRange(startDate, startDate);

		List<CovidData> sortedDataList = dataList.stream().sorted(Comparator.comparing(CovidData::getDate))
				.collect(Collectors.toList());

		Map<LocalDate, Map<String, Integer>> multipleFieldsMap = sortedDataList.stream()
				.collect(Collectors.groupingBy(CovidData::getDate,
						Collectors.groupingBy(CovidData::getState, Collectors.summingInt(CovidData::getConfirmedInt))));

		model.addAttribute("multipleFieldsMap", multipleFieldsMap);

		return "covidPage";

	}

	@RequestMapping(value = "/report/confirmedCases", method = RequestMethod.GET)
	public String displayConfirmedCases(ModelMap modelMap, @RequestParam("start") String startDateString,
			@RequestParam("end") String endDateString, @RequestParam("firstState") String firstState,
			@RequestParam("secondState") String secondState)
			throws InvalidDateException, InvalidDateRangeException, NoDataFoundException {

		dateValidation(startDateString, endDateString);

		LocalDate startDate = LocalDate.parse(startDateString);
		LocalDate endDate = LocalDate.parse(endDateString);

		dateRangeValidation(startDate, endDate);

		List<CovidData> confirmedCasesList = covidDataService.displayConfirmedCases(startDate, endDate, firstState,
				secondState);

		List<CovidData> firstStateList = confirmedCasesList.stream()
				.filter(covidData -> covidData.getState().equalsIgnoreCase(firstState)).collect(Collectors.toList());
		List<CovidData> secondStateList = confirmedCasesList.stream()
				.filter(covidData -> covidData.getState().equalsIgnoreCase(secondState)).collect(Collectors.toList());

		Map<LocalDate, Map<String, Integer>> firstStateMap = firstStateList.stream()
				.collect(Collectors.groupingBy(CovidData::getDate,
						Collectors.groupingBy(CovidData::getState, Collectors.summingInt(CovidData::getConfirmedInt))));

		Map<LocalDate, Map<String, Integer>> secondStateMap = secondStateList.stream()
				.collect(Collectors.groupingBy(CovidData::getDate,
						Collectors.groupingBy(CovidData::getState, Collectors.summingInt(CovidData::getConfirmedInt))));

		modelMap.addAttribute("firstStateMap", firstStateMap);
		modelMap.addAttribute("secondStateMap", secondStateMap);

		return "covidPage";

	}

	@RequestMapping("/exit")
	public String exitApplication(Model model) {

		model.addAttribute("exitMsg", "Exited the application successfully");

		return "exit";

	}

	/**
	 * @param startDate
	 * @param endDate
	 * @throws InvalidDateRangeException
	 */
	private void dateRangeValidation(LocalDate startDate, LocalDate endDate) throws InvalidDateRangeException {
		if (isValidDateRange().negate().test(startDate, endDate)) {

			throw new InvalidDateRangeException("Invalid Date Range, Please check your input");

		}
	}

	/**
	 * @param startDateString
	 * @param endDateString
	 * @throws InvalidDateException
	 */
	private void dateValidation(String startDateString, String endDateString) throws InvalidDateException {
		if (isValidDate().negate().test(startDateString)) {
			throw new InvalidDateException("Invalid Start date, please check your input");

		}

		if (isValidDate().negate().test(endDateString)) {
			throw new InvalidDateException("Invalid End date, please check your input");

		}
	}

	public Predicate<String> isValidDate() {
		Predicate<String> datePredicate = (dateString) -> {
			try {
				LocalDate.parse(dateString, this.dateFormatter);
			} catch (DateTimeParseException e) {
				return false;
			}
			return true;
		};
		return datePredicate;
	}

	private BiPredicate<LocalDate, LocalDate> isValidDateRange() {
		BiPredicate<LocalDate, LocalDate> datePredicate = (startDate, endDate) -> !(startDate.isAfter(endDate));
		return datePredicate;
	}

}
