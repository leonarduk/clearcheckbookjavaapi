/**
 * ReminderDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

/**
 * The Class ReminderDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class ReminderDataType extends AbstractDataType<ReminderDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ReminderDataType.class);

	/**
	 * Creates the.
	 *
	 * @param title
	 *            the title
	 * @param email
	 *            the email
	 * @param emailDays
	 *            the email days
	 * @param start_year
	 *            the start_year
	 * @param start_month
	 *            the start_month
	 * @param start_day
	 *            the start_day
	 * @param end_year
	 *            the end_year
	 * @param end_month
	 *            the end_month
	 * @param end_day
	 *            the end_day
	 * @param occur_once
	 *            the occur_once
	 * @param occur_repeating
	 *            the occur_repeating
	 * @param occur_floating
	 *            the occur_floating
	 * @param repeat_every
	 *            the repeat_every
	 * @param repeat_evey_num
	 *            the repeat_evey_num
	 * @param float_every_num
	 *            the float_every_num
	 * @param float_every
	 *            the float_every
	 * @param transactionDataType
	 *            the transaction data type
	 * @return the reminder data type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public static ReminderDataType create(final String title, final Boolean email,
	        final Integer emailDays, final Integer start_year, final String start_month,
	        final String start_day, final Integer end_year, final Integer end_month,
	        final Integer end_day, final Boolean occur_once, final Boolean occur_repeating,
	        final Boolean occur_floating, final Integer repeat_every, final Integer repeat_evey_num,
	        final Integer float_every_num, final Integer float_every,
	        final TransactionDataType transactionDataType) throws ClearcheckbookException {
		final ReminderDataType reminderDataType = new ReminderDataType();
		reminderDataType.setTitle(title);
		reminderDataType.setEmail(email);
		reminderDataType.setEmailDays(emailDays);
		reminderDataType.setStart_date(start_year + "-" + start_month + "-" + start_day);
		reminderDataType.setEnd_Date(end_year + "-" + end_month + "-" + end_day);
		reminderDataType.setOccur_once(occur_once);
		reminderDataType.setOccur_repeating(occur_repeating);
		reminderDataType.setOccur_floating(occur_floating);
		reminderDataType.setFloat_every(float_every);
		reminderDataType.setFloat_every_num(float_every_num);
		reminderDataType.setTransactionDataType(transactionDataType);
		ReminderDataType._logger.debug("createUserDataType: " + reminderDataType);

		return reminderDataType;
	}

	/**
	 * Instantiates a new reminder data type.
	 */
	private ReminderDataType() {
		super();
	}

	/**
	 * Instantiates a new reminder data type.
	 *
	 * @param map
	 *            the map
	 */
	public ReminderDataType(final Map<String, String> map) {
		super(map);
		this.setValue(Fields.ID, this.getId());
		this.setEnd_Date(this.getEnd_date());
		this.setStart_day(this.getStart_day());
		this.setStart_month(this.getStart_month());
		this.setStart_year(this.getStart_year());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getEditFields()
	 */
	@Override
	protected Enum<?>[] getEditFields() {
		return new Enum[] { Fields.ID, Fields.TITLE, Fields.EMAIL, Fields.EMAILDAYS,
		        Fields.START_YEAR, Fields.START_MONTH, Fields.START_DAY, Fields.END_YEAR,
		        Fields.END_MONTH, Fields.END_DATE, Fields.OCCUR_ONCE, Fields.OCCUR_REPEATING,
		        Fields.OCCUR_FLOATING, Fields.REPEAT_EVERY, Fields.REPEAT_EVERY_NUM,
		        Fields.FLOATS_EVERY, Fields.FLOATS_EVERY_NUM, Fields.TRANSACTION,
		        Fields.TRANS_AMOUNT, Fields.TRANS_DESCRIPTION, Fields.TRANS_PAYEE,
		        Fields.TRANS_MEMO, Fields.TRANS_CHECK_NUM, Fields.TRANS_ACCOUNT_ID,
		        Fields.TRANS_CATEGORY_ID, Fields.TRANS_TRANSACTION_TYPE, Fields.TRANS_ACCOUNTFROM,
		        Fields.TRANS_ACCOUNTTO };
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public Boolean getEmail() {
		return this.getBooleanValue(Fields.EMAIL);
	}

	/**
	 * Gets the email days.
	 *
	 * @return the email days
	 */
	public Integer getEmailDays() {
		return this.getIntegerValue(Fields.EMAILDAYS);

	}

	/**
	 * Gets the end_date.
	 *
	 * @return the end_date
	 */
	public String getEnd_date() {
		final String nonNullValue = this.getNonNullValue(Fields.END_DATE);
		if (nonNullValue.length() > 10) {
			return nonNullValue.substring(0, 10);
		}
		return nonNullValue;
	}

	/**
	 * Gets the end_day.
	 *
	 * @return the end_day
	 */
	public Integer getEnd_day() {
		return this.getIntegerValue(Fields.END_DAY);
	}

	/**
	 * Gets the end_month.
	 *
	 * @return the end_month
	 */
	public Integer getEnd_month() {
		return this.getIntegerValue(Fields.END_MONTH);
	}

	/**
	 * Gets the end_year.
	 *
	 * @return the end_year
	 */
	public Integer getEnd_year() {
		return this.getIntegerValue(Fields.END_YEAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getFields()
	 */
	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	/**
	 * There are some issues with Reminder data: reminder_id is used instead of id in get all. Email
	 * not given by get or getall
	 *
	 * @return the fields to ignore in equals method
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ArrayList<Enum> getFieldsToIgnoreInEqualsMethod() {
		return new ArrayList<Enum>(Arrays.asList(new Enum[] { Fields.EMAIL, Fields.REMINDER_ID }));
	}

	/**
	 * Gets the float_every.
	 *
	 * @return the float_every
	 */
	public Integer getFloat_every() {
		return this.getIntegerValue(Fields.FLOATS_EVERY);
	}

	/**
	 * Gets the float_every_num.
	 *
	 * @return the float_every_num
	 */
	public Integer getFloat_every_num() {
		return this.getIntegerValue(Fields.FLOATS_EVERY_NUM);
	}

	/**
	 * since the getall method uses reminder_id not id, need to fix the data.
	 *
	 * @return the id parameter
	 */
	@Override
	public ParsedNameValuePair getIdParameter() {
		String id = this.getValue(Fields.ID);
		if (id == null) {
			id = this.getValue(Fields.REMINDER_ID);
		}
		return new ParsedNameValuePair(Fields.ID.name().toLowerCase(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getInsertFields()
	 */
	@Override
	protected Enum<?>[] getInsertFields() {
		return new Fields[] { Fields.ID, Fields.TITLE, Fields.START_DATE, Fields.END_DATE,
		        Fields.NOTIFY, Fields.NOTIFY_TIME, Fields.REPEAT, Fields.REPEAT_EVERY_NUM,
		        Fields.REPEAT_EVERY, Fields.FLOATS, Fields.FLOATS_EVERY_NUM, Fields.FLOATS_EVERY,
		        Fields.TRANS_AMOUNT, Fields.TRANS_DESCRIPTION, Fields.TRANS_TRANSACTION_TYPE,
		        Fields.TRANS_ACCOUNT_ID, Fields.TRANS_CATEGORY_ID, Fields.TRANS_TRANSFERTOACCOUNT,
		        Fields.TRANS_CHECK_NUM, Fields.TRANS_MEMO, Fields.TRANS_PAYEE };
	}

	/**
	 * Gets the occur_floating.
	 *
	 * @return the occur_floating
	 */
	public Boolean getOccur_floating() {
		return this.getBooleanValue(Fields.OCCUR_FLOATING);
	}

	/**
	 * Gets the occur_once.
	 *
	 * @return the occur_once
	 */
	public Boolean getOccur_once() {
		return this.getBooleanValue(Fields.OCCUR_ONCE);
	}

	/**
	 * Gets the occur_repeating.
	 *
	 * @return the occur_repeating
	 */
	public Boolean getOccur_repeating() {
		return this.getBooleanValue(Fields.OCCUR_REPEATING);
	}

	/**
	 * Gets the repeat_every.
	 *
	 * @return the repeat_every
	 */
	public Integer getRepeat_every() {
		return this.getIntegerValue(Fields.REPEAT_EVERY);
	}

	/**
	 * Gets the repeat_evey_num.
	 *
	 * @return the repeat_evey_num
	 */
	public Integer getRepeat_evey_num() {
		return this.getIntegerValue(Fields.REPEAT_EVERY_NUM);
	}

	/**
	 * Gets the start_date.
	 *
	 * @return the start_date
	 */
	public String getStart_date() {
		return this.getValue(Fields.START_DATE);
	}

	/**
	 * Gets the start_day.
	 *
	 * @return the start_day
	 */
	public String getStart_day() {
		final String date = this.getStart_date();
		if (null == date) {
			return null;
		}
		return date.substring(8, 10);
	}

	/**
	 * Gets the start_month.
	 *
	 * @return the start_month
	 */
	public String getStart_month() {
		final String date = this.getStart_date();
		if (null == date) {
			return null;
		}
		return date.substring(5, 7);
	}

	/**
	 * Gets the start_year.
	 *
	 * @return the start_year
	 */
	public Integer getStart_year() {
		final String date = this.getStart_date();
		if (null == date) {
			return null;
		}
		return Integer.valueOf(date.substring(0, 4));
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.getValue(Fields.TITLE);
	}

	/**
	 * Converts all the "trans_" fields to a TransactionDataType object.
	 *
	 * @return the transaction data type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public TransactionDataType getTransactionDataType() throws ClearcheckbookException {
		final Boolean jive = false;
		final String date = null;
		final TransactionDataType transactionDataType = TransactionDataType.create(date,
		        this.getDoubleValue(Fields.TRANS_AMOUNT),
		        this.getLongValue(Fields.TRANS_ACCOUNT_ID),
		        this.getLongValue(Fields.TRANS_CATEGORY_ID),
		        this.getValue(Fields.TRANS_DESCRIPTION), jive,
		        this.getLongValue(Fields.TRANS_ACCOUNTFROM),
		        this.getLongValue(Fields.TRANS_ACCOUNTTO), this.getValue(Fields.TRANS_CHECK_NUM),
		        this.getValue(Fields.TRANS_MEMO), this.getValue(Fields.TRANS_PAYEE));

		return transactionDataType;
	}

	/**
	 * Checks if is occur_floating.
	 *
	 * @return the boolean
	 */
	public Boolean isOccur_floating() {
		return this.getBooleanValue(Fields.OCCUR_FLOATING);
	}

	/**
	 * Checks if is occur_once.
	 *
	 * @return the boolean
	 */
	public Boolean isOccur_once() {
		return this.getBooleanValue(Fields.OCCUR_ONCE);
	}

	/**
	 * Checks if is occur_repeating.
	 *
	 * @return the boolean
	 */
	public Boolean isOccur_repeating() {
		return this.getBooleanValue(Fields.OCCUR_REPEATING);
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(final Boolean email) {
		this.setValue(Fields.EMAIL, email);
	}

	/**
	 * Sets the email days.
	 *
	 * @param emailDays
	 *            the new email days
	 */
	public void setEmailDays(final Integer emailDays) {
		this.setValue(Fields.EMAILDAYS, emailDays);
	}

	/**
	 * Sets the end_ date.
	 *
	 * @param date
	 *            the new end_ date
	 */
	public void setEnd_Date(final String date) {
		this.setValue(Fields.END_DATE, date);
	}

	/**
	 * Sets the end_day.
	 *
	 * @param end_day
	 *            the new end_day
	 */
	public void setEnd_day(final Integer end_day) {
		this.setValue(Fields.END_DAY, end_day);
	}

	/**
	 * Sets the end_month.
	 *
	 * @param end_month
	 *            the new end_month
	 */
	public void setEnd_month(final Integer end_month) {
		this.setValue(Fields.END_MONTH, end_month);
	}

	/**
	 * Sets the end_year.
	 *
	 * @param end_year
	 *            the new end_year
	 */
	public void setEnd_year(final Integer end_year) {
		this.setValue(Fields.END_YEAR, end_year);
	}

	/**
	 * Sets the float_every.
	 *
	 * @param float_every
	 *            the new float_every
	 */
	public void setFloat_every(final Integer float_every) {
		this.setValue(Fields.FLOATS_EVERY, float_every);
	}

	/**
	 * Sets the float_every_num.
	 *
	 * @param float_every_num
	 *            the new float_every_num
	 */
	public void setFloat_every_num(final Integer float_every_num) {
		this.setValue(Fields.FLOATS_EVERY_NUM, float_every_num);
	}

	/**
	 * Sets the occur_floating.
	 *
	 * @param occur_floating
	 *            the new occur_floating
	 */
	public void setOccur_floating(final Boolean occur_floating) {
		this.setValue(Fields.OCCUR_FLOATING, occur_floating);
	}

	/**
	 * Sets the occur_once.
	 *
	 * @param occur_once
	 *            the new occur_once
	 */
	public void setOccur_once(final Boolean occur_once) {
		this.setValue(Fields.OCCUR_ONCE, occur_once);
	}

	/**
	 * Sets the occur_repeating.
	 *
	 * @param occur_repeating
	 *            the new occur_repeating
	 */
	public void setOccur_repeating(final Boolean occur_repeating) {
		this.setValue(Fields.OCCUR_REPEATING, occur_repeating);
	}

	/**
	 * Sets the repeat_every.
	 *
	 * @param repeat_every
	 *            the new repeat_every
	 */
	public void setRepeat_every(final Integer repeat_every) {
		this.setValue(Fields.REPEAT_EVERY, repeat_every);
	}

	/**
	 * Sets the repeat_evey_num.
	 *
	 * @param repeat_evey_num
	 *            the new repeat_evey_num
	 */
	public void setRepeat_evey_num(final Integer repeat_evey_num) {
		this.setValue(Fields.REPEAT_EVERY_NUM, repeat_evey_num);
	}

	/**
	 * Sets the start_date.
	 *
	 * @param string
	 *            the new start_date
	 */
	private void setStart_date(final String string) {
		this.setValue(Fields.START_DATE, string);
	}

	/**
	 * Sets the start_day.
	 *
	 * @param string
	 *            the new start_day
	 */
	public void setStart_day(final String string) {
		this.setValue(Fields.START_DAY, string);
	}

	/**
	 * Sets the start_month.
	 *
	 * @param string
	 *            the new start_month
	 */
	public void setStart_month(final String string) {
		this.setValue(Fields.START_MONTH, string);
	}

	/**
	 * Sets the start_year.
	 *
	 * @param start_year
	 *            the new start_year
	 */
	public void setStart_year(final Integer start_year) {
		this.setValue(Fields.START_YEAR, start_year);
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(final String title) {
		this.setValue(Fields.TITLE, title);
	}

	/**
	 * Converts the {@link TransactionDataType} object into the fields expected, ie the ones
	 * starting "trans_".
	 *
	 * @param transactionDataType
	 *            the new transaction data type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public void setTransactionDataType(final TransactionDataType transactionDataType)
	        throws ClearcheckbookException {
		this.setValue(Fields.TRANS_AMOUNT, transactionDataType.getAmount());
		this.setValue(Fields.TRANS_TRANSACTION_TYPE, transactionDataType.getTransactionType());
		this.setValue(Fields.TRANS_ACCOUNT_ID, transactionDataType.getAccountId());
		this.setValue(Fields.TRANS_CATEGORY_ID, transactionDataType.getCategoryId());
		this.setValue(Fields.TRANS_DESCRIPTION, transactionDataType.getDescription());
		this.setValue(Fields.TRANS_ACCOUNTFROM, transactionDataType.getFromAccountId());
		this.setValue(Fields.TRANS_ACCOUNTTO, transactionDataType.getToAccount());
		this.setValue(Fields.TRANS_CHECK_NUM, transactionDataType.getCheckNum());
		this.setValue(Fields.TRANS_MEMO, transactionDataType.getMemo());
		this.setValue(Fields.TRANS_PAYEE, transactionDataType.getPayee());
	}

	/**
	 * The Enum Fields.
	 */
	public enum Fields {

		/** The id. */
		ID, /** The title. */
		TITLE, /** The start date. */
		START_DATE, /** The end date. */
		END_DATE, /** The notify. */
		NOTIFY, /** The notify time. */
		NOTIFY_TIME, /** The repeat. */
		REPEAT, /** The repeat every num. */
		REPEAT_EVERY_NUM, /** The repeat every. */
		REPEAT_EVERY, /** The floats. */
		FLOATS, /** The floats every num. */
		FLOATS_EVERY_NUM, /** The floats every. */
		FLOATS_EVERY, /** The trans amount. */
		TRANS_AMOUNT, /** The trans description. */
		TRANS_DESCRIPTION, /** The trans transaction type. */
		TRANS_TRANSACTION_TYPE, /** The trans account id. */
		TRANS_ACCOUNT_ID, /** The trans category id. */
		TRANS_CATEGORY_ID, /** The trans transfertoaccount. */
		TRANS_TRANSFERTOACCOUNT, /** The trans check num. */
		TRANS_CHECK_NUM, /** The trans memo. */
		TRANS_MEMO, /** The trans payee. */
		TRANS_PAYEE, /** The start year. */
		START_YEAR, /** The occur floating. */
		OCCUR_FLOATING, /** The email. */
		EMAIL, /** The emaildays. */
		EMAILDAYS, /** The start month. */
		START_MONTH, /** The start day. */
		START_DAY, /** The end year. */
		END_YEAR, /** The end month. */
		END_MONTH, /** The end day. */
		END_DAY, /** The occur once. */
		OCCUR_ONCE, /** The occur repeating. */
		OCCUR_REPEATING, /** The trans accountfrom. */
		TRANS_ACCOUNTFROM, /** The trans accountto. */
		TRANS_ACCOUNTTO, /** The reminder id. */
		REMINDER_ID, /** The transaction. */
		TRANSACTION;
	}
}
