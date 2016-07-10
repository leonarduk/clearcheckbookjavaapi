/**
 * ClearCheckBookHelper
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.file.ClearCheckBookFileHandler;
import com.leonarduk.clearcheckbook.file.FilePreProcessor;
import com.leonarduk.clearcheckbook.processor.ClearCheckBookTaskProcessor;
import com.leonarduk.clearcheckbook.processor.ClearCheckBookTaskSerialProcessor;
import com.leonarduk.clearcheckbook.processor.parallel.ClearCheckBookDataTypeParallelProcessor;

/**
 * A class to hold helper methods around the API. This adds caching to the
 * {@link ClearCheckBookConnection} class. It is expected client code will use this class as an
 * interface to the API.
 *
 * @author Stephen Leonard
 * @since 6 Feb 2014
 *
 */
public class ClearCheckBookHelper {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ClearCheckBookHelper.class);

	/** The connection. */
	private final ClearCheckBookConnection connection;

	/** The file handler. */
	private final ClearCheckBookFileHandler fileHandler;

	/** The accounts map. */
	private Map<Long, AccountDataType> accountsMap = null;

	/** The consumers. */
	private final int consumers;

	/**
	 * Instantiates a new clear check book helper.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param consumers
	 *            the consumers
	 */
	public ClearCheckBookHelper(final String userName, final String password, final int consumers) {
		this.consumers = consumers;
		this.connection = new ClearCheckBookConnection(userName, password);
		this.fileHandler = new ClearCheckBookFileHandler();
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
		return this.fileHandler.exportAccounts(fileName, accounts);
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
		return this.fileHandler.exportCategories(fileName, categories);
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
		return this.fileHandler.exportLimits(fileName, limits);
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
		return this.fileHandler.exportReminders(fileName, reminders);
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
		return this.fileHandler.exportTransactions(fileName, transactions);
	}

	/**
	 * fetch accounts from memory cache if fetched already.
	 *
	 * @return the accounts
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<AccountDataType> getAccounts() throws ClearcheckbookException {
		return new ArrayList<>(this.getAccountsMap().values());
	}

	/**
	 * fetch accounts from memory cache if fetched already.
	 *
	 * @return the accounts map
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public Map<Long, AccountDataType> getAccountsMap() throws ClearcheckbookException {
		if (null == this.accountsMap) {

			final List<AccountDataType> accounts = this.connection.account().getAll();
			this.accountsMap = new HashMap<Long, AccountDataType>();
			for (final AccountDataType accountDataType : accounts) {
				this.accountsMap.put(accountDataType.getId(), accountDataType);
			}
		}
		return this.accountsMap;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<CategoryDataType> getCategories() throws ClearcheckbookException {
		return this.connection.category().getAll();
	}

	/**
	 * Gets the changes only.
	 *
	 * @param modified
	 *            the modified
	 * @param original
	 *            the original
	 * @return the changes only
	 */
	protected List<TransactionDataType> getChangesOnly(final List<TransactionDataType> modified,
	        final List<TransactionDataType> original) {
		modified.removeAll(original);
		return modified;
	}

	/**
	 * Gets the limits.
	 *
	 * @return the limits
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<LimitDataType> getLimits() throws ClearcheckbookException {
		return this.connection.limit().getAll();
	}

	/**
	 * Gets the reminders.
	 *
	 * @return the reminders
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<ReminderDataType> getReminders() throws ClearcheckbookException {
		return this.connection.reminder().getAll();
	}

	/**
	 * Gets the transactions.
	 *
	 * @return the transactions
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getTransactions() throws ClearcheckbookException {
		return this.connection.transaction().getAll();
	}

	/**
	 * Gets the transactions.
	 *
	 * @param account
	 *            the account
	 * @return the transactions
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getTransactions(final AccountDataType account)
	        throws ClearcheckbookException {
		return this.connection.transaction().getAll(account);
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
		// remove the cache
		this.accountsMap = null;
		return this.fileHandler.importAccounts(fileName);
	}

	/**
	 * Import transactions.
	 *
	 * @param transactionsFileName
	 *            the transactions file name
	 * @param preprocessor
	 *            the preprocessor
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> importTransactions(final String transactionsFileName,
	        final FilePreProcessor preprocessor) throws ClearcheckbookException {
		return this.fileHandler.importTransactions(transactionsFileName, preprocessor);
	}

	/**
	 * API extension to compare the provided account id with the list of ids for this user.
	 *
	 * @param accountId
	 *            the account id
	 * @return true, if is account id valid
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public boolean isAccountIdValid(final Long accountId) throws ClearcheckbookException {
		return this.getAccountsMap().containsKey(accountId);
	}

	/**
	 * Process accounts.
	 *
	 * @param accounts
	 *            the accounts
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public void processAccounts(final List<AccountDataType> accounts)
	        throws ClearcheckbookException {
		this.connection.account().bulkProcess(accounts);
	}

	/**
	 * Process transactions.
	 *
	 * @param modified
	 *            the modified
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactions(final List<TransactionDataType> modified)
	        throws ClearcheckbookException {
		return this.processTransactions(modified,
		        new ClearCheckBookTaskSerialProcessor<TransactionDataType>(
		                this.connection.transaction()));
	}

	/**
	 * Process transactions.
	 *
	 * @param dataTypeList
	 *            the data type list
	 * @param processor
	 *            the processor
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactions(final List<TransactionDataType> dataTypeList,
	        final ClearCheckBookTaskProcessor<TransactionDataType> processor)
	                throws ClearcheckbookException {
		return processor.processQueue(dataTypeList);
	}

	/**
	 * Process transactions.
	 *
	 * @param modified
	 *            the modified
	 * @param original
	 *            the original
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactions(final List<TransactionDataType> modified,
	        final List<TransactionDataType> original) throws ClearcheckbookException {
		return this.processTransactions(modified, original,
		        new ClearCheckBookTaskSerialProcessor<TransactionDataType>(
		                this.connection.transaction()));
	}

	/**
	 * Process transactions.
	 *
	 * @param modified
	 *            the modified
	 * @param original
	 *            the original
	 * @param processor
	 *            the processor
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactions(final List<TransactionDataType> modified,
	        final List<TransactionDataType> original,
	        final ClearCheckBookTaskProcessor<TransactionDataType> processor)
	                throws ClearcheckbookException {
		final List<TransactionDataType> processList = this.getChangesOnly(modified, original);
		return this.processTransactions(processList, processor);
	}

	/**
	 * Process transactions in parallel.
	 *
	 * @param modified
	 *            the modified
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactionsInParallel(final List<TransactionDataType> modified)
	        throws ClearcheckbookException {
		// limit number of consumers if we have small number of data to process
		final int queueSize = Math.min(100, modified.size());
		final int numberOfConsumers = Math.min(this.consumers, modified.size());
		return this.processTransactions(modified,
		        new ClearCheckBookDataTypeParallelProcessor<TransactionDataType>(
		                this.connection.transaction(), queueSize, numberOfConsumers));
	}

	/**
	 * Process transactions in parallel.
	 *
	 * @param modified
	 *            the modified
	 * @param original
	 *            the original
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<String> processTransactionsInParallel(final List<TransactionDataType> modified,
	        final List<TransactionDataType> original) throws ClearcheckbookException {
		final List<TransactionDataType> processList = this.getChangesOnly(modified, original);
		return this.processTransactionsInParallel(processList);
	}
}
