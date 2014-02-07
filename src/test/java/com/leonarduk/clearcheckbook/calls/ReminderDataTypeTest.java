package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearCheckBookConnectionTest;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;
import com.leonarduk.utils.DateUtils;

public class ReminderDataTypeTest {

	private static final Logger _logger = Logger
			.getLogger(ReminderDataTypeTest.class);
	private ReminderCall call;

	@Before
	public void setUp() throws Exception {
		this.call = new ReminderCall(
				ClearCheckBookConnectionTest.getTestConnection());

	}

	@Test
	public void testGetAll() {
		List<ReminderDataType> reminders;
		try {
			reminders = this.call.getAll();
			_logger.info(reminders.size() + " reminder(s) : " + reminders);
		} catch (ClearcheckbookException e) {
			_logger.error("Failed to getAll", e);
			fail();
		}
	}

	@Test
	public void testGet() {
		try {
			List<ReminderDataType> reminders = this.call.getAll();
			ParsedNameValuePair idParameter = reminders.get(0).getIdParameter();
			ReminderDataType reminder = this.call.get(idParameter);
			_logger.info("get: " + reminder);
			assertEquals(idParameter.getValue(), reminder.getIdParameter()
					.getValue());
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Reminders list needed to get id", e);
			fail();
		}

		ParsedNameValuePair idParameter = AbstractDataType.getIdParameter(1);
		ReminderDataType reminder;
		try {
			reminder = this.call.get(idParameter);
			_logger.error("Should not get back " + reminder);
			fail("Should not get this back");
		} catch (ClearcheckbookException e) {
		}
	}

	@Test
	public void testInsertReminderDataType() {
		ReminderDataType input = null;
		try {
			ReminderDataType original = this.call.getAll().get(0);

			input = ReminderDataType.create(original.getTitle(), "InsertTest"
					+ DateUtils.getNowyyyyMMddHHmm(), original.getEmailDays(),
					original.getStart_year(), original.getStart_month(),
					original.getStart_day(), original.getEnd_year(),
					original.getEnd_month(), original.getEnd_day(),
					original.getOccur_once(), original.getOccur_repeating(),
					original.getOccur_floating(), original.getRepeat_every(),
					original.getRepeat_evey_num(),
					original.getFloat_every_num(), original.getFloat_every(),
					original.getTransactionDataType());


			String id = this.call.insert(input);
			_logger.info("inserted " + id + ":" + input);
		} catch (ClearcheckbookException e) {
			_logger.fatal("failed to create reminder ", e);
			fail("Failed to create reminder " + input);
		}
	}

	@Test
	public void testEditReminderDataType() {
		try {
			List<ReminderDataType> reminderDataTypes = this.call.getAll();
			ReminderDataType reminderDataType = reminderDataTypes.get(1);

			reminderDataType.setEmail("EditTest"
					+ DateUtils.getNowyyyyMMddHHmm());
			boolean edited = this.call.edit(reminderDataType);
			assertTrue("Failed to edit reminder " + reminderDataType, edited);
			_logger.info("Edited " + reminderDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Reminders list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testDeleteReminderDataType() {
		try {
			List<ReminderDataType> reminders = this.call.getAll();
			ReminderDataType reminderDataType = reminders
					.get(reminders.size() - 1);
			ParsedNameValuePair idParameter = reminderDataType.getIdParameter();
			boolean deleted = this.call.delete(idParameter);
			assertTrue("Failed to delete reminder " + reminderDataType, deleted);
			_logger.info("Deleted " + reminderDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Reminders list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testReminderDataType() {

		// TODO test all the getters and setters
	}

}
