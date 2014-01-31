package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType.Type;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.DateUtils;

public class AccountCallTest {

	private static final Logger _logger = Logger
			.getLogger(AccountCallTest.class);
	private AccountCall call;

	@Before
	public void setUp() throws Exception {
		String username = "unittest_luk";
		String password = "unittest_luk";
		this.call = new AccountCall(username, password);

	}

	@Test
	public void testGetAll() {
		List<AccountDataType> accounts;
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
			List<AccountDataType> accounts = this.call.getAll();
			ParsedNameValuePair idParameter = accounts.get(0).getIdParameter();
			AccountDataType account = this.call.get(idParameter);
			_logger.info("get: " + account);
			assertEquals(idParameter.getValue(), account.getIdParameter()
					.getValue());
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Accounts list needed to get id", e);
			fail();
		}

		ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		AccountDataType account;
		try {
			account = this.call.get(idParameter);
			_logger.error("Should not get back " + account);
			fail("Should not get this back");
		} catch (ClearcheckbookException e) {
		}
	}

	@Test
	public void testInsertAccountDataType() {
		String name = "Insert Test" + DateUtils.getNowyyyyMMddHHmm();
		Type type_id = AccountDataType.Type.CHECKING;
		double balanceString = 90;
		AccountDataType input = AccountDataType.create(name, type_id,
				balanceString);
		try {
			String id = this.call.insert(input);
			_logger.info("inserted " + id + ":" + input);
		} catch (ClearcheckbookException e) {
			_logger.fatal("failed to create account", e);
			fail("Failed to create account " + name);
		}
	}

	@Test
	public void testEditAccountDataType() {
		try {
			List<AccountDataType> accounts = this.call.getAll();
			AccountDataType account = accounts.get(1);
			String oldName = account.getName();
			account.setName("EditTest" + DateUtils.getNowyyyyMMddHHmm());
			_logger.info("Going to edit '" + oldName + "' to " + account);
			boolean edited = this.call.edit(account);
			assertTrue("Failed to edit account " + account, edited);
			_logger.info("Edited " + account);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Accounts list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testDeleteAccountDataType() {
		try {
			List<AccountDataType> accounts = this.call.getAll();
			AccountDataType accountDataType = accounts.get(accounts.size() - 1);
			ParsedNameValuePair idParameter = accountDataType.getIdParameter();
			boolean deleted = this.call.delete(idParameter);
			assertTrue("Failed to delete account " + accountDataType, deleted);
			_logger.info("Deleted " + accountDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Accounts list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testAccountDataType() {
		String name = "Insert Test" + DateUtils.getNowyyyyMMddHHmm();
		Type type_id = AccountDataType.Type.CHECKING;
		double balanceString = 90;

		AccountDataType input = AccountDataType.create(name, type_id,
				balanceString);

		input.getDeposit();
		assertTrue("Name not set", name.equals(input.getName()));

		String newName = "new";
		input.setName(newName);
		assertTrue("Name not set: " + newName + " vs " + input.getName(),
				newName.equals(input.getName()));

		Type newtypeid = Type.UNKNOWN;
		input.setTypeId(newtypeid);
		try {
			assertTrue("Type not set", newtypeid.equals(input.getTypeId()));
		} catch (ClearcheckbookException e) {
			_logger.error(e);
			fail();
		}

		// TODO test all the getters and setters
	}
}
