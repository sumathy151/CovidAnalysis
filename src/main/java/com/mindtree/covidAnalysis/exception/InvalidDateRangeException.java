package com.mindtree.covidAnalysis.exception;

public class InvalidDateRangeException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateRangeException(String errMessage)
	{
		super(errMessage);
	}

}
