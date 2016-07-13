/**
 * AbstractDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

/**
 * The Class AbstractDataType.
 *
 * @param <U>
 *            the generic type
 */
abstract public class AbstractDataType<U extends AbstractDataType<?>> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(AbstractDataType.class);

	/** The fields map. */
	final private Map<String, String> fieldsMap;

	/**
	 * Helper method for use in get method.
	 *
	 * @param id
	 *            the id
	 * @return the id parameter
	 */
	public static ParsedNameValuePair getIdParameter(final long id) {
		return new ParsedNameValuePair(ControlField.ID.name().toLowerCase(), String.valueOf(id));
	}

	/**
	 * Gets the limit parameter.
	 *
	 * @param limit
	 *            the limit
	 * @return the limit parameter
	 */
	public static ParsedNameValuePair getLimitParameter(final int limit) {
		return new ParsedNameValuePair(ControlField.LIMIT.name().toLowerCase(),
		        String.valueOf(limit));
	}

	/**
	 * Gets the page parameter.
	 *
	 * @param page
	 *            the page
	 * @return the page parameter
	 */
	public static ParsedNameValuePair getPageParameter(final int page) {
		return new ParsedNameValuePair(ControlField.PAGE.name().toLowerCase(),
		        String.valueOf(page));
	}

	/**
	 * Instantiates a new abstract data type.
	 */
	protected AbstractDataType() {
		this.fieldsMap = new HashMap<>();
	}

	/**
	 * Instantiates a new abstract data type.
	 *
	 * @param map
	 *            the map
	 */
	public AbstractDataType(final Map<String, String> map) {
		this.fieldsMap = map;
	}

	/**
	 * Instantiates a new abstract data type.
	 *
	 * @param original
	 *            the original
	 */
	public AbstractDataType(final U original) {
		this(original.getFieldsMap());
	}

	/**
	 * Adds the field.
	 *
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 */
	protected void addField(final Enum<?> field, final String value) {
		this.getFieldsMap().put(field.name().toLowerCase(), value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if ((null == obj) || !obj.getClass().equals(this.getClass())) {
			AbstractDataType._logger.info("not equal " + this + " vs " + obj);
			return false;
		}
		@SuppressWarnings("unchecked")
		final U that = (U) obj;
		final Enum<?>[] fields = this.getFields();
		@SuppressWarnings("rawtypes")
		final ArrayList<Enum> ignore = this.getFieldsToIgnoreInEqualsMethod();
		for (int i = 0; i < fields.length; i++) {
			final String thisValue = this.getNonNullValue(fields[i]);
			final String thatValue = that.getNonNullValue(fields[i]);
			if (!ignore.contains(fields[i]) && !thisValue.equals(thatValue)) {
				AbstractDataType._logger.debug(fields[i].name() + " don't match. This:" + thisValue
				        + " vs That:" + thatValue);
				return false;
			}
		}
		AbstractDataType._logger.debug("Match");
		return true;
	}

	/**
	 * Gets the boolean value.
	 *
	 * @param field
	 *            the field
	 * @return the boolean value
	 */
	protected Boolean getBooleanValue(final Enum<?> field) {
		final String value = this.getValue(field);
		if (null == value) {
			return null;
		}
		return Boolean.valueOf(value);
	}

	/**
	 * Gets the double value.
	 *
	 * @param field
	 *            the field
	 * @return the double value
	 */
	protected Double getDoubleValue(final Enum<?> field) {
		final String value = this.getValue(field);
		if (null == value) {
			return null;
		}
		return Double.valueOf(value);
	}

	/**
	 * Gets the edits the fields.
	 *
	 * @return the edits the fields
	 */
	protected Enum<?>[] getEditFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	/**
	 * Gets the edits the parameters.
	 *
	 * @return the edits the parameters
	 */
	public ParsedNameValuePair[] getEditParameters() {
		final Enum<?>[] insertFields = this.getEditFields();
		return this.getParameters(insertFields);
	}

	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	abstract protected Enum<?>[] getFields();

	/**
	 * Gets the fields map.
	 *
	 * @return the fieldsMap
	 */
	protected Map<String, String> getFieldsMap() {
		return this.fieldsMap;
	}

	/**
	 * Gets the fields to ignore in equals method.
	 *
	 * @return the fields to ignore in equals method
	 */
	@SuppressWarnings("rawtypes")
	protected ArrayList<Enum> getFieldsToIgnoreInEqualsMethod() {
		return new ArrayList<>();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		final String value = this.getIdParameter().getValue();
		if (null == value) {
			return 0;
		}
		return Long.valueOf(value);
	}

	/**
	 * Gets the id parameter.
	 *
	 * @return the id parameter
	 */
	public ParsedNameValuePair getIdParameter() {
		return this.getNameValuePair(ControlField.ID);
	}

	/**
	 * Gets the insert fields.
	 *
	 * @return the insert fields
	 */
	protected Enum<?>[] getInsertFields() {
		throw new IllegalArgumentException("Not implemented");
	}

	/**
	 * Gets the insert parameters.
	 *
	 * @return the insert parameters
	 */
	public ParsedNameValuePair[] getInsertParameters() {
		final Enum<?>[] insertFields = this.getInsertFields();
		return this.getParameters(insertFields);
	}

	/**
	 * Gets the integer value.
	 *
	 * @param field
	 *            the field
	 * @return the integer value
	 */
	protected Integer getIntegerValue(final Enum<?> field) {
		final String value = this.getValue(field);
		if (null == value) {
			return null;
		}
		return Integer.valueOf(value);
	}

	/**
	 * Gets the long value.
	 *
	 * @param field
	 *            the field
	 * @return the long value
	 */
	protected Long getLongValue(final Enum<?> field) {
		final String value = this.getValue(field);
		if (null == value) {
			return 0L;
		}
		return Long.valueOf(value);
	}

	/**
	 * Gets the name value pair.
	 *
	 * @param field
	 *            the field
	 * @return the name value pair
	 */
	protected ParsedNameValuePair getNameValuePair(final Enum<?> field) {
		final String lowerKey = field.name().toLowerCase();
		final ParsedNameValuePair nameValuePair = new ParsedNameValuePair(lowerKey,
		        this.getValue(lowerKey));
		AbstractDataType._logger.debug("getNameValuePair : " + lowerKey + " -> " + nameValuePair);
		return nameValuePair;
	}

	/**
	 * Gets the non null value.
	 *
	 * @param fields
	 *            the fields
	 * @return the non null value
	 */
	protected String getNonNullValue(final Enum<?> fields) {
		final String value = this.getValue(fields);
		if (null == value) {
			return "";
		}
		return value.trim();
	}

	/**
	 * Gets the parameters.
	 *
	 * @param fields
	 *            the fields
	 * @return the parameters
	 */
	private ParsedNameValuePair[] getParameters(final Enum<?>[] fields) {
		final ParsedNameValuePair[] parameters = new ParsedNameValuePair[fields.length];
		for (int i = 0; i < fields.length; i++) {
			parameters[i] = this.getNameValuePair(fields[i]);
		}
		AbstractDataType._logger.debug("getParameters: " + Arrays.asList(parameters));
		return parameters;
	}

	/**
	 * Gets the value.
	 *
	 * @param field
	 *            the field
	 * @return the value
	 */
	protected String getValue(final Enum<?> field) {
		final String key = field.name().toLowerCase();
		return this.getValue(key);
	}

	/**
	 * Gets the value.
	 *
	 * @param key
	 *            the key
	 * @return the value
	 */
	protected String getValue(final String key) {
		final String value = this.getFieldsMap().get(key);
		AbstractDataType._logger.debug("getValue : " + key + "=" + value);
		return value;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public String[] getValues() throws ClearcheckbookException {
		final Enum<?>[] fields = this.getFields();
		final String[] values = new String[fields.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = this.getNonNullValue(fields[i]);
		}
		return values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.fieldsMap == null) ? 0 : this.fieldsMap.hashCode());
		return result;
	}

	/**
	 * Sets the int value from boolean string.
	 *
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 */
	protected void setIntValueFromBooleanString(final Enum<?> field, String value) {
		AbstractDataType._logger
		        .debug("setIntValueFromBooleanString: " + field.name() + "=" + value);
		switch (value) {
			case "false":
				value = "0";
				break;
			case "true":
				value = "1";
				break;
		}
		AbstractDataType._logger
		        .debug("setIntValueFromBooleanString: " + field.name() + "=" + value);
		this.setValue(field, value);
	}

	/**
	 * Sets the value.
	 *
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 */
	protected void setValue(final Enum<?> field, Object value) {
		if (value instanceof Enum) {
			value = ((Enum) value).ordinal();
		}

		if (value instanceof Double) {
			value = String.format("%.2f", (Double) value);
		}
		if ((null == value) || value.equals("")) {
			this.getFieldsMap().put(field.name().toLowerCase(), null);
		}
		else {
			this.getFieldsMap().put(field.name().toLowerCase(), value.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractDataType [fieldsMap=" + this.getFieldsMap() + "]";
	}

	/**
	 * The Enum ControlField.
	 */
	public enum ControlField {

		/** The id. */
		ID, /** The limit. */
		LIMIT, /** The page. */
		PAGE
	}

}
