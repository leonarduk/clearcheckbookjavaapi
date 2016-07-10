/**
 * AccountCallIT
 *
 * @author ${author}
 * @since 10-Jul-2016
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
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType.Type;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.DateUtils;

public class AccountCallIT {

	private static final Logger	_logger	= Logger.getLogger(AccountCallIT.class);
	private AccountCall			call;

	@Before
	public void setUp() throws Exception {
		this.call = new AccountCall(ClearCheckBookConnectionIT.getTestConnection());

	}

	@Test
	public void testAccountDataType() {
		final String name = "Insert Test" + DateUtils.getNowyyyyMMddHHmm();
		final Type type_id = AccountDataType.Type.CHECKING;
		final double balanceString = 90;

		final AccountDataType input = AccountDataType.create(name, type_id, balanceString);

		input.getDeposit();
		Assert.assertTrue("Name not set", name.equals(input.getName()));

		final String newName = "new";
		input.setName(newName);
		Assert.assertTrue("Name not set: " + newName + " vs " + input.getName(),
		        newName.equals(input.getName()));

		final Type newtypeid = Type.UNKNOWN;
		input.setTypeId(newtypeid);
		try {
			Assert.assertTrue("Type not set", newtypeid.equals(input.getTypeId()));
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.error(e);
			Assert.fail();
		}

		// TODO test all the getters and setters
	}

	@Test
	public void testDeleteAccountDataType() {
		try {
			final List<AccountDataType> accounts = this.call.getAll();
			final AccountDataType accountDataType = accounts.get(accounts.size() - 1);
			final ParsedNameValuePair idParameter = accountDataType.getIdParameter();
			final boolean deleted = this.call.delete(idParameter);
			Assert.assertTrue("Failed to delete account " + accountDataType, deleted);
			AccountCallIT._logger.info("Deleted " + accountDataType);
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.fatal("Failed to get Accounts list needed to get id", e);
			Assert.fail();
		}
	}

	@Test
	public void testEditAccountDataType() {
		try {
			final List<AccountDataType> accounts = this.call.getAll();
			final AccountDataType account = accounts.get(1);
			final String oldName = account.getName();
			account.setName("EditTest" + DateUtils.getNowyyyyMMddHHmm());
			AccountCallIT._logger.info("Going to edit '" + oldName + "' to " + account);
			final boolean edited = this.call.edit(account);
			Assert.assertTrue("Failed to edit account " + account, edited);
			AccountCallIT._logger.info("Edited " + account);
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.fatal("Failed to get Accounts list needed to get id", e);
			Assert.fail();
		}
	}

	@Test
	public void testGet() {
		try {
			final List<AccountDataType> accounts = this.call.getAll();
			final AccountDataType accountDataType = accounts.get(0);

			AccountCallIT._logger.info(accountDataType.getName() + " Current balance: "
			        + accountDataType.getCurrentBalance());
			final ParsedNameValuePair idParameter = accountDataType.getIdParameter();
			final AccountDataType account = this.call.get(idParameter);
			AccountCallIT._logger.info("get: " + account);
			Assert.assertEquals(idParameter.getValue(), account.getIdParameter().getValue());

			Assert.assertEquals(accountDataType.getId(),
			        this.call.get(accountDataType.getName()).getId());
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.fatal("Failed to get Accounts list needed to get id", e);
			Assert.fail();
		}

		final ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		AccountDataType account;
		try {
			account = this.call.get(idParameter);
			AccountCallIT._logger.error("Should not get back " + account);
			Assert.fail("Should not get this back");
		}
		catch (final ClearcheckbookException e) {
		}
	}

	@Test
	public void testGetAll() {
		List<AccountDataType> accounts;
		try {
			accounts = this.call.getAll();
			AccountCallIT._logger.info(accounts.size() + " account(s) : " + accounts);
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.error("Failed to getAll", e);
			Assert.fail();
		}
	}

	@Test
	public void testInsertAccountDataType() {
		final String name = "Insert Test" + DateUtils.getNowyyyyMMddHHmm();
		final Type type_id = AccountDataType.Type.CHECKING;
		final double balanceString = 90;
		final AccountDataType input = AccountDataType.create(name, type_id, balanceString);
		try {
			final String id = this.call.insert(input);
			AccountCallIT._logger.info("inserted " + id + ":" + input);
		}
		catch (final ClearcheckbookException e) {
			AccountCallIT._logger.fatal("failed to create account", e);
			Assert.fail("Failed to create account " + name);
		}
	}
}
