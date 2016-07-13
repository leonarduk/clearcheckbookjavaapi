/**
 * AbstractDataTypeTest
 *
 * @author ${author}
 * @since 12-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.TransactionDataType.Fields;

public class AbstractDataTypeTest {

	private TransactionDataType dataType;

	@Before
	public void setUp() throws Exception {
		final String date = "2016/03/02";
		final Double amount = 9.7;
		final Long accountId = 123L;
		final Long categoryId = null;
		final String description = "payment";
		final Boolean jive = true;
		final Long fromAccountId = null;
		final Long toAccountId = 5656L;
		final String checkNum = "234";
		final String memo = "memo";
		final String payee = "payer";
		this.dataType = TransactionDataType.create(date, amount, accountId, categoryId, description,
		        jive, fromAccountId, toAccountId, checkNum, memo, payee);
	}

	@Test
	public final void testSetValue() {
		this.dataType.setAmount(123.3);
		Assert.assertEquals("123.30", this.dataType.getValue(Fields.AMOUNT));
	}

}
