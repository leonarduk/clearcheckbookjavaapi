/**
 * TransactionDataTypeTest
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionDataTypeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testEquals() {
		final String date = "2012/11/23";
		final Double amount = 34345.23;
		final Long accountId = 1L;
		final Long categoryId = null;
		final String description = "payment";
		final Boolean jive = Boolean.TRUE;
		final Long fromAccountId = null;
		final Long toAccountId = accountId;
		final String checkNum = "123";
		final String memo = description;
		final String payee = "Someone";
		final Long id = 123L;
		final TransactionDataType transactionDataType = TransactionDataType.create(date, amount,
		        accountId, categoryId, description, jive, fromAccountId, toAccountId, checkNum,
		        memo, payee);

		final TransactionDataType transactionDataType2 = TransactionDataType.create(date, amount,
		        accountId, categoryId, description, jive, fromAccountId, toAccountId, checkNum,
		        memo, payee);

		Assert.assertEquals(transactionDataType, transactionDataType2);
		final TransactionDataType transactionDataType3 = TransactionDataType.create(date,
		        amount + 210, accountId, categoryId, description, jive, fromAccountId, toAccountId,
		        checkNum, memo, payee);
		Assert.assertNotEquals(transactionDataType, transactionDataType3);
		final TransactionDataType transactionDataType4 = TransactionDataType.create(id, date,
		        amount, accountId, categoryId, description, jive, fromAccountId, toAccountId,
		        checkNum, memo, payee);

		Assert.assertEquals(transactionDataType, transactionDataType4);

	}

}
