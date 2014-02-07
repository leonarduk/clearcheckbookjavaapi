package com.leonarduk.clearcheckbook;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * A class to hold helper methods around the API. This adds caching to the
 * {@link ClearCheckBookConnection} class. It is expected client code will use
 * this class as an interface to the API.
 * 
 * @author Stephen Leonard
 * @since 6 Feb 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ClearCheckBookHelper {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookHelper.class);
	private ClearCheckBookConnection connection;
	private ClearCheckBookFileHandler fileHandler;

	private Map<Long, AccountDataType> accountsMap = null;

	public ClearCheckBookHelper(String userName, String password) {

		this.connection = new ClearCheckBookConnection(userName, password);
		this.fileHandler = new ClearCheckBookFileHandler();
	}

	public File exportAccounts(String fileName, List<AccountDataType> accounts)
			throws ClearcheckbookException {
		return this.fileHandler.exportAccounts(fileName, accounts);
	}

	public File exportCategories(String fileName,
			List<CategoryDataType> categories) throws ClearcheckbookException {
		return this.fileHandler.exportCategories(fileName, categories);
	}

	public File exportLimits(String fileName, List<LimitDataType> limits)
			throws ClearcheckbookException {
		return this.fileHandler.exportLimits(fileName, limits);
	}

	public File exportReminders(String fileName,
			List<ReminderDataType> reminders) throws ClearcheckbookException {
		return this.fileHandler.exportReminders(fileName, reminders);
	}

	public File exportTransactions(String fileName,
			List<TransactionDataType> transactions)
			throws ClearcheckbookException {
		return this.fileHandler.exportTransactions(fileName, transactions);
	}

	/**
	 * fetch accounts from memory cache if fetched already.
	 * 
	 * @return
	 * @throws ClearcheckbookException
	 */
	public List<AccountDataType> getAccounts() throws ClearcheckbookException {
		return new ArrayList<>(getAccountsMap().values());
	}

	/**
	 * fetch accounts from memory cache if fetched already.
	 * 
	 * @return
	 * @throws ClearcheckbookException
	 */
	public Map<Long, AccountDataType> getAccountsMap()
			throws ClearcheckbookException {
		if (null == accountsMap) {

			List<AccountDataType> accounts = this.connection.account().getAll();
			accountsMap = new HashMap<Long, AccountDataType>();
			for (Iterator<AccountDataType> iterator = accounts.iterator(); iterator
					.hasNext();) {
				AccountDataType accountDataType = iterator.next();
				accountsMap.put(accountDataType.getId(), accountDataType);
			}
		}
		return accountsMap;
	}

	public List<CategoryDataType> getCategories()
			throws ClearcheckbookException {
		return this.connection.category().getAll();
	}

	public List<LimitDataType> getLimits() throws ClearcheckbookException {
		return this.connection.limit().getAll();
	}

	public List<ReminderDataType> getReminders() throws ClearcheckbookException {
		return this.connection.reminder().getAll();
	}

	public List<TransactionDataType> getTransactions()
			throws ClearcheckbookException {
		return this.connection.transaction().getAll();
	}

	public List<TransactionDataType> getTransactions(AccountDataType account)
			throws ClearcheckbookException {
		return this.connection.transaction().getAll(account);
	}

	public List<AccountDataType> importAccounts(String fileName)
			throws ClearcheckbookException {
		// remove the cache
		this.accountsMap = null;
		return this.fileHandler.importAccounts(fileName);
	}

	public List<TransactionDataType> importTransactions(
			String transactionsFileName, FilePreProcessor preprocessor)
			throws ClearcheckbookException {
		return this.fileHandler.importTransactions(transactionsFileName,
				preprocessor);
	}

	/**
	 * API extension to compare the provided account id with the list of ids for
	 * this user.
	 * 
	 * @param accountId
	 * @return
	 * @throws ClearcheckbookException
	 */
	public boolean isAccountIdValid(Long accountId)
			throws ClearcheckbookException {
		return getAccountsMap().containsKey(accountId);
	}

	public void processTransactions(List<TransactionDataType> dataTypeList)
			throws ClearcheckbookException {
		this.connection.transaction().bulkProcess(dataTypeList);
		;

	}

	public void processAccounts(List<AccountDataType> accounts) throws ClearcheckbookException {
		this.connection.account().bulkProcess(accounts);
	}
}
