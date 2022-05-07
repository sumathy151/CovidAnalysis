package com.mindtree.covidAnalysis.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Controller
@ControllerAdvice
public class CovidAnalysisExceptionHandler {
	
	@ExceptionHandler(value=InvalidStateCodeException.class)
	public String handleInValidaStateException(Model model)
	{
		model.addAttribute("errMessage", "Invalid State code, Please check your input");
		return "covidPage";
	
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public String handleNullPointerException(Model model)
	{
		model.addAttribute("errMessage", "Please check your input");
		return "covidPage";
	
	}
	
	@ExceptionHandler(value=InvalidDateException.class)
	public String handleInvalidDateException(Model model)
	{
		model.addAttribute("errMessage", "Invalid Date, Please check your input");		
		return "covidPage";
	
	}
	
	
	@ExceptionHandler(value=InvalidDateRangeException.class)
	public String handleInvalidDateRangeException(Model model)
	{
		model.addAttribute("errMessage", "Invalid Date Range, Please check your input");
		return "covidPage";
	
	}
	@ExceptionHandler(value=NoDataFoundException.class)
	public String handleNoDataFoundException(Model model) 
	{
		model.addAttribute("errMessage", "No Data Present");
		return "covidPage";
	
	}

}
