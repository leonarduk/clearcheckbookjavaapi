package com.leonarduk.clearcheckbook;

import java.util.Map;

import com.leonarduk.clearcheckbook.dto.TransactionDataType;

public class FilePreprocessor {

	private int rowsToSkip;

	public int getRowsToSkip() {
		return rowsToSkip;
	}

	public FilePreprocessor() {
		this(0);
	}

	public FilePreprocessor(int rowsToSkip) {
		this.rowsToSkip = rowsToSkip;
	}

	/**
	 * // "DATE","AMOUNT","DESCRIPTION","CHECK_NUM","MEMO","PAYEE"
	 * 
	 * @param fieldsMap
	 * @return
	 * @throws ClearcheckbookException
	 */
	public Map<String, String> processRow(Map<String, String> fieldsMap)
			throws ClearcheckbookException {
		fieldsMap.put(TransactionDataType.Fields.DATE.name().toLowerCase(),
				getDate(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.AMOUNT.name().toLowerCase(),
				getAmount(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.DESCRIPTION.name()
				.toLowerCase(), getDesription(fieldsMap));
		fieldsMap.put(
				TransactionDataType.Fields.CHECK_NUM.name().toLowerCase(),
				getCheckNum(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.MEMO.name().toLowerCase(),
				getMemo(fieldsMap));
		fieldsMap.put(TransactionDataType.Fields.PAYEE.name().toLowerCase(),
				getPayee(fieldsMap));

		return fieldsMap;
	}

	protected String getPayee(Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.PAYEE.name()
				.toLowerCase());
	}

	protected String getMemo(Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.MEMO.name()
				.toLowerCase());
	}

	protected String getCheckNum(Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.CHECK_NUM.name()
				.toLowerCase());
	}

	protected String getDesription(Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.DESCRIPTION.name()
				.toLowerCase());
	}

	protected String getAmount(Map<String, String> fieldsMap) {
		return fieldsMap.get(TransactionDataType.Fields.AMOUNT.name()
				.toLowerCase());
	}

	protected String getDate(Map<String, String> fieldsMap)
			throws ClearcheckbookException {
		return fieldsMap.get(TransactionDataType.Fields.DATE.name()
				.toLowerCase());
	}

	/**
	 * Helper function to remove all but numbers and periods
	 * 
	 * @param nextValue
	 * @return
	 */
	public double getDouble(String nextValue) {
		if (!nextValue.isEmpty()) {
			String number = nextValue.replaceAll("[^0-9. ]", "");
			return Double.valueOf(number);
		}
		return 0;
	}
}
