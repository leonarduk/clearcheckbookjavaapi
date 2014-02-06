package com.leonarduk.clearcheckbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.Header;
import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
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

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookCLI.class);
	private ClearCheckBookHelper helper;

	public ClearCheckBookCLI(String userName, String password) {
		helper = new ClearCheckBookHelper(userName, password);
		System.out.println("Clearcheckbook Tools - connecting as " + userName);
	}

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

	private void listAccounts() throws ClearcheckbookException {
		List<AccountDataType> accounts = helper.getAccounts();
		for (Iterator<AccountDataType> iterator = accounts.iterator(); iterator
				.hasNext();) {
			AccountDataType accountDataType = iterator.next();
			System.out.println(accountDataType.getId() + " : "
					+ accountDataType.getName() + " "
					+ accountDataType.getCurrentBalance());
		}
	}

	private void getMenu() throws ClearcheckbookException {
		String[] options = new String[] { "Accounts", "Transaction", "Quit" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		int option;
		try {
			option = getIntegerInput("Choose option:", options.length - 1);
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
		} catch (IOException e) {
			throw new ClearcheckbookException("Invalid option: ", e);
		}
	}

	private static void getTransactionsMenu() {
		// TODO Auto-generated method stub

	}

	private void getAccountsMenu() throws ClearcheckbookException {
		String[] options = new String[] { "List", "Export" };
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + " " + options[i]);
		}

		int option;
		try {
			option = getIntegerInput("Choose option:", options.length - 1);
			switch (option) {
			case 0:
				listAccounts();
				break;
			case 1:
				exportAccounts();
				break;
			default:
				throw new ClearcheckbookException("Invalid option: " + option);
			}
		} catch (IOException e) {
			throw new ClearcheckbookException("Invalid option: ", e);
		}
	}

	private void exportAccounts() {
		// TODO Auto-generated method stub

	}

	private int getIntegerInput(String question, int maxNumber)
			throws IOException {
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
		}
	}

	private Map<Long, AccountDataType> accountsMap = null;

}
