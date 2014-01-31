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
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.LimitDataType.Duration;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

public class LimitCallTest {

	private static final Logger _logger = Logger.getLogger(LimitCallTest.class);
	private LimitCall call;

	@Before
	public void setUp() throws Exception {
		String username = "unittest_luk";
		String password = "unittest_luk";
		this.call = new LimitCall(username, password);

	}

	@Test
	public void testGetAll() {
		List<LimitDataType> limits;
		try {
			limits = this.call.getAll();
			if (limits.size() < 1) {
				fail("Need to create a limit on the website to do this test");
			}

			_logger.info(limits.size() + " limit(s) : " + limits);
		} catch (ClearcheckbookException e) {
			_logger.error("Failed to getAll", e);
			fail();
		}
	}

	@Test
	public void testGet() {
		try {
			List<LimitDataType> limits = this.call.getAll();
			if (limits.size() < 1) {
				fail("Need to create a limit on the website to do this test");
			}

			ParsedNameValuePair idParameter = limits.get(0).getIdParameter();
			LimitDataType limit = this.call.get(idParameter);
			_logger.info("get: " + limit);
			assertEquals(idParameter.getValue(), limit.getIdParameter()
					.getValue());
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Limits list needed to get id", e);
			fail();
		}

		ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		LimitDataType limit;
		try {
			limit = this.call.get(idParameter);
			_logger.error("Should not get back " + limit);
			fail("Should not get this back");
		} catch (ClearcheckbookException e) {
		}
	}

	@Test
	public void testInsertLimitDataType() {

		LimitDataType original;
		try {
			List<LimitDataType> all = this.call.getAll();
			if (all.size() < 1) {
				fail("Need to create a limit on the website to do this test");
			}
			original = all.get(0);

			long accountId = original.getAccountId();
			long categoryId = original.getCategoryId();
			int amount = 342;
			Duration duration = original.getDuration();
			int reset_day = original.getReset_day();
			String start_date = original.getStart_date();
			Boolean rollover = original.getRollover();
			Boolean transfer = original.getTransfer();
			Boolean deposit = original.getDeposit();

			LimitDataType input = LimitDataType.create(accountId, categoryId,
					amount, duration, reset_day, start_date, rollover,
					transfer, deposit);
			String id = this.call.insert(input);
			_logger.info("inserted " + id + ":" + input);
		} catch (ClearcheckbookException e) {
			_logger.fatal("failed to create limit", e);
			fail("Failed to create limit");
		}
	}

	@Test
	public void testEditLimitDataType() {
		try {
			List<LimitDataType> limits = this.call.getAll();
			if (limits.size() < 1) {
				fail("Need to create a limit on the website to do this test");
			}

			LimitDataType limit = limits.get(1);
			Integer oldAmount = limit.getAmount();
			limit.setAmount(100);
			_logger.info("Going to edit '" + oldAmount + "' to " + limit);
			boolean edited = this.call.edit(limit);
			assertTrue("Failed to edit limit " + limit, edited);
			_logger.info("Edited " + limit);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Limits list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testDeleteLimitDataType() {
		try {
			List<LimitDataType> limits = this.call.getAll();
			if (limits.size() < 1) {
				fail("Need to create a limit on the website to do this test");
			}

			LimitDataType limitDataType = limits.get(limits.size() - 1);
			ParsedNameValuePair idParameter = limitDataType.getIdParameter();
			boolean deleted = this.call.delete(idParameter);
			assertTrue("Failed to delete limit " + limitDataType, deleted);
			_logger.info("Deleted " + limitDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Limits list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testLimitDataType() {

		// TODO test all the getters and setters
	}

}
