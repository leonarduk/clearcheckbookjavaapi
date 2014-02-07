package com.leonarduk.clearcheckbook.file;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.utils.DateUtils;

public class AMEXFilePreprocessor extends TransactionFilePreprocessor {

	private static final Logger _logger = Logger
			.getLogger(AMEXFilePreprocessor.class);

	/**
	 * 
	 */
	public AMEXFilePreprocessor() {
		super(0);
	}

	@Override
	protected String getCheckNum(Map<String, String> fieldsMap) {
		return "";
	}

	@Override
	protected String getDate(Map<String, String> fieldsMap)
			throws ClearcheckbookException {
		String dateString = fieldsMap.get("date");
		_logger.debug("getDate:" + dateString + ":" + fieldsMap);
		Date date;
		try {
			date = DateUtils.getDate(dateString, "dd MMM yyyy");
		} catch (ParseException e) {
			throw new ClearcheckbookException("Failed to parse date: "
					+ dateString, e);
		}
		return DateUtils.getFormattedDate("yyyy-MM-dd", date);
	}

	@Override
	protected String getMemo(Map<String, String> fieldsMap) {
		return "Balance: " + getDouble(fieldsMap.get("balance"));
	}
	@Override
	protected String getPayee(Map<String, String> fieldsMap) {
		return fieldsMap.get("description");
	}

	@Override
	protected String getDesription(Map<String, String> fieldsMap) {
		return fieldsMap.get("transaction type") + " "
				+ fieldsMap.get("description");
	}

	@Override
	protected String getAmount(Map<String, String> fieldsMap) {
		String credit = fieldsMap.get("paid in");
		String debit = fieldsMap.get("paid out");
		double amount = 0;
		if (credit.equals("")) {
			amount = -1 * getDouble(debit);
		} else {
			amount = getDouble(credit);
		}
		return String.valueOf(amount);
	}
}
