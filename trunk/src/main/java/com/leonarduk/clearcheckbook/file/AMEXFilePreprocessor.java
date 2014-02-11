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

public class AMEXFilePreprocessor extends TransactionFilePreprocessor {

	private static final Logger _logger = Logger
			.getLogger(AMEXFilePreprocessor.class);

	/**
	 * 
	 */
	public AMEXFilePreprocessor() {
		super(0);
	}

	public AMEXFilePreprocessor(long accountId) {
		super(0, accountId);
	}

	@Override
	protected String getDate(Map<String, String> fieldsMap)
			throws ClearcheckbookException {
		String dateString = fieldsMap.get("date");
		_logger.debug("getDate:" + dateString + ":" + fieldsMap);
		Date date;
		try {
			date = DateUtils.getDate(dateString, "dd/MM/yyyy");
		} catch (ParseException e) {
			throw new ClearcheckbookException("Failed to parse date: "
					+ dateString, e);
		}
		return DateUtils.getFormattedDate("yyyy-MM-dd", date);
	}

	@Override
	protected String getAmount(Map<String, String> fieldsMap) {
		return String.valueOf(-1
				* Double.valueOf(fieldsMap
						.get(TransactionDataType.Fields.AMOUNT.name()
								.toLowerCase())));
	}

	@Override
	public List<String> processHeaderRow(String separator, String line)
			throws IOException {
		// Read header
		List<String> headerFields = new LinkedList<>();
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
	
	@Override
	protected String getPayee(Map<String, String> fieldsMap) {
		return super.getDesription(fieldsMap);
	}
}