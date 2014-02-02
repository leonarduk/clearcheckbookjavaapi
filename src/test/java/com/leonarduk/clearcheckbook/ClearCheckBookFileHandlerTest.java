package com.leonarduk.clearcheckbook;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType.Type;
import com.leonarduk.utils.DateUtils;

public class ClearCheckBookFileHandlerTest {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookFileHandlerTest.class);
	private ClearCheckBookFileHandler fileHandler;

	@Before
	public void setUp() throws Exception {
		fileHandler = new ClearCheckBookFileHandler();
	}

	@Test
	public void testExportCategories() {
		CategoryDataType[] categories = new CategoryDataType[] {
				CategoryDataType.create(Long.valueOf(1234), "Test", null),
				CategoryDataType.create(Long.valueOf(1235), "Test2",
						Long.valueOf(1234)) };
		String fileName = "categories.csv";
		try {
			File file = this.fileHandler.exportCategories(fileName,
					Arrays.asList(categories));
			_logger.info("Exported " + categories.length + " Categories to "
					+ file.getCanonicalPath());
		} catch (ClearcheckbookException | IOException e) {
			_logger.fatal("Failed to export categories", e);
			fail();
		}
	}

	@Test
	public void testExportTransactions() {
		String date = DateUtils.getTodaysDateyyyyMMdd();
		double amount = 10;
		double amount2 = 1000;
		Type transactionType = TransactionDataType.Type.DEPOSIT;
		long accountId = 101;
		long categoryId = 123;
		String description = "deposit some cash";
		boolean jive = false;
		long fromAccountId = 0;
		long toAccountId = 0;
		String checkNum = "";
		String memo = "test";
		String payee = "Boss";

		TransactionDataType[] transactions = new TransactionDataType[] {
				TransactionDataType.create(date, amount, transactionType,
						accountId, categoryId, description, jive,
						fromAccountId, toAccountId, checkNum, memo, payee),
				TransactionDataType.create(date, amount2, transactionType,
						accountId, categoryId, description, jive,
						fromAccountId, toAccountId, checkNum, memo, payee) };
		String fileName = "transactions.csv";
		try {
			File file = this.fileHandler.exportTransactions(fileName,
					Arrays.asList(transactions));
			_logger.info("Exported " + transactions.length + " Categories to "
					+ file.getCanonicalPath());
		} catch (ClearcheckbookException | IOException e) {
			_logger.fatal("Failed to export transactions", e);
			fail();
		}
	}

	@Test
	public void testExportAccounts() {
	}

	@Test
	public void testExportLimits() {
	}

	@Test
	public void testExportReminders() {
	}

}
