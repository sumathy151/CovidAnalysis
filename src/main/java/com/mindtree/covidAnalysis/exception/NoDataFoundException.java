package com.mindtree.covidAnalysis.exception;

public class NoDataFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String errMessage)
	{
		super(errMessage);
	}

}
