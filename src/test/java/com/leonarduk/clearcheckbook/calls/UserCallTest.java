package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearCheckBookConnectionTest;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.UserDataType;
import com.leonarduk.utils.DateUtils;

public class UserCallTest {

	private static final Logger _logger = Logger.getLogger(UserCallTest.class);
	private UserCall call;

	private String username;

	@Before
	public void setUp() throws Exception {
		this.call = new UserCall(
				ClearCheckBookConnectionTest.getTestConnection());
	}

	@Test
	public void get() {
		UserDataType user;
		try {
			user = this.call.get();
			_logger.info("get:" + user);
			assertTrue(username + " vs " + user.getUsername(),
					username.equals(user.getUsername()));
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to getuser", e);
			fail();
		}
	}

	@Test
	public void insert() {
		String username = "leonarduk_11" + DateUtils.getNowyyyyMMddHHmm();
		String email = "cctest_" + username + "@leonarduk.com";
		UserDataType userDataType = UserDataType.create(username, email,
				username);
		try {
			this.call.insert(userDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to create user", e);
			fail("Failed to create user");
		}

		// Try to create again - should fail
		try {
			this.call.insert(userDataType);
			fail("Created same user twice");
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to create user", e);
		}

	}

}
