package com.leonarduk.clearcheckbook;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;

public class ClearCheckBookClientTest {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookClientTest.class);
	private ClearCheckBookClient client;

	@Before
	public void setUp() throws Exception {
		client = new ClearCheckBookClient();
	}

	@Test
	public void testGetAccounts() {
		try {
			List<AccountDataType> accounts = this.client.getAccounts();
			_logger.info(accounts);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testGetAccounts", e);
			fail();
		}
	}

	@Test
	public void testGetTransactionsAccountDataType() {
		try {
			List<AccountDataType> accounts = this.client.getAccounts();
			List<TransactionDataType> transactionDataTypes = this.client
					.getTransactions(accounts.get(0));
			_logger.info(transactionDataTypes);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testGetTransactionsAccountDataType", e);
			fail();
		}
	}

	@Test
	public void testGetTransactions() {
		try {
			List<TransactionDataType> transactionDataTypes = this.client
					.getTransactions();
			_logger.info(transactionDataTypes);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testGetTransactions", e);
			fail();
		}
	}

	@Test
	public void testGetCategories() {
		try {
			List<CategoryDataType> categoryDataTypes = this.client
					.getCategories();
			_logger.info(categoryDataTypes);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testGetCategories", e);
			fail();
		}
	}

	@Test
	public void testExportAccounts() {
		try {
			List<AccountDataType> accounts = this.client.getAccounts();
			String fileName = "testExportAccounts.csv";
			this.client.exportAccounts(fileName, accounts);
			_logger.info(accounts);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportAccounts", e);
			fail();
		}
	}

	@Test
	public void testExportCategories() {
		try {
			List<CategoryDataType> categoryDataTypes = this.client
					.getCategories();
			String fileName = "testExportCategories.csv";
			this.client.exportCategories(fileName, categoryDataTypes);
			_logger.info(categoryDataTypes);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportCategories", e);
			fail();
		}
	}

	@Test
	public void testExportLimits() {
		try {
			List<LimitDataType> limits = this.client.getLimits();
			String fileName = "testExportLimits.csv";
			this.client.exportLimits(fileName, limits);
			_logger.info(limits);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportLimits", e);
			fail();
		}
	}

	@Test
	public void testExportReminders() {
		try {
			List<ReminderDataType> reminders = this.client.getReminders();
			String fileName = "testExportReminders.csv";
			this.client.exportReminders(fileName, reminders);
			_logger.info(reminders);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportReminders", e);
			fail();
		}
	}

	@Test
	public void testExportTransactions() {
		String fileName = "testExportTransactions";
		try {
			List<TransactionDataType> transactions = this.client
					.getTransactions();
			this.client.exportTransactions(fileName, transactions);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportTransactions", e);
			fail();
		}
	}

}
