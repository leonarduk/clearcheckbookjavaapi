/**
 * ClearCheckBookCLI
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.file.AMEXFilePreprocessor;
import com.leonarduk.clearcheckbook.file.FilePreProcessor;
import com.leonarduk.clearcheckbook.file.NationwideFilePreprocessor;
import com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor;
import com.leonarduk.utils.Config;

/**
 * A simple Command line Interface to use the API.
 *
 * @author Stephen Leonard
 * @since 6 Feb 2014
 *
 *
 */
public class ClearCheckBookCLI {

	/** The Constant ALL_TRANSACTIONS_CSV. */
	private static final String ALL_TRANSACTIONS_CSV = "all_transactions.csv";

	/** The Constant ACCOUNTS_CSV. */
	private static final String ACCOUNTS_CSV = "accounts.csv";

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ClearCheckBookCLI.class);

	/** The helper. */
	private final ClearCheckBookHelper helper;

	/** The accounts map. */
	private final Map<Long, AccountDataType> accountsMap = null;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public static void main(final String[] args) throws ClearcheckbookException {
		final Config config = new Config();

		final String userName = config.getMandatoryPropertyValue("clearcheckbook.user");
		final String password = config.getMandatoryPropertyValue("clearcheckbook.password");

		final String command = config.getOptionalPropertyValue("clearcheckbook.command");

		final int consumers = Integer
		        .valueOf(config.getMandatoryPropertyValue("clearcheckbook.concurrent.connections"));
		final ClearCheckBookCLI cli = new ClearCheckBookCLI(userName, password, consumers);
		if (null == command) {
			// There is an option to quit, so iterate till then
			while (true) {
				cli.getMenu();
			}
		}

	}

	/**
	 * Instantiates a new clear check book cli.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param consumers
	 *            the consumers
	 */
	public ClearCheckBookCLI(final String userName, final String password, final int consumers) {
		this.helper = new ClearCheckBookHelper(userName, password, consumers);
		System.out.println("Clearcheckbook Tools - connecting as " + userName);
	}

	/**
	 * Choose account.
	 *
	 * @param zeroOption
	 *            the zero option
	 * @return the account data type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private AccountDataType chooseAccount(final String zeroOption) throws ClearcheckbookException {
		final List<AccountDataType> accounts = this.fetchAccounts();
		AccountDataType account = null;
		for (int i = 0; i < accounts.size(); i++) {
			final AccountDataType accountDataType = accounts.get(i);
			System.out.println((i + 1) + " " + accountDataType.getId() + " "
			        + accountDataType.getName() + " " + accountDataType.getCurrentBalance());
		}
		final int option = this.getIntegerInput(
		        "Choose number of account or select 0 for " + zeroOption + ":", accounts.size());
		if (option > 0) {
			account = accounts.get(option - 1);
		}
		return account;
	}

	/**
	 * Choose file processor.
	 *
	 * @param id
	 *            the id
	 * @return the file pre processor
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private FilePreProcessor chooseFileProcessor(final Long id) throws ClearcheckbookException {
		final String[] processors = new String[] { "Default", AMEXFilePreprocessor.class.getName(),
		        NationwideFilePreprocessor.class.getName() };
		for (int i = 0; i < processors.length; i++) {
			System.out.println((i) + " " + processors[i]);
		}
		final int option = this.getIntegerInput("Choose fileProcessor :", processors.length);
		switch (option) {
			case 1:
				return new AMEXFilePreprocessor(id);
			case 2:
				return new NationwideFilePreprocessor(id);
			default:
				return new TransactionFilePreprocessor(id);
		}
	}

	/**
	 * Export accounts.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void exportAccounts() throws ClearcheckbookException {
		final List<AccountDataType> accounts = this.fetchAccounts();
		final String fileName = this.getFilename(ClearCheckBookCLI.ACCOUNTS_CSV);
		this.helper.exportAccounts(fileName, accounts);
		System.out.println("Saved " + accounts.size() + " accounts to " + fileName);
	}

	/**
	 * Export transactions.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void exportTransactions() throws ClearcheckbookException {
		List<TransactionDataType> transactions;
		final AccountDataType account = this.chooseAccount("all transactions");

		if (null == account) {
			System.out.println("Fetching all transactions...");
			transactions = this.helper.getTransactions();
		}
		else {
			System.out.println("Fetching transactions for " + account.getName() + "...");
			transactions = this.helper.getTransactions(account);
		}
		System.out.println("Fetched " + transactions.size() + " transactions");
		if (transactions.size() > 0) {
			final String fileName = this.getFilename(this.getDefaultFileName(account));
			System.out.println("Exporting " + transactions.size() + " transactions to file : "
			        + fileName + "...");
			this.helper.exportTransactions(fileName, transactions);
			System.out.println("...done.");
		}
		else {
			System.out.println("Nothing to save.");
		}
	}

	/**
	 * Fetch accounts.
	 *
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private List<AccountDataType> fetchAccounts() throws ClearcheckbookException {
		System.out.println("Fetching account list...");
		final List<AccountDataType> accounts = this.helper.getAccounts();
		return accounts;
	}

	private void getAccountsMenu() throws ClearcheckbookException {
		final String[] options = new String[] { "List", "Export", "Import" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		final int option = this.getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
			case 0:
				this.listAccounts();
				break;
			case 1:
				this.exportAccounts();
				break;
			case 2:
				this.importAccounts();
				break;
			default:
				throw new ClearcheckbookException("Invalid option: " + option);
		}
	}

	/**
	 * Gets the default file name.
	 *
	 * @param account
	 *            the account
	 * @return the default file name
	 */
	private String getDefaultFileName(final AccountDataType account) {
		String defaultFilename;
		if (null == account) {
			defaultFilename = ClearCheckBookCLI.ALL_TRANSACTIONS_CSV;
		}
		else {
			defaultFilename = "account_" + account.getId() + ".csv";
		}
		return defaultFilename;
	}

	/**
	 * Gets the filename.
	 *
	 * @param defaultFilename
	 *            the default filename
	 * @return the filename
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private String getFilename(final String defaultFilename) throws ClearcheckbookException {
		String fileName = this
		        .getStringInput("Enter file name [enter for " + defaultFilename + "]:");
		if (fileName.trim().equals("")) {
			fileName = defaultFilename;
		}
		return fileName;
	}

	/**
	 * Gets the integer input.
	 *
	 * @param question
	 *            the question
	 * @param maxNumber
	 *            the max number
	 * @return the integer input
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private int getIntegerInput(final String question, final int maxNumber)
	        throws ClearcheckbookException {
		System.out.print(question);

		try {
			final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			final int i = Integer.parseInt(br.readLine());
			if ((i > maxNumber) || (i < 0)) {
				System.err.println("Choose number between 0 and " + maxNumber);
				return this.getIntegerInput(question, maxNumber);
			}
			return i;
		}
		catch (final NumberFormatException nfe) {
			System.err.println("Invalid Format!");
			return this.getIntegerInput(question, maxNumber);
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to get option", e);
		}
	}

	private void getMenu() throws ClearcheckbookException {
		final String[] options = new String[] { "Accounts", "Transaction", "Quit" };
		System.out.println("MAIN MENU");
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		final int option = this.getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
			case 0:
				this.getAccountsMenu();
				break;
			case 1:
				this.getTransactionsMenu();
				break;
			default:
				System.out.println("Exiting");
				System.exit(0);
		}
	}

	/**
	 * Gets the string input.
	 *
	 * @param question
	 *            the question
	 * @return the string input
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private String getStringInput(final String question) throws ClearcheckbookException {
		System.out.print(question);
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to get input", e);
		}

	}

	private void getTransactionsMenu() throws ClearcheckbookException {
		final String[] options = new String[] { "List", "Export", "Import" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		final int option = this.getIntegerInput("Choose option:", options.length - 1);
		switch (option) {
			case 0:
				this.listTransactions();
				break;
			case 1:
				this.exportTransactions();
				break;
			case 2:
				this.importTransactions();
				break;
			default:
				throw new ClearcheckbookException("Invalid option: " + option);
		}
	}

	/**
	 * Import accounts.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void importAccounts() throws ClearcheckbookException {
		final String fileName = this.getFilename(ClearCheckBookCLI.ACCOUNTS_CSV);
		final List<AccountDataType> accounts = this.helper.importAccounts(fileName);
		this.helper.processAccounts(accounts);
		System.out.println("Imported " + accounts.size() + " accounts");
	}

	/**
	 * Import transactions.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void importTransactions() throws ClearcheckbookException {
		System.out.println("Choose which account to import to");
		final AccountDataType account = this.chooseAccount("no account specified");
		Long id = 0L;
		if (null != account) {
			id = account.getId();
		}
		final String fileName = this.getFilename(this.getDefaultFileName(account));

		final FilePreProcessor preprocessor = this.chooseFileProcessor(id);

		final List<TransactionDataType> transactions = this.helper.importTransactions(fileName,
		        preprocessor);
		this.helper.processTransactionsInParallel(transactions);
		System.out.println("Imported " + transactions.size() + " transactions");
	}

	/**
	 * List accounts.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void listAccounts() throws ClearcheckbookException {
		final List<AccountDataType> accounts = this.fetchAccounts();
		for (final AccountDataType accountDataType : accounts) {
			System.out.println(accountDataType.getId() + " : " + accountDataType.getName() + " "
			        + accountDataType.getCurrentBalance());
		}
	}

	/**
	 * List transactions.
	 *
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private void listTransactions() throws ClearcheckbookException {
		List<TransactionDataType> transactions;
		final AccountDataType account = this.chooseAccount("all transactions");

		if (null == account) {
			System.out.println("Fetching all transactions...");
			transactions = this.helper.getTransactions();
		}
		else {
			System.out.println("Fetching transactions for " + account.getName() + "...");
			transactions = this.helper.getTransactions(account);
		}
		System.out.println("Fetched " + transactions.size() + " transactions");
		for (final TransactionDataType transactionDataType : transactions) {
			System.out.println(transactionDataType.getDate() + " " + transactionDataType.getAmount()
			        + " " + transactionDataType.getPayee() + " " + transactionDataType.getMemo());
		}

	}

}
