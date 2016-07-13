/**
 * TransactionFilePreprocessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.file;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;

/**
 * The Class TransactionFilePreprocessor.
 */
public class TransactionFilePreprocessor implements FilePreProcessor {

	/** The default account id. */
	private final Long defaultAccountId;

	/** The rows to skip. */
	private final int rowsToSkip;

	/**
	 * Instantiates a new transaction file preprocessor.
	 */
	public TransactionFilePreprocessor() {
		this(0);
	}

	/**
	 * Instantiates a new transaction file preprocessor.
	 *
	 * @param rowsToSkip
	 *            the rows to skip
	 */
	public TransactionFilePreprocessor(final int rowsToSkip) {
		this(rowsToSkip, 0L);
	}

	/**
	 * Instantiates a new transaction file preprocessor.
	 *
	 * @param rowsToSkip
	 *            the rows to skip
	 * @param accountId
	 *            the account id
	 */
	public TransactionFilePreprocessor(final int rowsToSkip, final Long accountId) {
		this.defaultAccountId = accountId;
		this.rowsToSkip = rowsToSkip;
	}

	/**
	 * Instantiates a new transaction file preprocessor.
	 *
	 * @param accountId
	 *            the account id
	 */
	public TransactionFilePreprocessor(final Long accountId) {
		this(0, accountId);
	}

	/**
	 * Gets the account id.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the account id
	 */
	protected String getAccountId(final Map<String, String> fieldsMap) {
		String accountId = fieldsMap
		        .get(TransactionDataType.Fields.ACCOUNT_ID.name().toLowerCase());
		if ((null == accountId) || accountId.equals("0")) {
			accountId = String.valueOf(this.defaultAccountId);
		}
		return accountId;
	}

	/**
	 * Gets the amount.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the amount
	 */
	protected String getAmount(final Map<String, String> fieldsMap) {
		final String amountString = fieldsMap
		        .get(TransactionDataType.Fields.AMOUNT.name().toLowerCase());
		if (null == amountString) {
			return "0.00";
		}
		return String.format("%.2f", Double.valueOf(amountString));
	}

	/**
	 * Gets the check num.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the check num
	 */
	protected String getCheckNum(final Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.CHECK_NUM.name().toLowerCase());
	}

	/**
	 * Gets the date.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the date
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected String getDate(final Map<String, String> fieldsMap) throws ClearcheckbookException {
		return fieldsMap.get(TransactionDataType.Fields.DATE.name().toLowerCase());
	}

	/**
	 * Gets the desription.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the desription
	 */
	protected String getDesription(final Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.DESCRIPTION.name().toLowerCase());
	}

	/**
	 * Helper function to remove all but numbers and periods.
	 *
	 * @param nextValue
	 *            the next value
	 * @return the double
	 */
	public double getDouble(final String nextValue) {
		if (!nextValue.isEmpty()) {
			final String number = nextValue.replaceAll("[^0-9. ]", "");
			return Double.valueOf(number).doubleValue();
		}
		return 0;
	}

	/**
	 * Gets the memo.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the memo
	 */
	protected String getMemo(final Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.MEMO.name().toLowerCase());
	}

	/**
	 * Gets the payee.
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the payee
	 */
	protected String getPayee(final Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.PAYEE.name().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.FilePreProcessor#getRowsToSkip()
	 */
	@Override
	public int getRowsToSkip() {
		return this.rowsToSkip;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.file.FilePreProcessor#processHeaderRow(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> processHeaderRow(final String separator, final String line)
	        throws IOException {
		// Read header
		final List<String> headerFields = new LinkedList<>();
		final Iterable<String> columnNames = Splitter.on(separator).trimResults().split(line);
		for (final String columnn : columnNames) {

			headerFields.add(columnn.replace("\"", ""));
		}
		return headerFields;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.FilePreProcessor#processRow(java.util.Map)
	 */
	@Override
	public Map<String, String> processRow(final Map<String, String> fieldsMap)
	        throws ClearcheckbookException {
		fieldsMap.put(TransactionDataType.Fields.DATE.name().toLowerCase(),
		        this.getDate(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.AMOUNT.name().toLowerCase(),
		        this.getAmount(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.DESCRIPTION.name().toLowerCase(),
		        this.getDesription(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.CHECK_NUM.name().toLowerCase(),
		        this.getCheckNum(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.MEMO.name().toLowerCase(),
		        this.getMemo(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.PAYEE.name().toLowerCase(),
		        this.getPayee(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.ACCOUNT_ID.name().toLowerCase(),
		        this.getAccountId(fieldsMap));

		return fieldsMap;
	}
}
