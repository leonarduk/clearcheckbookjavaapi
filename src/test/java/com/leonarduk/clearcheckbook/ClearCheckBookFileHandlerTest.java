package com.leonarduk.clearcheckbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.file.ClearCheckBookFileHandler;
import com.leonarduk.clearcheckbook.file.FilePreProcessor;
import com.leonarduk.clearcheckbook.file.NationwideFilePreprocessor;
import com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor;
import com.leonarduk.utils.DateUtils;

public class ClearCheckBookFileHandlerTest {

	public static final String datadir = "src/main/resources/";
	private final String testCategoriesfileName = datadir
			+ "filehandler_categories.csv";
	private final String testTransactionFile = datadir
			+ "filehandler_transactions.csv";
	private final String testMiniTransactionFile = datadir
			+ "filehandler_transactions_mini.csv";
	private final String testNationwideTransactionFile = datadir
			+ "nationwide.csv";

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookFileHandlerTest.class);
	private ClearCheckBookFileHandler fileHandler;

	@Before
	public void setUp() throws Exception {
		fileHandler = new ClearCheckBookFileHandler();
	}

	@Test
	public void testExportCategories() {
		CategoryDataType[] categories = createTestCategories();
		try {
			File file = this.fileHandler.exportCategories(
					testCategoriesfileName, Arrays.asList(categories));
			_logger.info("Exported " + categories.length + " Categories to "
					+ file.getCanonicalPath());
		} catch (ClearcheckbookException | IOException e) {
			_logger.fatal("Failed to export categories", e);
			fail();
		}
	}

	@Test
	public void testExportTransactions() {
		TransactionDataType[] transactions = createTestTransactions();
		try {
			File file = this.fileHandler.exportTransactions(
					testTransactionFile, Arrays.asList(transactions));
			_logger.info("Exported " + transactions.length + " Categories to "
					+ file.getCanonicalPath());
		} catch (ClearcheckbookException | IOException e) {
			_logger.fatal("Failed to export transactions", e);
			fail();
		}
	}

	@Test
	public void testImportCategories() {
		try {
			List<CategoryDataType> categories = this.fileHandler
					.importCategories(testCategoriesfileName);
			_logger.info("Read:" + categories.size() + ": " + categories);
			for (Iterator<CategoryDataType> iterator = categories.iterator(); iterator
					.hasNext();) {
				System.out.println(iterator.next());

			}
			CategoryDataType[] expected = createTestCategories();
			assertEquals("Wrong number of categories: " + categories.size()
					+ " vs " + expected.length, expected.length,
					categories.size());
			for (int i = 0; i < expected.length; i++) {
				assertTrue("does not match what is expected",
						expected[i].equals(categories.get(i)));
			}
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to import ", e);
			fail();
		}
	}

	@Test
	public void testImportTransactions() {
		try {
			List<TransactionDataType> transactions = this.fileHandler
					.importTransactions(testTransactionFile,
							new TransactionFilePreprocessor());
			_logger.info("Read:" + transactions.size() + ": " + transactions);
			for (Iterator<TransactionDataType> iterator = transactions
					.iterator(); iterator.hasNext();) {
				System.out.println(iterator.next());

			}
			TransactionDataType[] expected = createTestTransactions();
			assertEquals("Wrong number of transactions: " + transactions.size()
					+ " vs " + expected.length, expected.length,
					transactions.size());
			for (int i = 0; i < expected.length; i++) {
				assertTrue("transaction does not match what is expected",
						expected[i].equals(transactions.get(i)));
			}
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to import transactions", e);
			fail();
		}
	}

	@Test
	public void testImportNationwide() {
		try {
			Map<String, String> remapMap = new HashMap<>();
			// "DATE","AMOUNT","DESCRIPTION","CHECK_NUM","MEMO","PAYEE"
			// "Date","Transaction type","Description","Paid out","Paid in","Balance"
			// "21 Nov 2013","Transfer from","0275/636 848 557","","£120.00","£120.00"

			FilePreProcessor processor = new NationwideFilePreprocessor();
			List<TransactionDataType> transactions = this.fileHandler
					.importTransactions(testNationwideTransactionFile,
							processor);
			_logger.info("Read:" + transactions.size() + ": " + transactions);
			for (Iterator<TransactionDataType> iterator = transactions
					.iterator(); iterator.hasNext();) {
				System.out.println(iterator.next());

			}
			TransactionDataType[] expected = createTestTransactions();
			assertEquals("Wrong number of transactions: " + transactions.size()
					+ " vs " + expected.length, expected.length,
					transactions.size());
			for (int i = 0; i < expected.length; i++) {
				assertTrue("transaction does not match what is expected",
						expected[i].equals(transactions.get(i)));
			}
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to import transactions", e);
			fail();
		}
	}

	@Test
	public void testImportMiniTransactions() {
		try {
			List<TransactionDataType> expected = this.fileHandler
					.importTransactions(testTransactionFile,
							new TransactionFilePreprocessor());
			List<TransactionDataType> transactions = this.fileHandler
					.importTransactions(testMiniTransactionFile,
							new TransactionFilePreprocessor());
			_logger.info("Read:" + transactions.size() + ": " + transactions);
			for (Iterator<TransactionDataType> iterator = transactions
					.iterator(); iterator.hasNext();) {
				System.out.println(iterator.next());

			}
			assertEquals("Wrong number of transactions: " + transactions.size()
					+ " vs " + expected.size(), expected.size(),
					transactions.size());
			for (int i = 0; i < expected.size(); i++) {
				assertTrue("transaction does not match what is expected",
						expected.get(i).equals(transactions.get(i)));
			}
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to import transactions", e);
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

	private CategoryDataType[] createTestCategories() {
		CategoryDataType[] categories = new CategoryDataType[] {
				CategoryDataType.create(Long.valueOf(1234), "Test", null),
				CategoryDataType.create(Long.valueOf(1235), "Test2",
						Long.valueOf(1234)) };
		return categories;
	}

	private TransactionDataType[] createTestTransactions() {
		String date = DateUtils.getTodaysDateyyyyMMdd();
		double amount = 10;
		double amount2 = -1000;
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
				TransactionDataType.create(Long.valueOf(123), date, amount,
						accountId, categoryId, description, jive,
						fromAccountId, toAccountId, checkNum, memo, payee),
				TransactionDataType.create(Long.valueOf(124), date, amount2,
						accountId, categoryId, description, jive,
						fromAccountId, toAccountId, checkNum, memo, payee) };

		return transactions;
	}

}
