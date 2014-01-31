package com.leonarduk.clearcheckbook;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.CategoryDataType;

public class ClearCheckBookConnectionTest {

	private static final Logger _logger = Logger
			.getLogger(ClearCheckBookConnectionTest.class);

	public static ClearCheckBookConnection getTestConnection() {
		String userName = "unittest_luk";
		String password = "unittest_luk";
		return new ClearCheckBookConnection(userName, password);

	}

	private ClearCheckBookConnection connection;

	@Before
	public void setUp() throws Exception {
		connection = getTestConnection();
	}

	@Test
	public void test() {
		try {
			List<CategoryDataType> categoryDataType = this.connection
					.category().getAll();
			_logger.info(categoryDataType);

		} catch (ClearcheckbookException e) {
			fail("Failed to get categories");
		}
	}

}
