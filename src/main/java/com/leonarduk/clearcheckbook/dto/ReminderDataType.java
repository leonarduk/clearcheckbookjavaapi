package com.leonarduk.clearcheckbook.dto;

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
public class ReminderDataType extends AbstractDataType {

	enum Fields {
		ID, TITLE, START_DATE, END_DATE, NOTIFY, NOTIFY_TIME, REPEAT, REPEAT_EVERY_NUM, REPEAT_EVERY, FLOATS, FLOATS_EVERY_NUM, FLOATS_EVERY, TRANS_AMOUNT, TRANS_DESCRIPTION, TRANS_TRANSACTION_TYPE, TRANS_ACCOUNT_ID, TRANS_CATEGORY_ID, TRANS_TRANSFERTOACCOUNT, TRANS_CHECK_NUM, TRANS_MEMO, TRANS_PAYEE, START_YEAR, OCCUR_FLOATING, EMAIL, EMAILDAYS, START_MONTH, START_DAY, END_YEAR, END_MONTH, END_DAY, OCCUR_ONCE, OCCUR_REPEATING, TRANS_ACCOUNTFROM, TRANS_ACCOUNTTO;
	}

	public ReminderDataType(Map<String, String> map) {
		super(map);
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

	public static ReminderDataType create(String title, String email,
			int emailDays, int start_year, int start_month, int start_day,
			int end_year, int end_month, int end_day, boolean occur_once,
			boolean occur_repeating, boolean occur_floating, int repeat_every,
			int repeat_evey_num, int float_every_num, int float_every,
			TransactionDataType transactionDataType) {
		ReminderDataType reminderDataType = new ReminderDataType();

		_logger.debug("createUserDataType: " + reminderDataType);

		return reminderDataType;
	}

	public String getTitle() {
		return getValue(Fields.TITLE);
	}

	public void setTitle(String title) {
		setValue(Fields.TITLE, title);
	}

	public String getEmail() {
		return getValue(Fields.EMAIL);
	}

	public void setEmail(String email) {
		setValue(Fields.EMAIL, email);
	}

	public int getEmailDays() {
		return getIntegerValue(Fields.EMAILDAYS);

	}

	public void setEmailDays(int emailDays) {
		setValue(Fields.EMAILDAYS, emailDays);
	}

	public int getStart_year() {
		return getIntegerValue(Fields.START_YEAR);
	}

	public void setStart_year(int start_year) {
		setValue(Fields.START_YEAR, start_year);
	}

	public int getStart_month() {
		return getIntegerValue(Fields.START_MONTH);
	}

	public void setStart_month(int start_month) {
		setValue(Fields.START_YEAR, start_month);
	}

	public int getStart_day() {
		return getIntegerValue(Fields.START_DAY);
	}

	public void setStart_day(int start_day) {
		setValue(Fields.START_DAY, start_day);
	}

	public int getEnd_year() {
		return getIntegerValue(Fields.END_YEAR);
	}

	public void setEnd_year(int end_year) {
		setValue(Fields.END_YEAR, end_year);
	}

	public int getEnd_month() {
		return getIntegerValue(Fields.END_MONTH);
	}

	public void setEnd_month(int end_month) {
		setValue(Fields.END_MONTH, end_month);
	}

	public int getEnd_day() {
		return getIntegerValue(Fields.END_DAY);
	}

	public void setEnd_day(int end_day) {
		setValue(Fields.END_DAY, end_day);
	}

	public boolean isOccur_once() {
		return getBooleanValue(Fields.OCCUR_ONCE);
	}

	public void setOccur_once(boolean occur_once) {
		setValue(Fields.OCCUR_ONCE, occur_once);
	}

	public boolean isOccur_repeating() {
		return getBooleanValue(Fields.OCCUR_REPEATING);
	}

	public void setOccur_repeating(boolean occur_repeating) {
		setValue(Fields.OCCUR_REPEATING, occur_repeating);
	}

	public boolean isOccur_floating() {
		return getBooleanValue(Fields.OCCUR_FLOATING);
	}

	public void setOccur_floating(boolean occur_floating) {
		setValue(Fields.OCCUR_FLOATING, occur_floating);
	}

	public int getRepeat_every() {
		return getIntegerValue(Fields.REPEAT_EVERY);
	}

	public void setRepeat_every(int repeat_every) {
		setValue(Fields.REPEAT_EVERY, repeat_every);
	}

	public int getRepeat_evey_num() {
		return getIntegerValue(Fields.REPEAT_EVERY_NUM);
	}

	public void setRepeat_evey_num(int repeat_evey_num) {
		setValue(Fields.REPEAT_EVERY_NUM, repeat_evey_num);
	}

	public int getFloat_every_num() {
		return getIntegerValue(Fields.FLOATS_EVERY_NUM);
	}

	public void setFloat_every_num(int float_every_num) {
		setValue(Fields.FLOATS_EVERY_NUM, float_every_num);
	}

	public int getFloat_every() {
		return getIntegerValue(Fields.FLOATS_EVERY);
	}

	public void setFloat_every(int float_every) {
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
		boolean jive = false;
		String date = null;
		TransactionDataType transactionDataType = TransactionDataType
				.create(date,
						getDoubleValue(Fields.TRANS_AMOUNT),
						TransactionDataType.Type
								.fromOrdinal(getIntegerValue(Fields.TRANS_TRANSACTION_TYPE)),
						getLongValue(Fields.TRANS_ACCOUNT_ID),
						getLongValue(Fields.TRANS_CATEGORY_ID),
						getValue(Fields.TRANS_DESCRIPTION), jive,
						getLongValue(Fields.TRANS_ACCOUNTFROM),
						getLongValue(Fields.TRANS_ACCOUNTTO),
						getValue(Fields.TRANS_CHECK_NUM),
						getValue(Fields.TRANS_MEMO),
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
}
