package com.leonarduk.clearcheckbook;

public class ClearcheckbookException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6833991090411157540L;

	public ClearcheckbookException(String text) {
		super(text);
	}

	public ClearcheckbookException(String text, Throwable exception) {
		super(text, exception);
	}
}
