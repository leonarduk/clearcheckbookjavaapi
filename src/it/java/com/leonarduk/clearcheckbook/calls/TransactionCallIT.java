/**
 * TransactionCallIT
 *
 * @author ${author}
 * @since 12-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearCheckBookConnectionIT;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;
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
public class TransactionCallIT {

	private static final Logger	_logger	= Logger.getLogger(TransactionCallIT.class);
	private TransactionCall		call;

	@Before
	public void setUp() throws Exception {
		this.call = new TransactionCall(ClearCheckBookConnectionIT.getTestConnection());

	}

	@Test
	public void testDeleteTransactionDataType() {
		try {
			final List<TransactionDataType> accounts = this.call.getAll();
			final TransactionDataType accountDataType = accounts.get(accounts.size() - 1);
			final ParsedNameValuePair idParameter = accountDataType.getIdParameter();
			final boolean deleted = this.call.delete(idParameter);
			Assert.assertTrue("Failed to delete account " + accountDataType, deleted);
			TransactionCallIT._logger.info("Deleted " + accountDataType);
		}
		catch (final ClearcheckbookException e) {
			TransactionCallIT._logger.fatal("Failed to get Transactions list needed to get id", e);
			Assert.fail();
		}
	}

	@Test
	public void testEditTransactionDataType() {
		try {
			final List<TransactionDataType> transactionDataTypes = this.call.getAll();
			final TransactionDataType transactionDataType = transactionDataTypes.get(1);

			transactionDataType.setPayee("EditTest" + DateUtils.getNowyyyyMMddHHmm());
			final boolean edited = this.call.edit(transactionDataType);
			Assert.assertTrue("Failed to edit account " + transactionDataType, edited);
			TransactionCallIT._logger.info("Edited " + transactionDataType);
		}
		catch (final ClearcheckbookException e) {
			TransactionCallIT._logger.fatal("Failed to get Transactions list needed to get id", e);
			Assert.fail();
		}
	}

	@Test
	public void testGet() {
		try {
			final List<TransactionDataType> accounts = this.call.getAll();
			final ParsedNameValuePair idParameter = accounts.get(0).getIdParameter();
			final TransactionDataType account = this.call.get(idParameter);
			TransactionCallIT._logger.info("get: " + account);
			Assert.assertEquals(idParameter.getValue(), account.getIdParameter().getValue());
		}
		catch (final ClearcheckbookException e) {
			TransactionCallIT._logger.fatal("Failed to get Transactions list needed to get id", e);
			Assert.fail();
		}

		final ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		TransactionDataType account;
		try {
			account = this.call.get(idParameter);
			TransactionCallIT._logger.error("Should not get back " + account);
			Assert.fail("Should not get this back");
		}
		catch (final ClearcheckbookException e) {
		}
	}

	@Test
	public void testGetAll() {
		List<TransactionDataType> transactions;
		try {
			transactions = this.call.getAll(0, 10);
			TransactionCallIT._logger.info(transactions.size() + " account(s) : " + transactions);
		}
		catch (final ClearcheckbookException e) {
			TransactionCallIT._logger.error("Failed to getAll", e);
			Assert.fail();
		}
	}

	@Test
	public void testInsertNationwideTransactionDataType() {
		TransactionDataType input = null;
		try {
			final TransactionDataType original = this.call.getAll().get(0);

			/*
			 * AbstractDataType [fieldsMap={account_id=1613073, to_account_id=1613073
			 * transaction_type=0, }]
			 */
			final String date = "2016-07-02 12:00:00";
			final Double amount = 6.85;
			final Long accountId = 2053078L; // original.getAccountId();
			final Long categoryId = 0L;
			final String description = "Visa purchase BOCKETTS FARM SHOP LEATHERHEAD GB";
			final Boolean jive = Boolean.TRUE;
			final Long fromAccountId = 0L;
			final Long toAccountId = 0L;
			final String checkNum = "A4251.39";
			final String memo = "Visa purchase BOCKETTS FARM SHOP LEATHERHEAD GB";
			final String payee = "BOCKETTS FARM SHOP LEATHERHEAD GB";

			input = TransactionDataType.create(date, amount, accountId, categoryId, description,
			        jive, fromAccountId, toAccountId, checkNum, memo, payee);

			final String id = this.call.insert(input);
			TransactionCallIT._logger.info("inserted " + id + ":" + input);
		}
		catch (ClearcheckbookException | NullPointerException e) {
			TransactionCallIT._logger.fatal("failed to create transaction ", e);
			Assert.fail("Failed to create transaction " + input);
		}
	}

	@Test
	public void testInsertTransactionDataType() {
		TransactionDataType input = null;
		try {
			final TransactionDataType original = this.call.getAll().get(0);

			final String date = original.getDate();
			final Double amount = original.getAmount();
			final Long accountId = original.getAccountId();
			final Long categoryId = original.getCategoryId();
			final String description = original.getDescription();
			final Boolean jive = original.getJive();
			final Long fromAccountId = original.getFromAccountId();
			final Long toAccountId = original.getToAccount();
			final String checkNum = original.getCheckNum();
			final String memo = original.getMemo();
			final String payee = original.getPayee();

			input = TransactionDataType.create(date, amount, accountId, categoryId, description,
			        jive, fromAccountId, toAccountId, checkNum, memo, payee);

			input.setDate(DateUtils.getTodaysDateyyyyMMdd());
			input.setAmount(1000.0);
			input.setCheckNum("1234");
			input.setMemo("New transaction");
			input.setPayee("Tesco");

			final String id = this.call.insert(input);
			TransactionCallIT._logger.info("inserted " + id + ":" + input);
		}
		catch (ClearcheckbookException | NullPointerException e) {
			TransactionCallIT._logger.fatal("failed to create transaction ", e);
			Assert.fail("Failed to create transaction " + input);
		}
	}

	@Test
	public void testTransactionDataType() {

		// TODO test all the getters and setters
	}

}
