/**
 * AMEXFilePreprocessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.file;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.DateUtils;

/**
 * The Class AMEXFilePreprocessor.
 */
public class AMEXFilePreprocessor extends TransactionFilePreprocessor {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(AMEXFilePreprocessor.class);

	/**
	 * Instantiates a new AMEX file preprocessor.
	 */
	public AMEXFilePreprocessor() {
		super(0);
	}

	/**
	 * Instantiates a new AMEX file preprocessor.
	 *
	 * @param accountId
	 *            the account id
	 */
	public AMEXFilePreprocessor(final long accountId) {
		super(0, accountId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getAmount(java.util.Map)
	 */
	@Override
	protected String getAmount(final Map<String, String> fieldsMap) {
		return String.valueOf(-1 * Double
		        .valueOf(fieldsMap.get(TransactionDataType.Fields.AMOUNT.name().toLowerCase())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getDate(java.util.Map)
	 */
	@Override
	protected String getDate(final Map<String, String> fieldsMap) throws ClearcheckbookException {
		final String dateString = fieldsMap.get("date");
		AMEXFilePreprocessor._logger.debug("getDate:" + dateString + ":" + fieldsMap);
		Date date;
		try {
			date = DateUtils.getDate(dateString, "dd/MM/yyyy");
		}
		catch (final ParseException e) {
			throw new ClearcheckbookException("Failed to parse date: " + dateString, e);
		}
		return DateUtils.getFormattedDate("yyyy-MM-dd", date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#getPayee(java.util.Map)
	 */
	@Override
	protected String getPayee(final Map<String, String> fieldsMap) {
		return super.getDesription(fieldsMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor#processHeaderRow(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<String> processHeaderRow(final String separator, final String line)
	        throws IOException {
		// Read header
		final List<String> headerFields = new LinkedList<>();
		// 21/01/2014,"Reference: AT140220038000010303608"," 56.76",
		// "TESCO STORES 2934 NEW MALDEN"," Process Date 22/01/2014",

		headerFields.add(TransactionDataType.Fields.DATE.name());
		headerFields.add(TransactionDataType.Fields.CHECK_NUM.name());
		headerFields.add(TransactionDataType.Fields.AMOUNT.name());
		headerFields.add(TransactionDataType.Fields.DESCRIPTION.name());
		headerFields.add(TransactionDataType.Fields.MEMO.name());
		headerFields.add("IGNORE");

		return headerFields;
	}
}
