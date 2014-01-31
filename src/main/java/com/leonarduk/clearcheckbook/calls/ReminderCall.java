package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;

public class ReminderCall extends AbstractCall<ReminderDataType> {

	private static final Logger _logger = Logger.getLogger(ReminderCall.class);

	public static final String TYPE = "reminder";

	public ReminderCall(String username, String password) {
		super(TYPE, username, password);
	}

	@Override
	public List<ReminderDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	@Override
	public ReminderDataType get(ParsedNameValuePair id)
			throws ClearcheckbookException {
		return super.get(id);
	}

	@Override
	public String insert(ReminderDataType input) throws ClearcheckbookException {
		return super.insert(input);
	}

	@Override
	public boolean edit(ReminderDataType input) throws ClearcheckbookException {
		return super.edit(input);
	}

	@Override
	public boolean delete(ParsedNameValuePair input) throws ClearcheckbookException {
		return super.delete(input);
	}

	public void deleteAll() {
	}

}
