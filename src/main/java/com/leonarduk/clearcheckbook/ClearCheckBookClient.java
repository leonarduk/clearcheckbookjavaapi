package com.leonarduk.clearcheckbook;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.Config;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 6 Feb 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ClearCheckBookClient {
	private ClearCheckBookConnection connection;
	private ClearCheckBookFileHandler fileHandler;

	public ClearCheckBookClient(String userName, String password) {

		this.connection = new ClearCheckBookConnection(userName, password);
		this.fileHandler = new ClearCheckBookFileHandler();
	}

	/**
	 * 
	 * @param args
	 * @throws ClearcheckbookException
	 */
	public static void main(String[] args) throws ClearcheckbookException {
		Config config = new Config();

		String userName = config.getPropertyValue("clearcheckbook.user");
		String password = config.getPropertyValue("clearcheckbook.password");

		ClearCheckBookClient client = new ClearCheckBookClient(userName,
				password);

		System.out.println("Clearcheckbook Tools - connecting as " + userName);

		// download accounts
		List<AccountDataType> accounts = client.getAccounts();
		for (Iterator<AccountDataType> iterator = accounts.iterator(); iterator
				.hasNext();) {
			AccountDataType accountDataType = iterator.next();
			System.out.println(accountDataType.getId() + " : "
					+ accountDataType.getName() + " "
					+ accountDataType.getCurrentBalance());

		}
		// download transactions

		// upload transactions
		// List<TransactionDataType> dataTypeList = null; // TODO
		//
		// client.processTransactions(dataTypeList);

	}

	public List<AccountDataType> getAccounts() throws ClearcheckbookException {
		return this.connection.account().getAll();
	}

	public List<LimitDataType> getLimits() throws ClearcheckbookException {
		return this.connection.limit().getAll();
	}

	public List<ReminderDataType> getReminders() throws ClearcheckbookException {
		return this.connection.reminder().getAll();
	}

	public List<TransactionDataType> getTransactions(AccountDataType account)
			throws ClearcheckbookException {
		return this.connection.transaction().getAll(account);
	}

	public List<TransactionDataType> getTransactions()
			throws ClearcheckbookException {
		return this.connection.transaction().getAll();
	}

	public List<CategoryDataType> getCategories()
			throws ClearcheckbookException {
		return this.connection.category().getAll();
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

	public List<TransactionDataType> importTransactions(
			String transactionsFileName) throws ClearcheckbookException {
		return this.fileHandler.importTransactions(transactionsFileName);
	}

	public List<TransactionDataType> importTransactions(
			String transactionsFileName, FilePreprocessor preprocessor)
			throws ClearcheckbookException {
		return this.fileHandler.importTransactions(transactionsFileName,
				preprocessor);
	}

	public void processTransactions(List<TransactionDataType> dataTypeList)
			throws ClearcheckbookException {
		this.connection.transaction().bulkProcess(dataTypeList);
		;

	}
}
