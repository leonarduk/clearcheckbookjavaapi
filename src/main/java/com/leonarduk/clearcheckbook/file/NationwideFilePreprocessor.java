/**
 * NationwideFilePreprocessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.file;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.utils.DateUtils;

/**
 * The Class NationwideFilePreprocessor.
 */
public class NationwideFilePreprocessor extends TransactionFilePreprocessor {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(NationwideFilePreprocessor.class);

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

	/**
	 * Instantiates a new nationwide file preprocessor.
	 *
	 * @param id
	 *            the id
	 */
	public NationwideFilePreprocessor(final Long id) {
		super(4, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getAmount(java.util.Map)
	 */
	@Override
	protected String getAmount(final Map<String, String> fieldsMap) {
		final String credit = fieldsMap.get("paid in");
		final String debit = fieldsMap.get("paid out");
		double amount = 0;
		if (credit.equals("")) {
			amount = -1 * this.getDouble(debit);
		}
		else {
			amount = this.getDouble(credit);
		}
		return String.format("%.2f", Double.valueOf(amount));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getCheckNum(java.util.Map)
	 */
	@Override
	protected String getCheckNum(final Map<String, String> fieldsMap) {
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getDate(java.util.Map)
	 */
	@Override
	protected String getDate(final Map<String, String> fieldsMap) throws ClearcheckbookException {
		final String dateString = fieldsMap.get("date");
		NationwideFilePreprocessor._logger.debug("getDate:" + dateString + ":" + fieldsMap);
		Date date;
		try {
			date = DateUtils.getDate(dateString, "dd MMM yyyy");
		}
		catch (final ParseException e) {
			throw new ClearcheckbookException("Failed to parse date: " + dateString, e);
		}
		return DateUtils.getFormattedDate("yyyy-MM-dd", date);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getDesription(java.util.Map)
	 */
	@Override
	protected String getDesription(final Map<String, String> fieldsMap) {
		return fieldsMap.get("transaction type") + " " + fieldsMap.get("description");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getMemo(java.util.Map)
	 */
	@Override
	protected String getMemo(final Map<String, String> fieldsMap) {
		return "Balance: " + this.getDouble(fieldsMap.get("balance"));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getPayee(java.util.Map)
	 */
	@Override
	protected String getPayee(final Map<String, String> fieldsMap) {
		return fieldsMap.get("description");
	}
}
