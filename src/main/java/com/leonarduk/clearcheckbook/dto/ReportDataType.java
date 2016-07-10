/**
 * ReportDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

/**
 * The Class ReportDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class ReportDataType extends AbstractDataType<ReminderDataType> {

	/**
	 * Instantiates a new report data type.
	 *
	 * @param map
	 *            the map
	 */
	public ReportDataType(final Map<String, String> map) {
		super(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getFields()
	 */
	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return this.getValue(Fields.LABEL);
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.getValue(Fields.URL);
	}

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            the new label
	 */
	public void setLabel(final String label) {
		this.setValue(Fields.LABEL, label);
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.setValue(Fields.URL, url);
	}

	/**
	 * The Enum Fields.
	 */
	public enum Fields {

		/** The type. */
		TYPE, /** The months. */
		MONTHS, /** The bgcolor. */
		BGCOLOR, /** The height. */
		HEIGHT, /** The width. */
		WIDTH, /** The label. */
		LABEL, /** The url. */
		URL
	}

	/**
	 * The Enum Type.
	 */
	public enum Type {

		/** The pie. */
		PIE, /** The line. */
		LINE
	}

}
