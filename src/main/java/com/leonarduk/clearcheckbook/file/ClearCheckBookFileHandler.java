/**
 * ClearCheckBookFileHandler
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;

/**
 * The Class ClearCheckBookFileHandler.
 */
public class ClearCheckBookFileHandler {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ClearCheckBookFileHandler.class);

	/**
	 * Instantiates a new clear check book file handler.
	 */
	public ClearCheckBookFileHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Export accounts.
	 *
	 * @param fileName
	 *            the file name
	 * @param accounts
	 *            the accounts
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public File exportAccounts(final String fileName, final List<AccountDataType> accounts)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("exportAccounts: " + fileName + " " + accounts);
		final Enum<?>[] headers = AccountDataType.Fields.values();
		return this.exportToFile(fileName, headers, accounts);
	}

	/**
	 * Export categories.
	 *
	 * @param fileName
	 *            the file name
	 * @param categories
	 *            the categories
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public File exportCategories(final String fileName, final List<CategoryDataType> categories)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("exportCategories: " + fileName + " " + categories);
		final Enum<?>[] headers = CategoryDataType.Fields.values();
		return this.exportToFile(fileName, headers, categories);
	}

	/**
	 * Export limits.
	 *
	 * @param fileName
	 *            the file name
	 * @param limits
	 *            the limits
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public File exportLimits(final String fileName, final List<LimitDataType> limits)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("exportLimits: " + fileName + " " + limits);
		final Enum<?>[] headers = LimitDataType.Fields.values();
		return this.exportToFile(fileName, headers, limits);
	}

	/**
	 * Export reminders.
	 *
	 * @param fileName
	 *            the file name
	 * @param reminders
	 *            the reminders
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public File exportReminders(final String fileName, final List<ReminderDataType> reminders)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("exportReminders: " + fileName + " " + reminders);
		final Enum<?>[] headers = ReminderDataType.Fields.values();
		return this.exportToFile(fileName, headers, reminders);
	}

	/**
	 * Export to file.
	 *
	 * @param fileName
	 *            the file name
	 * @param headers
	 *            the headers
	 * @param dataTypes
	 *            the data types
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private File exportToFile(final String fileName, final Enum<?>[] headers,
	        final List<? extends AbstractDataType> dataTypes) throws ClearcheckbookException {
		PrintWriter writer;
		try {
			final File file = new File(fileName);
			writer = new PrintWriter(file, "UTF-8");
			final String separator = "\",\"";
			writer.println("\"" + Joiner.on(separator).join(headers) + "\"");
			for (final AbstractDataType abstractDataType : dataTypes) {
				final AbstractDataType<?> dataType = abstractDataType;
				writer.println("\"" + Joiner.on(separator).join(dataType.getValues()) + "\"");
			}
			writer.close();
			return file;
		}
		catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new ClearcheckbookException("Failed to export to " + fileName, e);
		}

	}

	/**
	 * Export transactions.
	 *
	 * @param fileName
	 *            the file name
	 * @param transactions
	 *            the transactions
	 * @return the file
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public File exportTransactions(final String fileName,
	        final List<TransactionDataType> transactions) throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger
		        .debug("exportTransactions: " + fileName + " " + transactions);
		final Enum<?>[] headers = TransactionDataType.getFileFields();
		return this.exportToFile(fileName, headers, transactions);
	}

	/**
	 * Import accounts.
	 *
	 * @param fileName
	 *            the file name
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<AccountDataType> importAccounts(final String fileName)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("importTransactions: " + fileName);
		return this.importFromFile(fileName, AccountDataType.class);
	}

	/**
	 * Import categories.
	 *
	 * @param fileName
	 *            the file name
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<CategoryDataType> importCategories(final String fileName)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("importTransactions: " + fileName);
		return this.importFromFile(fileName, CategoryDataType.class);
	}

	/**
	 * Import from file.
	 *
	 * @param <D>
	 *            the generic type
	 * @param fileName
	 *            the file name
	 * @param c
	 *            the c
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public <D extends AbstractDataType<?>> List<D> importFromFile(final String fileName,
	        final Class<D> c) throws ClearcheckbookException {
		return this.importFromFile(fileName, c, new TransactionFilePreprocessor());
	}

	/**
	 * Import from file.
	 *
	 * @param <D>
	 *            the generic type
	 * @param fileName
	 *            the file name
	 * @param class1
	 *            the class1
	 * @param processor
	 *            the processor
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public <D extends AbstractDataType<?>> List<D> importFromFile(final String fileName,
	        final Class<D> class1, final FilePreProcessor processor)
	                throws ClearcheckbookException {

		final String separator = ",";
		final List<D> dataItems = new LinkedList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			// Ignore some rows
			for (int i = 0; i < processor.getRowsToSkip(); i++) {
				br.readLine();
			}
			String line = br.readLine();
			final List<String> headerFields = processor.processHeaderRow(separator, line);

			// first data line
			line = br.readLine();

			while (line != null) {
				final Map<String, String> fieldsMap = new HashMap<>();
				final Iterable<String> fields = Splitter.on(separator).trimResults().split(line);
				final Iterator<String> headerIter = headerFields.iterator();
				for (final String field : fields) {
					final String headerName = headerIter.next();
					ClearCheckBookFileHandler._logger
					        .debug(headerName + "=" + field.replace("\"", ""));
					fieldsMap.put(headerName.toLowerCase(), field.replace("\"", ""));
				}
				final Map<String, String> processedMap = processor.processRow(fieldsMap);
				try {
					final D newElem = class1.getDeclaredConstructor(Map.class)
					        .newInstance(processedMap);
					dataItems.add(newElem);
				}
				catch (final Exception e) {
					throw new ClearcheckbookException("Failed to import file", e);
				}

				line = br.readLine();
			}
			return dataItems;
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to import file", e);
		}
	}

	/**
	 * Import limits.
	 *
	 * @param fileName
	 *            the file name
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<LimitDataType> importLimits(final String fileName) throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("importTransactions: " + fileName);
		return this.importFromFile(fileName, LimitDataType.class);
	}

	/**
	 * Import reminders.
	 *
	 * @param fileName
	 *            the file name
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<ReminderDataType> importReminders(final String fileName)
	        throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("importTransactions: " + fileName);
		return this.importFromFile(fileName, ReminderDataType.class);
	}

	/**
	 * Import transactions.
	 *
	 * @param fileName
	 *            the file name
	 * @param processor
	 *            the processor
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> importTransactions(final String fileName,
	        final FilePreProcessor processor) throws ClearcheckbookException {
		ClearCheckBookFileHandler._logger.debug("importTransactions: " + fileName);
		return this.importFromFile(fileName, TransactionDataType.class, processor);
	}

}
