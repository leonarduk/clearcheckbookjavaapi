package com.leonarduk.clearcheckbook;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.DateUtils;

public class NationwideFilePreprocessor extends FilePreprocessor {

	private static final Logger _logger = Logger
			.getLogger(NationwideFilePreprocessor.class);

	/**
	 * "Account Name:","Smart Junior ISA ****07843" <BR>
	 * "Account Balance:","£3720.00" <BR>
	 * "Available Balance: ","£3720.00" <BR>
	 * <BR>
	 * "Date","Transaction type","Description","Paid out","Paid in","Balance" <BR>
	 * "21 Nov 2013","Transfer from","0275/636 848 557","","£120.00","£120.00" <BR>
	 * "25 Nov 2013","Transfer from","07-10-40 54817554 Credit 24 November 2013"
	 * ,"","£3300.00","£3420.00" <BR>
	 * "25 Nov 2013","Transfer from","0275/636 848 557 Credit 24 November 2013",
	 * "","£300.00","£3720.00"
	 */
	public NationwideFilePreprocessor() {
		super(4);
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
