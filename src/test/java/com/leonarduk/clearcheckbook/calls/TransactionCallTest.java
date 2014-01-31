package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearCheckBookConnectionTest;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
import com.leonarduk.clearcheckbook.dto.TransactionDataType.Type;
import com.leonarduk.utils.DateUtils;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 30 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class TransactionCallTest {

	private static final Logger _logger = Logger
			.getLogger(TransactionCallTest.class);
	private TransactionCall call;

	@Before
	public void setUp() throws Exception {
		this.call = new TransactionCall(
				ClearCheckBookConnectionTest.getTestConnection());

	}

	@Test
	public void testGetAll() {
		List<TransactionDataType> accounts;
		try {
			accounts = this.call.getAll();
			_logger.info(accounts.size() + " account(s) : " + accounts);
		} catch (ClearcheckbookException e) {
			_logger.error("Failed to getAll", e);
			fail();
		}
	}

	@Test
	public void testGet() {
		try {
			List<TransactionDataType> accounts = this.call.getAll();
			ParsedNameValuePair idParameter = accounts.get(0).getIdParameter();
			TransactionDataType account = this.call.get(idParameter);
			_logger.info("get: " + account);
			assertEquals(idParameter.getValue(), account.getIdParameter()
					.getValue());
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Transactions list needed to get id", e);
			fail();
		}

		ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		TransactionDataType account;
		try {
			account = this.call.get(idParameter);
			_logger.error("Should not get back " + account);
			fail("Should not get this back");
		} catch (ClearcheckbookException e) {
		}
	}

	@Test
	public void testInsertTransactionDataType() {
		TransactionDataType input = null;
		try {
			TransactionDataType original = this.call.getAll().get(0);

			String date = original.getDate();
			Double amount = original.getAmount();
			Type transactionType = original.getTransactionType();
			Long accountId = original.getAccountId();
			Long categoryId = original.getCategoryId();
			String description = original.getDescription();
			Boolean jive = original.getJive();
			Long fromAccountId = original.getFromAccountId();
			Long toAccountId = original.getToAccount();
			String checkNum = original.getCheckNum();
			String memo = original.getMemo();
			String payee = original.getPayee();

			input = TransactionDataType.create(date, amount, transactionType,
					accountId, categoryId, description, jive, fromAccountId,
					toAccountId, checkNum, memo, payee);

			input.setDate(DateUtils.getTodaysDateyyyyMMdd());
			input.setAmount(1000);
			input.setCheckNum("1234");
			input.setMemo("New transaction");
			input.setPayee("Tesco");

			String id = this.call.insert(input);
			_logger.info("inserted " + id + ":" + input);
		} catch (ClearcheckbookException e) {
			_logger.fatal("failed to create transaction ", e);
			fail("Failed to create transaction " + input);
		}
	}

	@Test
	public void testEditTransactionDataType() {
		try {
			List<TransactionDataType> transactionDataTypes = this.call.getAll();
			TransactionDataType transactionDataType = transactionDataTypes
					.get(1);

			transactionDataType.setPayee("EditTest"
					+ DateUtils.getNowyyyyMMddHHmm());
			boolean edited = this.call.edit(transactionDataType);
			assertTrue("Failed to edit account " + transactionDataType, edited);
			_logger.info("Edited " + transactionDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Transactions list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testDeleteTransactionDataType() {
		try {
			List<TransactionDataType> accounts = this.call.getAll();
			TransactionDataType accountDataType = accounts
					.get(accounts.size() - 1);
			ParsedNameValuePair idParameter = accountDataType.getIdParameter();
			boolean deleted = this.call.delete(idParameter);
			assertTrue("Failed to delete account " + accountDataType, deleted);
			_logger.info("Deleted " + accountDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Transactions list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testTransactionDataType() {

		// TODO test all the getters and setters
	}

}
