package com.leonarduk.clearcheckbook;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class ClearCheckBookConnectionTest {

	public static ClearCheckBookConnection getTestConnection() {
		String userName = "unittest_luk";
		String password = "unittest_luk";
		return new ClearCheckBookConnection(userName, password);

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
