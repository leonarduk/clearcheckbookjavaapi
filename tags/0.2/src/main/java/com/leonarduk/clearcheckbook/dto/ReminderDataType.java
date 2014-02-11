package com.leonarduk.clearcheckbook.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 28 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class ReminderDataType extends AbstractDataType<ReminderDataType> {

	public enum Fields {
		ID, TITLE, START_DATE, END_DATE, NOTIFY, NOTIFY_TIME, REPEAT, REPEAT_EVERY_NUM, REPEAT_EVERY, FLOATS, FLOATS_EVERY_NUM, FLOATS_EVERY, TRANS_AMOUNT, TRANS_DESCRIPTION, TRANS_TRANSACTION_TYPE, TRANS_ACCOUNT_ID, TRANS_CATEGORY_ID, TRANS_TRANSFERTOACCOUNT, TRANS_CHECK_NUM, TRANS_MEMO, TRANS_PAYEE, START_YEAR, OCCUR_FLOATING, EMAIL, EMAILDAYS, START_MONTH, START_DAY, END_YEAR, END_MONTH, END_DAY, OCCUR_ONCE, OCCUR_REPEATING, TRANS_ACCOUNTFROM, TRANS_ACCOUNTTO, REMINDER_ID, TRANSACTION;
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	public ReminderDataType(Map<String, String> map) {
		super(map);
		setValue(Fields.ID, getId());
		setEnd_Date(getEnd_date());
		setStart_day(getStart_day());
		setStart_month(getStart_month());
		setStart_year(getStart_year());
	}

	private ReminderDataType() {
		super();
	}

	private static final Logger _logger = Logger
			.getLogger(ReminderDataType.class);

	@Override
	protected Enum<?>[] getInsertFields() {
		return new Fields[] { Fields.ID, Fields.TITLE, Fields.START_DATE,
				Fields.END_DATE, Fields.NOTIFY, Fields.NOTIFY_TIME,
				Fields.REPEAT, Fields.REPEAT_EVERY_NUM, Fields.REPEAT_EVERY,
				Fields.FLOATS, Fields.FLOATS_EVERY_NUM, Fields.FLOATS_EVERY,
				Fields.TRANS_AMOUNT, Fields.TRANS_DESCRIPTION,
				Fields.TRANS_TRANSACTION_TYPE, Fields.TRANS_ACCOUNT_ID,
				Fields.TRANS_CATEGORY_ID, Fields.TRANS_TRANSFERTOACCOUNT,
				Fields.TRANS_CHECK_NUM, Fields.TRANS_MEMO, Fields.TRANS_PAYEE };
	}

	public static ReminderDataType create(String title, Boolean email,
			Integer emailDays, Integer start_year, String start_month,
			String start_day, Integer end_year, Integer end_month,
			Integer end_day, Boolean occur_once, Boolean occur_repeating,
			Boolean occur_floating, Integer repeat_every,
			Integer repeat_evey_num, Integer float_every_num,
			Integer float_every, TransactionDataType transactionDataType)
			throws ClearcheckbookException {
		ReminderDataType reminderDataType = new ReminderDataType();
		reminderDataType.setTitle(title);
		reminderDataType.setEmail(email);
		reminderDataType.setEmailDays(emailDays);
		reminderDataType.setStart_date(start_year + "-" + start_month + "-"
				+ start_day);
		reminderDataType
				.setEnd_Date(end_year + "-" + end_month + "-" + end_day);
		reminderDataType.setOccur_once(occur_once);
		reminderDataType.setOccur_repeating(occur_repeating);
		reminderDataType.setOccur_floating(occur_floating);
		reminderDataType.setFloat_every(float_every);
		reminderDataType.setFloat_every_num(float_every_num);
		reminderDataType.setTransactionDataType(transactionDataType);
		_logger.debug("createUserDataType: " + reminderDataType);

		return reminderDataType;
	}

	private void setStart_date(String string) {
		setValue(Fields.START_DATE, string);
	}

	/**
	 * since the getall method uses reminder_id not id, need to fix the data
	 */
	@Override
	public ParsedNameValuePair getIdParameter() {
		String id = getValue(Fields.ID);
		if (id == null) {
			id = getValue(Fields.REMINDER_ID);
		}
		return new ParsedNameValuePair(Fields.ID.name().toLowerCase(), id);
	}

	public String getTitle() {
		return getValue(Fields.TITLE);
	}

	public void setTitle(String title) {
		setValue(Fields.TITLE, title);
	}

	public String getEnd_date() {
		String nonNullValue = getNonNullValue(Fields.END_DATE);
		if (nonNullValue.length() > 10) {
			return nonNullValue.substring(0, 10);
		}
		return nonNullValue;
	}

	public void setEnd_Date(String date) {
		setValue(Fields.END_DATE, date);
	}

	public Boolean getEmail() {
		return getBooleanValue(Fields.EMAIL);
	}

	public void setEmail(Boolean email) {
		setValue(Fields.EMAIL, email);
	}

	public Integer getEmailDays() {
		return getIntegerValue(Fields.EMAILDAYS);

	}

	public void setEmailDays(Integer emailDays) {
		setValue(Fields.EMAILDAYS, emailDays);
	}

	public String getStart_date() {
		return getValue(Fields.START_DATE);
	}

	public Integer getStart_year() {
		String date = getStart_date();
		if (null == date) {
			return null;
		}
		return Integer.valueOf(date.substring(0, 4));
	}

	public void setStart_year(Integer start_year) {
		setValue(Fields.START_YEAR, start_year);
	}

	public String getStart_month() {
		String date = getStart_date();
		if (null == date) {
			return null;
		}
		return date.substring(5, 7);
	}

	public void setStart_month(String string) {
		setValue(Fields.START_MONTH, string);
	}

	public String getStart_day() {
		String date = getStart_date();
		if (null == date) {
			return null;
		}
		return date.substring(8, 10);
	}

	public void setStart_day(String string) {
		setValue(Fields.START_DAY, string);
	}

	public Integer getEnd_year() {
		return getIntegerValue(Fields.END_YEAR);
	}

	public void setEnd_year(Integer end_year) {
		setValue(Fields.END_YEAR, end_year);
	}

	public Integer getEnd_month() {
		return getIntegerValue(Fields.END_MONTH);
	}

	public void setEnd_month(Integer end_month) {
		setValue(Fields.END_MONTH, end_month);
	}

	public Integer getEnd_day() {
		return getIntegerValue(Fields.END_DAY);
	}

	public void setEnd_day(Integer end_day) {
		setValue(Fields.END_DAY, end_day);
	}

	public Boolean isOccur_once() {
		return getBooleanValue(Fields.OCCUR_ONCE);
	}

	public void setOccur_once(Boolean occur_once) {
		setValue(Fields.OCCUR_ONCE, occur_once);
	}

	public Boolean isOccur_repeating() {
		return getBooleanValue(Fields.OCCUR_REPEATING);
	}

	public void setOccur_repeating(Boolean occur_repeating) {
		setValue(Fields.OCCUR_REPEATING, occur_repeating);
	}

	public Boolean isOccur_floating() {
		return getBooleanValue(Fields.OCCUR_FLOATING);
	}

	public void setOccur_floating(Boolean occur_floating) {
		setValue(Fields.OCCUR_FLOATING, occur_floating);
	}

	public Integer getRepeat_every() {
		return getIntegerValue(Fields.REPEAT_EVERY);
	}

	public void setRepeat_every(Integer repeat_every) {
		setValue(Fields.REPEAT_EVERY, repeat_every);
	}

	public Integer getRepeat_evey_num() {
		return getIntegerValue(Fields.REPEAT_EVERY_NUM);
	}

	public void setRepeat_evey_num(Integer repeat_evey_num) {
		setValue(Fields.REPEAT_EVERY_NUM, repeat_evey_num);
	}

	public Integer getFloat_every_num() {
		return getIntegerValue(Fields.FLOATS_EVERY_NUM);
	}

	public void setFloat_every_num(Integer float_every_num) {
		setValue(Fields.FLOATS_EVERY_NUM, float_every_num);
	}

	public Integer getFloat_every() {
		return getIntegerValue(Fields.FLOATS_EVERY);
	}

	public void setFloat_every(Integer float_every) {
		setValue(Fields.FLOATS_EVERY, float_every);
	}

	/**
	 * Converts all the "trans_" fields to a TransactionDataType object
	 * 
	 * @return
	 * @throws ClearcheckbookException
	 */
	public TransactionDataType getTransactionDataType()
			throws ClearcheckbookException {
		Boolean jive = false;
		String date = null;
		TransactionDataType transactionDataType = TransactionDataType.create(
				date, getDoubleValue(Fields.TRANS_AMOUNT),
				getLongValue(Fields.TRANS_ACCOUNT_ID),
				getLongValue(Fields.TRANS_CATEGORY_ID),
				getValue(Fields.TRANS_DESCRIPTION), jive,
				getLongValue(Fields.TRANS_ACCOUNTFROM),
				getLongValue(Fields.TRANS_ACCOUNTTO),
				getValue(Fields.TRANS_CHECK_NUM), getValue(Fields.TRANS_MEMO),
				getValue(Fields.TRANS_PAYEE));

		return transactionDataType;
	}

	/**
	 * Converts the {@link TransactionDataType} object into the fields expected,
	 * ie the ones starting "trans_".
	 * 
	 * @param transactionDataType
	 * @throws ClearcheckbookException
	 */
	public void setTransactionDataType(TransactionDataType transactionDataType)
			throws ClearcheckbookException {
		setValue(Fields.TRANS_AMOUNT, transactionDataType.getAmount());
		setValue(Fields.TRANS_TRANSACTION_TYPE,
				transactionDataType.getTransactionType());
		setValue(Fields.TRANS_ACCOUNT_ID, transactionDataType.getAccountId());
		setValue(Fields.TRANS_CATEGORY_ID, transactionDataType.getCategoryId());
		setValue(Fields.TRANS_DESCRIPTION, transactionDataType.getDescription());
		setValue(Fields.TRANS_ACCOUNTFROM,
				transactionDataType.getFromAccountId());
		setValue(Fields.TRANS_ACCOUNTTO, transactionDataType.getToAccount());
		setValue(Fields.TRANS_CHECK_NUM, transactionDataType.getCheckNum());
		setValue(Fields.TRANS_MEMO, transactionDataType.getMemo());
		setValue(Fields.TRANS_PAYEE, transactionDataType.getPayee());
	}

	public Boolean getOccur_once() {
		return getBooleanValue(Fields.OCCUR_ONCE);
	}

	public Boolean getOccur_repeating() {
		return getBooleanValue(Fields.OCCUR_REPEATING);
	}

	public Boolean getOccur_floating() {
		return getBooleanValue(Fields.OCCUR_FLOATING);
	}

	@Override
	protected Enum<?>[] getEditFields() {
		return new Enum[] { Fields.ID, Fields.TITLE, Fields.EMAIL,
				Fields.EMAILDAYS, Fields.START_YEAR, Fields.START_MONTH,
				Fields.START_DAY, Fields.END_YEAR, Fields.END_MONTH,
				Fields.END_DATE, Fields.OCCUR_ONCE, Fields.OCCUR_REPEATING,
				Fields.OCCUR_FLOATING, Fields.REPEAT_EVERY,
				Fields.REPEAT_EVERY_NUM, Fields.FLOATS_EVERY,
				Fields.FLOATS_EVERY_NUM, Fields.TRANSACTION,
				Fields.TRANS_AMOUNT, Fields.TRANS_DESCRIPTION,
				Fields.TRANS_PAYEE, Fields.TRANS_MEMO, Fields.TRANS_CHECK_NUM,
				Fields.TRANS_ACCOUNT_ID, Fields.TRANS_CATEGORY_ID,
				Fields.TRANS_TRANSACTION_TYPE, Fields.TRANS_ACCOUNTFROM,
				Fields.TRANS_ACCOUNTTO };
	}

	/**
	 * There are some issues with Reminder data: reminder_id is used instead of
	 * id in get all. Email not given by get or getall
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ArrayList<Enum> getFieldsToIgnoreInEqualsMethod() {
		return new ArrayList<Enum>(Arrays.asList(new Enum[] { Fields.EMAIL,
				Fields.REMINDER_ID }));
	}
}
