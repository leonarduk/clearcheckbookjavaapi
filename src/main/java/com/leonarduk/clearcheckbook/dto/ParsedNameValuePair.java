/**
 * ParsedNameValuePair
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * This is a special work-around to convert boolean values to 1 or 0 rather than true or false.
 *
 * @author Stephen Leonard
 * @since 30 Jan 2014
 *
 *
 */
public class ParsedNameValuePair extends NameValuePair {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 895202581659046934L;

	/** The is boolean. */
	private final boolean isBoolean;

	/**
	 * Instantiates a new parsed name value pair.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public ParsedNameValuePair(final String name, final String value) {
		super(name, value);
		this.isBoolean = ((null != value)
		        && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.util.NameValuePair#getValue()
	 */
	@Override
	public String getValue() {
		final String value = super.getValue();
		if (this.isBoolean) {
			if (Boolean.valueOf(value)) {
				return "1";
			}
			return "0";
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.util.NameValuePair#toString()
	 */
	@Override
	public String toString() {
		if (this.isBoolean) {
			return "Boolean value: " + super.toString();
		}
		return super.toString();
	}

}
