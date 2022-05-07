package com.mindtree.covidAnalysis.exception;

public class InvalidDateException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateException(String errMessage)
	{
		super(errMessage);
	}

}
