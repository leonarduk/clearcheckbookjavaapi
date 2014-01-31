package com.leonarduk.clearcheckbook;

import java.io.File;
import java.util.List;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.Config;

public class ClearCheckBookClient {
	private ClearCheckBookConnection connection;
	private ClearCheckBookFileHandler fileHandler;
	private Config config;

	public ClearCheckBookClient() {
		config = new Config();
		String userName = config.getPropertyValue("clearcheckbook.user");
		String password = config.getPropertyValue("clearcheckbook.password");

		this.connection = new ClearCheckBookConnection(userName, password);
		this.fileHandler = new ClearCheckBookFileHandler();
	}

	public static void main(String[] args) {
		ClearCheckBookClient client = new ClearCheckBookClient();
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

}
