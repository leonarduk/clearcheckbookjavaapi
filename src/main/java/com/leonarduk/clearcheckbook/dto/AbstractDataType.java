package com.leonarduk.clearcheckbook.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

abstract public class AbstractDataType {

	public enum Id {
		ID
	}

	private static final Logger _logger = Logger
			.getLogger(AbstractDataType.class);;

	/**
	 * Helper method for use in get method.
	 * 
	 * @param id
	 * @return
	 */
	public static ParsedNameValuePair getIdParameter(long id) {
		return new ParsedNameValuePair(Id.ID.name().toLowerCase(),
				String.valueOf(id));
	}

	final private Map<String, String> fieldsMap;

	protected AbstractDataType() {
		this.fieldsMap = new HashMap<String, String>();
	}

	protected void setIntValueFromBooleanString(Enum<?> field, String value) {
		_logger.debug("setIntValueFromBooleanString: " + field.name() + "="
				+ value);
		switch (value) {
		case "false":
			value = "0";
			break;
		case "true":
			value = "1";
			break;
		}
		_logger.debug("setIntValueFromBooleanString: " + field.name() + "="
				+ value);
		setValue(field, value);
	}

	public AbstractDataType(Map<String, String> map) {
		this.fieldsMap = map;
	}

	protected void addField(Enum<?> field, String value) {
		this.fieldsMap.put(field.name().toLowerCase(), value);
	}

	protected Enum<?>[] getEditFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	protected Enum<?>[] getInsertFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	public ParsedNameValuePair[] getEditParameters() {
		Enum<?>[] insertFields = getEditFields();
		return getParameters(insertFields);
	}

	public String getId() {
		return getIdParameter().getValue();
	}

	public ParsedNameValuePair getIdParameter() {
		return getNameValuePair(Id.ID);
	}

	public ParsedNameValuePair[] getInsertParameters() {
		Enum<?>[] insertFields = getInsertFields();
		return getParameters(insertFields);
	}

	/**
	 * 
	 * @param field
	 * @return
	 */
	protected ParsedNameValuePair getNameValuePair(Enum<?> field) {
		String lowerKey = field.name().toLowerCase();
		ParsedNameValuePair nameValuePair = new ParsedNameValuePair(lowerKey,
				getValue(lowerKey));
		_logger.debug("getNameValuePair : " + lowerKey + " -> " + nameValuePair);
		return nameValuePair;
	}

	private ParsedNameValuePair[] getParameters(Enum<?>[] fields) {
		ParsedNameValuePair[] parameters = new ParsedNameValuePair[fields.length];
		for (int i = 0; i < fields.length; i++) {
			parameters[i] = getNameValuePair(fields[i]);
		}
		_logger.debug("getParameters: " + Arrays.asList(parameters));
		return parameters;
	}

	protected String getValue(Enum<?> field) {
		String key = field.name().toLowerCase();
		return getValue(key);
	}

	protected Double getDoubleValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Double.valueOf(value);
	}

	protected Boolean getBooleanValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Boolean.valueOf(value);
	}

	protected Integer getIntegerValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Integer.valueOf(value);
	}

	protected Long getLongValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Long.valueOf(value);
	}

	protected String getValue(String key) {
		String value = this.fieldsMap.get(key);
		_logger.debug("getValue : " + key + "=" + value);
		return value;
	}

	protected void setValue(Enum<?> field, Object value) {
		if (null == value || value.equals("")) {
			this.fieldsMap.put(field.name().toLowerCase(), null);
		} else {
			this.fieldsMap.put(field.name().toLowerCase(), value.toString());
		}
	}

	@Override
	public String toString() {
		return "AbstractDataType [fieldsMap=" + fieldsMap + "]";
	}

}
