package com.leonarduk.clearcheckbook.dto;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * This is a special work-around to convert boolean values to 1 or 0 rather than
 * true or false.
 * 
 * @author Stephen Leonard
 * @since 30 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ParsedNameValuePair extends NameValuePair {

	private boolean isBoolean;

	
	/**
	 * This is a special work-around to convert boolean values to 1 or 0 rather
	 * than true or false.
	 * 
	 * @param name
	 * @param value
	 */
	public ParsedNameValuePair(String name, String value) {
		super(name, value);
		this.isBoolean = (null != value && (value.equalsIgnoreCase("true") || value
				.equalsIgnoreCase("false")));
	}

	@Override
	public String toString() {
		if (this.isBoolean) {
			return "Boolean value: " + super.toString();
		}
		return super.toString();
	}

	@Override
	public String getValue() {
		String value = super.getValue();
		if (this.isBoolean) {
			if (Boolean.valueOf(value)) {
				return "1";
			}
			return "0";
		}
		return value;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 895202581659046934L;

}
