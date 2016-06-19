/**
 * ClearCheckBookConnectionTest
 * 
 * @author ${author}
 * @since 19-Jun-2016
 */
package com.leonarduk.clearcheckbook;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.CategoryDataType;

public class ClearCheckBookConnectionIT {
	public static String userName = "unittest_luk";

	private static final Logger _logger = Logger.getLogger(ClearCheckBookConnectionIT.class);

	private ClearCheckBookConnection connection;

	public static ClearCheckBookConnection getTestConnection() {
		final String password = "unittest_luk";
		return new ClearCheckBookConnection(ClearCheckBookConnectionIT.userName, password);

	}

	@Before
	public void setUp() throws Exception {
		this.connection = ClearCheckBookConnectionIT.getTestConnection();
	}

	@Test
	public void test() {
		try {
			final List<CategoryDataType> categoryDataType = this.connection.category().getAll();
			ClearCheckBookConnectionIT._logger.info(categoryDataType);

		}
		catch (final ClearcheckbookException e) {
			Assert.fail("Failed to get categories");
		}
	}

}
