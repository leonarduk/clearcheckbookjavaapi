/**
 * ClearCheckBookFileHandlerTest
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.file.ClearCheckBookFileHandler;
import com.leonarduk.clearcheckbook.file.FilePreProcessor;
import com.leonarduk.clearcheckbook.file.NationwideFilePreprocessor;
import com.leonarduk.clearcheckbook.file.TransactionFilePreprocessor;
import com.leonarduk.utils.DateUtils;

/**
 * The Class ClearCheckBookFileHandlerTest.
 */
public class ClearCheckBookFileHandlerTest {

	/** The Constant datadir. */
	public static final String datadir = "src/main/resources/";

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ClearCheckBookFileHandlerTest.class);

	/** The test categoriesfile name. */
	private final String testCategoriesfileName = ClearCheckBookFileHandlerTest.datadir
	        + "filehandler_categories.csv";

	/** The test transaction file. */
	private final String testTransactionFile = ClearCheckBookFileHandlerTest.datadir
	        + "filehandler_transactions.csv";

	/** The test mini transaction file. */
	private final String testMiniTransactionFile = ClearCheckBookFileHandlerTest.datadir
	        + "filehandler_transactions_mini.csv";

	/** The test nationwide transaction file. */
	private final String testNationwideTransactionFile = ClearCheckBookFileHandlerTest.datadir
	        + "nationwide.csv";

	/** The file handler. */
	private ClearCheckBookFileHandler fileHandler;

	/**
	 * Creates the test categories.
	 *
	 * @return the category data type[]
	 */
	private CategoryDataType[] createTestCategories() {
		final CategoryDataType[] categories = new CategoryDataType[] {
		        CategoryDataType.create(Long.valueOf(1234), "Test", null),
		        CategoryDataType.create(Long.valueOf(1235), "Test2", Long.valueOf(1234)) };
		return categories;
	}

	/**
	 * Creates the test transactions.
	 *
	 * @return the transaction data type[]
	 */
	private TransactionDataType[] createTestTransactions() {
		final String date = DateUtils.getTodaysDateyyyyMMdd();
		final double amount = 10;
		final double amount2 = -1000;
		final long accountId = 101;
		final long categoryId = 123;
		final String description = "deposit some cash";
		final boolean jive = false;
		final long fromAccountId = 0;
		final long toAccountId = 0;
		final String checkNum = "";
		final String memo = "test";
		final String payee = "Boss";

		final TransactionDataType[] transactions = new TransactionDataType[] {
		        TransactionDataType.create(Long.valueOf(123), date, amount, accountId, categoryId,
		                description, jive, fromAccountId, toAccountId, checkNum, memo, payee),
		        TransactionDataType.create(Long.valueOf(124), date, amount2, accountId, categoryId,
		                description, jive, fromAccountId, toAccountId, checkNum, memo, payee) };

		return transactions;
	}

	/**
	 * Gets the test nationwide transactions.
	 *
	 * @return the test nationwide transactions
	 */
	private TransactionDataType[] getTestNationwideTransactions() {
		final Long accountId = 0L;
		final Long categoryId = null;
		final Long fromAccountId = null;
		final Long toAccountId = null;
		final TransactionDataType[] transactions = new TransactionDataType[] {
		        // "21 Nov 2013","Transfer from","0275/636 848 557","","�120.00","�120.00"
		        TransactionDataType.create("2013-11-21", 120.00, accountId, categoryId,
		                "Transfer from 0275/777 999 888", false, fromAccountId, toAccountId, "",
		                "Balance: 120.0", "Transfer from 0275/777 999 888"),
		        // "25 Nov 2013","Transfer from","07-10-40 54817554 Credit 24 November 2013","",
		        // "�3300.00","�3420.00"
		        TransactionDataType.create("2013-11-25", 3300.00, accountId, categoryId,
		                "Transfer from 11-11-11 12345678 Credit 24 November 2013", false,
		                fromAccountId, toAccountId, "", "Balance: 3420.0",
		                "Transfer from 11-11-11 12345678 Credit 24 November 2013"),
		        // "25 Nov 2013","Transfer from","0275/636 848 557 Credit 24 November 2013","","
		        // �300.00","�3720.00"
		        TransactionDataType.create("2013-11-25", 300.00, accountId, categoryId,
		                "Transfer from 0275/777 999 888 Credit 24 November 2013", false,
		                fromAccountId, toAccountId, "", "Balance: 3720.0",
		                "Transfer from 0275/777 999 888 Credit 24 November 2013") };
		return transactions;

	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		this.fileHandler = new ClearCheckBookFileHandler();
	}

	/**
	 * Test export accounts.
	 */
	@Test
	public void testExportAccounts() {
	}

	/**
	 * Test export categories.
	 */
	@Test
	public void testExportCategories() {
		final CategoryDataType[] categories = this.createTestCategories();
		try {
			final File file = this.fileHandler.exportCategories(this.testCategoriesfileName,
			        Arrays.asList(categories));
			ClearCheckBookFileHandlerTest._logger.info(
			        "Exported " + categories.length + " Categories to " + file.getCanonicalPath());
		}
		catch (ClearcheckbookException | IOException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to export categories", e);
			Assert.fail();
		}
	}

	/**
	 * Test export limits.
	 */
	@Test
	public void testExportLimits() {
	}

	/**
	 * Test export reminders.
	 */
	@Test
	public void testExportReminders() {
	}

	/**
	 * Test export transactions.
	 */
	@Test
	public void testExportTransactions() {
		final TransactionDataType[] transactions = this.createTestTransactions();
		try {
			final File file = this.fileHandler.exportTransactions(this.testTransactionFile,
			        Arrays.asList(transactions));
			ClearCheckBookFileHandlerTest._logger.info("Exported " + transactions.length
			        + " Categories to " + file.getCanonicalPath());
		}
		catch (ClearcheckbookException | IOException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to export transactions", e);
			Assert.fail();
		}
	}

	/**
	 * Test import categories.
	 */
	@Test
	public void testImportCategories() {
		try {
			final List<CategoryDataType> categories = this.fileHandler
			        .importCategories(this.testCategoriesfileName);
			ClearCheckBookFileHandlerTest._logger
			        .info("Read:" + categories.size() + ": " + categories);
			for (final CategoryDataType categoryDataType : categories) {
				System.out.println(categoryDataType);

			}
			final CategoryDataType[] expected = this.createTestCategories();
			Assert.assertEquals(
			        "Wrong number of categories: " + categories.size() + " vs " + expected.length,
			        expected.length, categories.size());
			for (int i = 0; i < expected.length; i++) {
				Assert.assertTrue("does not match what is expected",
				        expected[i].equals(categories.get(i)));
			}
		}
		catch (final ClearcheckbookException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to import ", e);
			Assert.fail();
		}
	}

	/**
	 * Test import mini transactions.
	 */
	@Ignore
	@Test
	public void testImportMiniTransactions() {
		try {
			final List<TransactionDataType> expected = this.fileHandler.importTransactions(
			        this.testTransactionFile, new TransactionFilePreprocessor());
			final List<TransactionDataType> transactions = this.fileHandler.importTransactions(
			        this.testMiniTransactionFile, new TransactionFilePreprocessor());
			ClearCheckBookFileHandlerTest._logger
			        .info("Read:" + transactions.size() + ": " + transactions);
			for (final TransactionDataType transactionDataType : transactions) {
				System.out.println(transactionDataType);

			}
			Assert.assertEquals("Wrong number of transactions: " + transactions.size() + " vs "
			        + expected.size(), expected.size(), transactions.size());
			for (int i = 0; i < expected.size(); i++) {
				Assert.assertTrue("transaction does not match what is expected",
				        expected.get(i).equals(transactions.get(i)));
			}
		}
		catch (final ClearcheckbookException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to import transactions", e);
			Assert.fail();
		}
	}

	/**
	 * Test import nationwide.
	 */
	@Test
	public void testImportNationwide() {
		try {
			final Map<String, String> remapMap = new HashMap<>();
			// "DATE","AMOUNT","DESCRIPTION","CHECK_NUM","MEMO","PAYEE"
			// "Date","Transaction type","Description","Paid out","Paid in","Balance"
			// "21 Nov 2013","Transfer from","0275/636 848 557","","£120.00","£120.00"

			final FilePreProcessor processor = new NationwideFilePreprocessor();
			final List<TransactionDataType> transactions = this.fileHandler
			        .importTransactions(this.testNationwideTransactionFile, processor);
			ClearCheckBookFileHandlerTest._logger
			        .info("Read:" + transactions.size() + ": " + transactions);
			for (final TransactionDataType transactionDataType : transactions) {
				System.out.println(transactionDataType);
			}
			final TransactionDataType[] expected = this.getTestNationwideTransactions();
			Assert.assertEquals("Wrong number of transactions: " + transactions.size() + " vs "
			        + expected.length, expected.length, transactions.size());
			for (int i = 0; i < expected.length; i++) {
				Assert.assertTrue("transaction does not match what is expected",
				        expected[i].equals(transactions.get(i)));
			}
		}
		catch (final ClearcheckbookException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to import transactions", e);
			Assert.fail();
		}
	}

	/**
	 * Test import transactions.
	 */
	@Test
	@Ignore
	public void testImportTransactions() {
		try {
			final List<TransactionDataType> transactions = this.fileHandler.importTransactions(
			        this.testTransactionFile, new TransactionFilePreprocessor());
			ClearCheckBookFileHandlerTest._logger
			        .info("Read:" + transactions.size() + ": " + transactions);
			for (final TransactionDataType transactionDataType : transactions) {
				System.out.println(transactionDataType);

			}
			final TransactionDataType[] expected = this.createTestTransactions();
			Assert.assertEquals("Wrong number of transactions: " + transactions.size() + " vs "
			        + expected.length, expected.length, transactions.size());
			for (int i = 0; i < expected.length; i++) {
				Assert.assertTrue("transaction does not match what is expected",
				        expected[i].equals(transactions.get(i)));
			}
		}
		catch (final ClearcheckbookException e) {
			ClearCheckBookFileHandlerTest._logger.fatal("Failed to import transactions", e);
			Assert.fail();
		}
	}

}
