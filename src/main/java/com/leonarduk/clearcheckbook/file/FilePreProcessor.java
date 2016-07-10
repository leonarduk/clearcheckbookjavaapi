/**
 * FilePreProcessor
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.file;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

/**
 * The Interface FilePreProcessor.
 */
public interface FilePreProcessor {

	/**
	 * Gets the rows to skip.
	 *
	 * @return the rows to skip
	 */
	public abstract int getRowsToSkip();

	/**
	 * Process header row.
	 *
	 * @param separator
	 *            the separator
	 * @param line
	 *            the line
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public List<String> processHeaderRow(String separator, String line) throws IOException;

	/**
	 * // "DATE","AMOUNT","DESCRIPTION","CHECK_NUM","MEMO","PAYEE".
	 *
	 * @param fieldsMap
	 *            the fields map
	 * @return the map
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public abstract Map<String, String> processRow(Map<String, String> fieldsMap)
	        throws ClearcheckbookException;

}
