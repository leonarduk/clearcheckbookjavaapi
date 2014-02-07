package com.leonarduk.clearcheckbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.Config;

/**
 * A simple Command line Interface to use the API.
 * 
 * @author Stephen Leonard
 * @since 6 Feb 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ClearCheckBookCLI {

	private static final String ACCOUNTS_CSV = "accounts.csv";

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookCLI.class);

	/**
	 * 
	 * @param args
	 * @throws ClearcheckbookException
	 */
	public static void main(String[] args) throws ClearcheckbookException {
		Config config = new Config();

		String userName = config
				.getMandatoryPropertyValue("clearcheckbook.user");
		String password = config
				.getMandatoryPropertyValue("clearcheckbook.password");

		String command = config
				.getOptionalPropertyValue("clearcheckbook.command");

		ClearCheckBookCLI cli = new ClearCheckBookCLI(userName, password);
		if (null == command) {
			// There is an option to quit, so iterate till then
			while (true) {
				cli.getMenu();
			}
		}

	}

	private ClearCheckBookHelper helper;

	private Map<Long, AccountDataType> accountsMap = null;

	public ClearCheckBookCLI(String userName, String password) {
		helper = new ClearCheckBookHelper(userName, password);
		System.out.println("Clearcheckbook Tools - connecting as " + userName);
	}

	private AccountDataType chooseAccount(String zeroOption)
			throws ClearcheckbookException {
		List<AccountDataType> accounts = fetchAccounts();
		AccountDataType account = null;
		for (int i = 0; i < accounts.size(); i++) {
			AccountDataType accountDataType = accounts.get(i);
			System.out.println((i + 1) + " " + accountDataType.getId() + " "
					+ accountDataType.getName() + " "
					+ accountDataType.getCurrentBalance());
		}
		int option = getIntegerInput(
				"Choose number of account or select 0 for " + zeroOption + ":",
				accounts.size());
		if (option > 0) {
			account = accounts.get(option - 1);
		}
		return account;
	}

	private void exportAccounts() throws ClearcheckbookException {
		List<AccountDataType> accounts = fetchAccounts();
		String fileName = getFilename(ACCOUNTS_CSV);
		helper.exportAccounts(fileName, accounts);
		System.out.println("Saved " + accounts.size() + " accounts to "
				+ fileName);
	}

	private void exportTransactions() throws ClearcheckbookException {
		List<TransactionDataType> transactions;
		AccountDataType account = chooseAccount("all transactions");

		if (null == account) {
			System.out.println("Fetching all transactions...");
			transactions = this.helper.getTransactions();
		} else {
			System.out.println("Fetching transactions for " + account.getName()
					+ "...");
			transactions = this.helper.getTransactions(account);
		}
		System.out.println("Fetched " + transactions.size() + " transactions");
		if (transactions.size() > 0) {
			String fileName = getFilename(getDefaultFileName(account));
			System.out.println("Exporting " + transactions.size()
					+ " transactions to file : " + fileName + "...");
			this.helper.exportTransactions(fileName, transactions);
			System.out.println("...done.");
		} else {
			System.out.println("Nothing to save.");
		}
	}

	private List<AccountDataType> fetchAccounts()
			throws ClearcheckbookException {
		System.out.println("Fetching account list...");
		List<AccountDataType> accounts = helper.getAccounts();
		return accounts;
	}

	private void getAccountsMenu() throws ClearcheckbookException {
		String[] options = new String[] { "List", "Export", "Import" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		int option = getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
		case 0:
			listAccounts();
			break;
		case 1:
			exportAccounts();
			break;
		case 2:
			importAccounts();
			break;
		default:
			throw new ClearcheckbookException("Invalid option: " + option);
		}
	}

	private String getDefaultFileName(AccountDataType account) {
		String defaultFilename;
		if (null == account) {
			defaultFilename = "all_transactions.csv";
		} else {
			defaultFilename = "account_" + account.getId() + ".csv";
		}
		return defaultFilename;
	}

	private String getFilename(String defaultFilename)
			throws ClearcheckbookException {
		String fileName = getStringInput("Enter file name [enter for "
				+ defaultFilename + "]:");
		if (fileName.trim().equals("")) {
			fileName = defaultFilename;
		}
		return fileName;
	}

	private int getIntegerInput(String question, int maxNumber)
			throws ClearcheckbookException {
		System.out.print(question);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			int i = Integer.parseInt(br.readLine());
			if (i > maxNumber || i < 0) {
				System.err.println("Choose number between 0 and " + maxNumber);
				return getIntegerInput(question, maxNumber);
			}
			return i;
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Format!");
			return getIntegerInput(question, maxNumber);
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to get option", e);
		}
	}

	private void getMenu() throws ClearcheckbookException {
		String[] options = new String[] { "Accounts", "Transaction", "Quit" };
		System.out.println("MAIN MENU");
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		int option = getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
		case 0:
			getAccountsMenu();
			break;
		case 1:
			getTransactionsMenu();
			break;
		default:
			System.out.println("Exiting");
			System.exit(0);
		}
	}

	private String getStringInput(String question)
			throws ClearcheckbookException {
		System.out.print(question);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to get input", e);
		}

	}

	private void getTransactionsMenu() throws ClearcheckbookException {
		String[] options = new String[] { "List", "Export", "Import" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		int option = getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
		case 0:
			listTransactions();
			break;
		case 1:
			exportTransactions();
			break;
		case 2:
			importTransactions();
			break;
		default:
			throw new ClearcheckbookException("Invalid option: " + option);
		}
	}

	private void importAccounts() throws ClearcheckbookException {
		String fileName = getFilename(ACCOUNTS_CSV);
		List<AccountDataType> accounts = helper.importAccounts(fileName);
		this.helper.processAccounts(accounts);
		System.out.println("Imported " + accounts.size() + " accounts");
	}

	private void importTransactions() throws ClearcheckbookException {
		System.out.println("Choose which account to import to");
		AccountDataType account = chooseAccount("no account specified");
		String fileName = getFilename(getDefaultFileName(account));

		List<TransactionDataType> transactions = helper.importTransactions(
				fileName, new FilePreprocessor());
		this.helper.processTransactions(transactions);
	}

	private void listAccounts() throws ClearcheckbookException {
		List<AccountDataType> accounts = fetchAccounts();
		for (Iterator<AccountDataType> iterator = accounts.iterator(); iterator
				.hasNext();) {
			AccountDataType accountDataType = iterator.next();
			System.out.println(accountDataType.getId() + " : "
					+ accountDataType.getName() + " "
					+ accountDataType.getCurrentBalance());
		}
	}

	private void listTransactions() throws ClearcheckbookException {
		List<TransactionDataType> transactions;
		AccountDataType account = chooseAccount("all transactions");

		if (null == account) {
			System.out.println("Fetching all transactions...");
			transactions = this.helper.getTransactions();
		} else {
			System.out.println("Fetching transactions for " + account.getName()
					+ "...");
			transactions = this.helper.getTransactions(account);
		}
		System.out.println("Fetched " + transactions.size() + " transactions");
		for (Iterator<TransactionDataType> iterator = transactions.iterator(); iterator
				.hasNext();) {
			TransactionDataType transactionDataType = iterator.next();
			System.out.println(transactionDataType.getDate() + " "
					+ transactionDataType.getAmount() + " "
					+ transactionDataType.getPayee() + " "
					+ transactionDataType.getMemo());
		}

	}

}
