package com.leonarduk.clearcheckbook.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

abstract public class AbstractDataType<U extends AbstractDataType<?>> {

	public enum ControlField {
		ID, LIMIT, PAGE
	}

	private static final Logger _logger = Logger
			.getLogger(AbstractDataType.class);

	/**
	 * @return the fieldsMap
	 */
	protected Map<String, String> getFieldsMap() {
		return fieldsMap;
	}

	/**
	 * Helper method for use in get method.
	 * 
	 * @param id
	 * @return
	 */
	public static ParsedNameValuePair getIdParameter(long id) {
		return new ParsedNameValuePair(ControlField.ID.name().toLowerCase(),
				String.valueOf(id));
	};

	public static ParsedNameValuePair getLimitParameter(int limit) {
		return new ParsedNameValuePair(ControlField.LIMIT.name().toLowerCase(),
				String.valueOf(limit));
	}

	public static ParsedNameValuePair getPageParameter(int page) {
		return new ParsedNameValuePair(ControlField.PAGE.name().toLowerCase(),
				String.valueOf(page));
	}

	final private Map<String, String> fieldsMap;

	protected AbstractDataType() {
		this.fieldsMap = new HashMap<String, String>();
	}

	public AbstractDataType(Map<String, String> map) {
		this.fieldsMap = map;
	}

	public AbstractDataType(U original) {
		this(original.getFieldsMap());
	}

	protected void addField(Enum<?> field, String value) {
		this.getFieldsMap().put(field.name().toLowerCase(), value);
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !obj.getClass().equals(this.getClass())) {
			_logger.info("not equal " + this + " vs " + obj);
			return false;
		}
		@SuppressWarnings("unchecked")
		U that = (U) obj;
		Enum<?>[] fields = getFields();
		for (int i = 0; i < fields.length; i++) {
			if (!getNonNullValue(fields[i]).equals(
					that.getNonNullValue(fields[i]))) {
				_logger.info(fields[i].name() + " don't match. This:"
						+ getNonNullValue(fields[i]) + " vs That:"
						+ that.getNonNullValue(fields[i]));
				return false;
			}
		}
		return true;
	}

	protected Boolean getBooleanValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Boolean.valueOf(value);
	}

	protected Double getDoubleValue(Enum<?> field) {
		String value = getValue(field);
		if (null == value)
			return null;
		return Double.valueOf(value);
	}

	protected Enum<?>[] getEditFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	public ParsedNameValuePair[] getEditParameters() {
		Enum<?>[] insertFields = getEditFields();
		return getParameters(insertFields);
	}

	abstract protected Enum<?>[] getFields();

	public long getId() {
		String value = getIdParameter().getValue();
		if (null == value) {
			return 0;
		}
		return Long.valueOf(value);
	}

	public ParsedNameValuePair getIdParameter() {
		return getNameValuePair(ControlField.ID);
	}

	protected Enum<?>[] getInsertFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	public ParsedNameValuePair[] getInsertParameters() {
		Enum<?>[] insertFields = getInsertFields();
		return getParameters(insertFields);
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
			return 0L;
		return Long.valueOf(value);
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

	protected String getNonNullValue(Enum<?> fields) {
		String value = getValue(fields);
		if (null == value) {
			return "";
		}
		return value;
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

	protected String getValue(String key) {
		String value = this.getFieldsMap().get(key);
		_logger.debug("getValue : " + key + "=" + value);
		return value;
	}

	public String[] getValues() throws ClearcheckbookException {
		Enum<?>[] fields = getFields();
		String[] values = new String[fields.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = getNonNullValue(fields[i]);
		}
		return values;
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

	protected void setValue(Enum<?> field, Object value) {
		if (null == value || value.equals("")) {
			this.getFieldsMap().put(field.name().toLowerCase(), null);
		} else {
			this.getFieldsMap().put(field.name().toLowerCase(), value.toString());
		}
	}

	@Override
	public String toString() {
		return "AbstractDataType [fieldsMap=" + getFieldsMap() + "]";
	}

}
