package com.leonarduk.clearcheckbook.file;

import java.util.Map;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

public interface FilePreProcessor {

	public abstract int getRowsToSkip();

	/**
	 * // "DATE","AMOUNT","DESCRIPTION","CHECK_NUM","MEMO","PAYEE"
	 * 
	 * @param fieldsMap
	 * @return
	 * @throws ClearcheckbookException
	 */
	public abstract Map<String, String> processRow(Map<String, String> fieldsMap)
			throws ClearcheckbookException;

}