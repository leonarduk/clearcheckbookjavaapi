package com.leonarduk.clearcheckbook;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.util.Asserts;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.utils.DateUtils;

public class ClearCheckBookClientTest {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookClientTest.class);
	private ClearCheckBookClient client;
	private final String categoriesFileName = "clientCategories.csv";
	private final String accountsFileName = "clientAccounts.csv";
	private final String limitsfileName = "clientLimits.csv";
	private final String remindersFileName = "clientReminders.csv";
	private final String transactionsFileName = "clientTransactions.csv";
	private String transactionsNationwideFileName = "src/main/resources/nationwide.csv";

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
			this.client.exportAccounts(accountsFileName, accounts);
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
			this.client.exportCategories(categoriesFileName, categoryDataTypes);
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
			this.client.exportLimits(limitsfileName, limits);
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
			this.client.exportReminders(remindersFileName, reminders);
			_logger.info(reminders);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportReminders", e);
			fail();
		}
	}

	@Test
	public void testExportTransactions() {
		try {
			List<TransactionDataType> transactions = this.client
					.getTransactions();
			this.client.exportTransactions(transactionsFileName, transactions);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportTransactions", e);
			fail();
		}
	}

	@Test
	public void testImportTransactions() {
		try {
			List<TransactionDataType> expected = this.client.getTransactions();
			List<TransactionDataType> actual = this.client
					.importTransactions(transactionsFileName);
			compareTransactionList(expected, actual);

		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportTransactions", e);
			fail();
		}
	}

	@Test
	public void testBulkUpdate() {
		try {
			List<TransactionDataType> file = this.client
					.importTransactions(transactionsFileName);
			file.get(1).setDescription(
					"updated " + DateUtils.getNowyyyyMMddHHmm());
			this.client.processTransactions(file);
			List<TransactionDataType> after = this.client.getTransactions();
			compareTransactionList(file, after);

		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportTransactions", e);
			fail();
		}
	}

	@Test
	public void testBulkUpdateNationwide() {
		try {
			List<TransactionDataType> file = this.client
					.importTransactions(transactionsNationwideFileName);
			file.get(1).setDescription(
					"updated " + DateUtils.getNowyyyyMMddHHmm());
			this.client.processTransactions(file);
			List<TransactionDataType> after = this.client.getTransactions();
			compareTransactionList(file, after);

		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to testExportTransactions", e);
			fail();
		}
	}

	private void compareTransactionList(List<TransactionDataType> expected,
			List<TransactionDataType> actual) {
		assertSame(expected.size(), actual.size());
		_logger.info("testImportTransactions: " + actual.size() + ": " + actual);
		Map<Long, AbstractDataType<?>> expectedMap = new HashMap<>();
		for (Iterator<TransactionDataType> iterator = expected.iterator(); iterator
				.hasNext();) {
			TransactionDataType next = iterator.next();
			expectedMap.put(next.getId(), next);
		}
		for (Iterator<TransactionDataType> iterator = actual.iterator(); iterator
				.hasNext();) {
			TransactionDataType transactionDataType = iterator.next();
			_logger.debug("comparing " + transactionDataType);
			assertTrue(expectedMap.containsKey(transactionDataType.getId()));
			AbstractDataType<?> expectedValue = expectedMap
					.get(transactionDataType.getId());
			assertTrue(expectedValue.equals(transactionDataType));
		}
	}

}
