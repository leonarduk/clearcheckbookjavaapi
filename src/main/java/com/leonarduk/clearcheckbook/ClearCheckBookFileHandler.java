package com.leonarduk.clearcheckbook;

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
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;

public class ClearCheckBookFileHandler {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookFileHandler.class);

	public ClearCheckBookFileHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param fileName
	 * @param accounts
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportAccounts(String fileName, List<AccountDataType> accounts)
			throws ClearcheckbookException {
		_logger.debug("exportAccounts: " + fileName + " " + accounts);
		Enum<?>[] headers = AccountDataType.Fields.values();
		return exportToFile(fileName, headers, accounts);
	}

	/**
	 * 
	 * @param fileName
	 * @param categories
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportCategories(String fileName,
			List<CategoryDataType> categories) throws ClearcheckbookException {
		_logger.debug("exportCategories: " + fileName + " " + categories);
		Enum<?>[] headers = CategoryDataType.Fields.values();
		return exportToFile(fileName, headers, categories);
	}

	/**
	 * 
	 * @param fileName
	 * @param limits
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportLimits(String fileName, List<LimitDataType> limits)
			throws ClearcheckbookException {
		_logger.debug("exportLimits: " + fileName + " " + limits);
		Enum<?>[] headers = LimitDataType.Fields.values();
		return exportToFile(fileName, headers, limits);
	}

	/**
	 * 
	 * @param fileName
	 * @param reminders
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportReminders(String fileName,
			List<ReminderDataType> reminders) throws ClearcheckbookException {
		_logger.debug("exportReminders: " + fileName + " " + reminders);
		Enum<?>[] headers = ReminderDataType.Fields.values();
		return exportToFile(fileName, headers, reminders);
	}

	/**
	 * 
	 * @param fileName
	 * @param headers
	 * @param dataTypes
	 * @return
	 * @throws ClearcheckbookException
	 */
	private File exportToFile(String fileName, Enum<?>[] headers,
			List<? extends AbstractDataType> dataTypes)
			throws ClearcheckbookException {
		PrintWriter writer;
		try {
			File file = new File(fileName);
			writer = new PrintWriter(file, "UTF-8");
			String separator = "\",\"";
			writer.println("\"" + Joiner.on(separator).join(headers) + "\"");
			for (Iterator<? extends AbstractDataType> iterator = dataTypes
					.iterator(); iterator.hasNext();) {
				AbstractDataType<?> dataType = iterator.next();
				writer.println("\""
						+ Joiner.on(separator).join(dataType.getValues())
						+ "\"");
			}
			writer.close();
			return file;
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new ClearcheckbookException(
					"Failed to export to " + fileName, e);
		}

	}

	/**
	 * 
	 * @param fileName
	 * @param transactions
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportTransactions(String fileName,
			List<TransactionDataType> transactions)
			throws ClearcheckbookException {
		_logger.debug("exportTransactions: " + fileName + " " + transactions);
		Enum<?>[] headers = TransactionDataType.getFileFields();
		return exportToFile(fileName, headers, transactions);
	}

	public List<AccountDataType> importAccounts(String fileName)
			throws ClearcheckbookException {
		_logger.debug("importTransactions: " + fileName);
		return importFromFile(fileName, AccountDataType.class);
	}

	public List<CategoryDataType> importCategories(String fileName)
			throws ClearcheckbookException {
		_logger.debug("importTransactions: " + fileName);
		return importFromFile(fileName, CategoryDataType.class);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws ClearcheckbookException
	 */
	public <D extends AbstractDataType<?>> List<D> importFromFile(
			String fileName, Class<D> c) throws ClearcheckbookException {
		return importFromFile(fileName, c, new FilePreprocessor());
	}

	public <D extends AbstractDataType<?>> List<D> importFromFile(
			String fileName, Class<D> class1, FilePreprocessor processor)
			throws ClearcheckbookException {

		String separator = ",";
		List<D> dataItems = new LinkedList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			// Ignore some rows
			for (int i = 0; i < processor.getRowsToSkip(); i++) {
				br.readLine();
			}

			// Read header
			List<String> headerFields = new LinkedList<>();
			String line = br.readLine();
			Iterable<String> columnNames = Splitter.on(separator).trimResults()
					.split(line);
			for (String columnn : columnNames) {

				headerFields.add(columnn.replace("\"", ""));
			}

			// first data line
			line = br.readLine();

			while (line != null) {
				Map<String, String> fieldsMap = new HashMap<>();
				Iterable<String> fields = Splitter.on(separator).trimResults()
						.split(line);
				Iterator<String> headerIter = headerFields.iterator();
				for (String field : fields) {
					String headerName = headerIter.next();
					_logger.debug(headerName + "=" + field.replace("\"", ""));
					fieldsMap.put(headerName.toLowerCase(),
							field.replace("\"", ""));
				}
				Map<String, String> processedMap = processor
						.processRow(fieldsMap);
				try {
					D newElem = class1.getDeclaredConstructor(Map.class)
							.newInstance(processedMap);
					dataItems.add(newElem);
				} catch (Exception e) {
					throw new ClearcheckbookException("Failed to import file",
							e);
				}

				line = br.readLine();
			}
			return dataItems;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to import file", e);
		}
	}

	public List<LimitDataType> importLimits(String fileName)
			throws ClearcheckbookException {
		_logger.debug("importTransactions: " + fileName);
		return importFromFile(fileName, LimitDataType.class);
	}

	public List<ReminderDataType> importReminders(String fileName)
			throws ClearcheckbookException {
		_logger.debug("importTransactions: " + fileName);
		return importFromFile(fileName, ReminderDataType.class);
	}

	public List<TransactionDataType> importTransactions(String fileName)
			throws ClearcheckbookException {
		return importTransactions(fileName, new FilePreprocessor());
	}

	public List<TransactionDataType> importTransactions(String fileName,
			FilePreprocessor processor) throws ClearcheckbookException {
		_logger.debug("importTransactions: " + fileName);
		return importFromFile(fileName, TransactionDataType.class, processor);
	}

}
