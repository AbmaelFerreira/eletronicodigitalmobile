package com.eletronicodigitalmobile.service.exceptions;

public class DateIntegratyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DateIntegratyException(String msg) {
		super(msg);
	}

	public DateIntegratyException(String msg, Throwable causa) {
		super(msg, causa);
	}
}
