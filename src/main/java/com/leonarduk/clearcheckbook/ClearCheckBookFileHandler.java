package com.leonarduk.clearcheckbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
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
	 * @param transactions
	 * @return
	 * @throws ClearcheckbookException
	 */
	public File exportTransactions(String fileName,
			List<TransactionDataType> transactions)
			throws ClearcheckbookException {
		_logger.debug("exportTransactions: " + fileName + " " + transactions);
		Enum<?>[] headers = TransactionDataType.Fields.values();
		return exportToFile(fileName, headers, transactions);
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
			String separator = ",";
			writer.println(Joiner.on(separator).join(headers));
			for (Iterator<? extends AbstractDataType> iterator = dataTypes
					.iterator(); iterator.hasNext();) {
				AbstractDataType dataType = iterator.next();
				writer.println(Joiner.on(separator).join(
						dataType.getValues()));
			}
			writer.close();
			return file;
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new ClearcheckbookException(
					"Failed to export to " + fileName, e);
		}

	}

}
