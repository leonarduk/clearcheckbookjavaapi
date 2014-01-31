package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import org.apache.log4j.Logger;

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
		ID, TITLE, START_DATE, END_DATE, NOTIFY, NOTIFY_TIME, REPEAT, REPEAT_EVERY_NUM, REPEAT_EVERY, FLOATS, FLOATS_EVERY_NUM, FLOATS_EVERY, TRANS_AMOUNT, TRANS_DESCRIPTION, TRANS_TRANSACTION_TYPE, TRANS_ACCOUNT_ID, TRANS_CATEGORY_ID, TRANS_TRANSFERTOACCOUNT, TRANS_CHECK_NUM, TRANS_MEMO, TRANS_PAYEE;
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

//	public static ReminderDataType create(String title, String email,
//			int emailDays, int start_year, int start_month, int start_day,
//			int end_year, int end_month, int end_day, boolean occur_once,
//			boolean occur_repeating, boolean occur_floating, int repeat_every,
//			int repeat_evey_num, int float_every_num, int float_every,
//			TransactionDataType transactionDataType) {
//		ReminderDataType reminderDataType = new ReminderDataType();
//
//		
//		_logger.debug("createUserDataType: "+ reminderDataType);
//
//		return reminderDataType;
//	}
//
//	public String getTitle() {
//		return getValue(Fields.TITLE);
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getEmail() {
//		return getValue(Fields.);
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getEmailDays() {
//		return emailDays;
//	}
//
//	public void setEmailDays(int emailDays) {
//		this.emailDays = emailDays;
//	}
//
//	public int getStart_year() {
//		return start_year;
//	}
//
//	public void setStart_year(int start_year) {
//		this.start_year = start_year;
//	}
//
//	public int getStart_month() {
//		return start_month;
//	}
//
//	public void setStart_month(int start_month) {
//		this.start_month = start_month;
//	}
//
//	public int getStart_day() {
//		return start_day;
//	}
//
//	public void setStart_day(int start_day) {
//		this.start_day = start_day;
//	}
//
//	public int getEnd_year() {
//		return end_year;
//	}
//
//	public void setEnd_year(int end_year) {
//		this.end_year = end_year;
//	}
//
//	public int getEnd_month() {
//		return end_month;
//	}
//
//	public void setEnd_month(int end_month) {
//		this.end_month = end_month;
//	}
//
//	public int getEnd_day() {
//		return end_day;
//	}
//
//	public void setEnd_day(int end_day) {
//		this.end_day = end_day;
//	}
//
//	public boolean isOccur_once() {
//		return occur_once;
//	}
//
//	public void setOccur_once(boolean occur_once) {
//		this.occur_once = occur_once;
//	}
//
//	public boolean isOccur_repeating() {
//		return occur_repeating;
//	}
//
//	public void setOccur_repeating(boolean occur_repeating) {
//		this.occur_repeating = occur_repeating;
//	}
//
//	public boolean isOccur_floating() {
//		return occur_floating;
//	}
//
//	public void setOccur_floating(boolean occur_floating) {
//		this.occur_floating = occur_floating;
//	}
//
//	public int getRepeat_every() {
//		return repeat_every;
//	}
//
//	public void setRepeat_every(int repeat_every) {
//		this.repeat_every = repeat_every;
//	}
//
//	public int getRepeat_evey_num() {
//		return repeat_evey_num;
//	}
//
//	public void setRepeat_evey_num(int repeat_evey_num) {
//		this.repeat_evey_num = repeat_evey_num;
//	}
//
//	public int getFloat_every_num() {
//		return float_every_num;
//	}
//
//	public void setFloat_every_num(int float_every_num) {
//		this.float_every_num = float_every_num;
//	}
//
//	public int getFloat_every() {
//		return float_every;
//	}
//
//	public void setFloat_every(int float_every) {
//		this.float_every = float_every;
//	}
//
//	public TransactionDataType getTransactionDataType() {
//		return transactionDataType;
//	}
//
//	public void setTransactionDataType(TransactionDataType transactionDataType) {
//		this.transactionDataType = transactionDataType;
//	}
}
