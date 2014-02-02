package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 28 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ReportDataType extends AbstractDataType<ReminderDataType> {

	public enum Fields {
		TYPE, MONTHS, BGCOLOR, HEIGHT, WIDTH, LABEL, URL
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	public enum Type {
		PIE, LINE
	}

	public ReportDataType(Map<String, String> map) {
		super(map);
	}

	public String getLabel() {
		return getValue(Fields.LABEL);
	}

	public String getUrl() {
		return getValue(Fields.URL);
	}

	public void setLabel(String label) {
		setValue(Fields.LABEL, label);
	}

	public void setUrl(String url) {
		setValue(Fields.URL, url);
	}

}
