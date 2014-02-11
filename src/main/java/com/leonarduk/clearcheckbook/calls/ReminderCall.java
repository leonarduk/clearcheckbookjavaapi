package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.ReminderDataType;

public class ReminderCall extends AbstractCall<ReminderDataType> {

	private static final Logger _logger = Logger.getLogger(ReminderCall.class);

	public static final String TYPE = "reminder";

	public ReminderCall(ClearCheckBookConnection connection) {
		super(connection, ReminderDataType.class);
	}

	@Override
	protected String getUrlSuffix() {
		return TYPE;
	}

	/**
	 * Returns an array of reminders for the current user <br>
	 * Method: get <br>
	 * Call: reminders
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminders/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * upcoming_days Optional The number of days worth of reminders you would
	 * like to receive. Default = 30
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * reminder_id the id of the reminder in the ClearCheckbook system <br>
	 * date the next date this reminder is set to occur on <br>
	 * reminder_date_id the id of the date in the ClearCheckbook system <br>
	 * title the title of the reminder <br>
	 * start_date the date the reminder is set to start <br>
	 * end_date the date the reminder is set to end <br>
	 * notify 0 or 1. 1 if the user selected to be emailed before the reminder
	 * occurs. <br>
	 * notify_time 0-7. The number of days ahead of the reminder the user would
	 * like to be emailed <br>
	 * repeat 0 or 1. 1 if this reminder is set to repeat <br>
	 * repeat_every_num Integer for how often this should repeat. If set to 2
	 * and repeat_every = 3, this reminder will repeat every 2 months. <br>
	 * repeat_every 1-4. 1=Day, 2=Week, 3=Month, 4=Year <br>
	 * floats 0 or 1. 1 if this reminder is set to float <br>
	 * floats_every_num When this reminder floats. 1= First, 2= Second, 3=
	 * Third, 4= Fourth, -1= Last <br>
	 * floats_every When this reminder floats. 0= Sunday, 1= Monday, 2= Tuesday,
	 * 3= Wednesday, 4= Thursday, 5= Friday, 6= Saturday, -1= Day <br>
	 * trans_amount If there is a recurring transaction associated with this
	 * reminder, this is the amount <br>
	 * trans_description If there is a recurring transaction associated with
	 * this reminder, this is the description <br>
	 * trans_transaction_type If there is a recurring transaction associated
	 * with this reminder, this is the transaction type (0=withdrawal,
	 * 1=deposit, 3= transfer) <br>
	 * trans_account_id If there is a recurring transaction associated with this
	 * reminder, this is the account_id <br>
	 * trans_category_id If there is a recurring transaction associated with
	 * this reminder, this is the category_id <br>
	 * trans_transfertoaccount If there is a recurring transaction associated
	 * with this reminder, this is the account_id of the account being
	 * transferred to <br>
	 * trans_check_num If there is a recurring transaction associated with this
	 * reminder, this is the check_num <br>
	 * trans_memo If there is a recurring transaction associated with this
	 * reminder, this is the memo <br>
	 * trans_payee If there is a recurring transaction associated with this
	 * reminder, this is the payee
	 */
	@Override
	public List<ReminderDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	/**
	 * Returns information about a specific reminder. <br>
	 * Method: get <br>
	 * Call: reminder
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminder/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the reminder you want
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id the id of the reminder in the ClearCheckbook system <br>
	 * title the title of the reminder <br>
	 * start_date the date the reminder is set to start <br>
	 * end_date the date the reminder is set to end <br>
	 * notify 0 or 1. 1 if the user selected to be emailed before the reminder
	 * occurs. <br>
	 * notify_time 0-7. The number of days ahead of the reminder the user would
	 * like to be emailed <br>
	 * repeat 0 or 1. 1 if this reminder is set to repeat <br>
	 * repeat_every_num Integer for how often this should repeat. If set to 2
	 * and repeat_every = 3, this reminder will repeat every 2 months. <br>
	 * repeat_every 1-4. 1=Day, 2=Week, 3=Month, 4=Year <br>
	 * floats 0 or 1. 1 if this reminder is set to float <br>
	 * floats_every_num When this reminder floats. 1= First, 2= Second, 3=
	 * Third, 4= Fourth, -1= Last <br>
	 * floats_every When this reminder floats. 0= Sunday, 1= Monday, 2= Tuesday,
	 * 3= Wednesday, 4= Thursday, 5= Friday, 6= Saturday, -1= Day <br>
	 * trans_amount If there is a recurring transaction associated with this
	 * reminder, this is the amount <br>
	 * trans_description If there is a recurring transaction associated with
	 * this reminder, this is the description <br>
	 * trans_transaction_type If there is a recurring transaction associated
	 * with this reminder, this is the transaction type (0=withdrawal,
	 * 1=deposit, 3= transfer) <br>
	 * trans_account_id If there is a recurring transaction associated with this
	 * reminder, this is the account_id <br>
	 * trans_category_id If there is a recurring transaction associated with
	 * this reminder, this is the category_id <br>
	 * trans_transfertoaccount If there is a recurring transaction associated
	 * with this reminder, this is the account_id of the account being
	 * transferred to <br>
	 * trans_check_num If there is a recurring transaction associated with this
	 * reminder, this is the check_num <br>
	 * trans_memo If there is a recurring transaction associated with this
	 * reminder, this is the memo <br>
	 * trans_payee If there is a recurring transaction associated with this
	 * reminder, this is the payee
	 */
	@Override
	public ReminderDataType get(ParsedNameValuePair id)
			throws ClearcheckbookException {
		ReminderDataType reminderDataType = super.get(id);
		_logger.debug("get: " + reminderDataType);
		return reminderDataType;
	}

	/**
	 * Inserts a reminder for the current user <br>
	 * Method: post <br>
	 * Call: reminder
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminder/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * title Required The title of the reminder <br>
	 * email Required "true" or "false". "true" if you want to this email to
	 * remind the user <br>
	 * emailDays Optional If email is set to "true", this is the number of days
	 * ahead of time the user will be emailed (1-7) <br>
	 * start_year Required the year of the start date (YYYY) <br>
	 * start_month Required the month of the start date (MM) <br>
	 * start_day Required the day of the start date (DD) <br>
	 * end_year Optional the year of the end date (YYYY) <br>
	 * end_month Optional the month of the end date (MM) <br>
	 * end_day Optional the day of the end date (DD) <br>
	 * occur_once Optional "true" if this reminder is only occuring once. <br>
	 * occur_repeating Optional "true" if this reminder is repeating <br>
	 * occur_floating Optional "true" if this reminder is floating <br>
	 * repeat_every Optional 1-4. 1=Day, 2=Week, 3=Month, 4=Year <br>
	 * repeat_every_num Optional Integer for how often this should repeat. If
	 * set to 2 and repeat_every = 3, this reminder will repeat every 2 months.
	 * <br>
	 * float_every Optional When this reminder floats. 0= Sunday, 1= Monday, 2=
	 * Tuesday, 3= Wednesday, 4= Thursday, 5= Friday, 6= Saturday, -1= Day <br>
	 * float_every_num Optional When this reminder floats. 1= First, 2= Second,
	 * 3= Third, 4= Fourth, -1= Last <br>
	 * transaction Required "true" or "false". "true" if there is a transaction
	 * associated with this reminder. <br>
	 * trans_amount Optional The amount associated with the transaction <br>
	 * trans_description Optional the description associated with the
	 * transaction <br>
	 * trans_payee Optional the payee associated with this transaction <br>
	 * trans_memo Optional the memo associated with this transaction <br>
	 * trans_check_num Optional the check number associated with this
	 * transaction <br>
	 * trans_account_id Optional the account_id associated with this transaction
	 * <br>
	 * trans_category_id Optional the category_id associated with this
	 * transaction <br>
	 * trans_transaction_type Optional The transaction type associated with this
	 * transaction. (0=withdrawal, 1=deposit, 3= transfer) <br>
	 * trans_accountFrom Optional If trans_transaction_type = 3, this is the
	 * account_id you're transferring from <br>
	 * trans_accountTo Optional If trans_transaction_type = 3, this is the
	 * account_id you're transferring to
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id / false Returns the id of the newly created reminder or false/null on
	 * fail
	 */
	@Override
	public String insert(ReminderDataType input) throws ClearcheckbookException {
		return super.insert(input);
	}

	/**
	 * 
	 Edit a specific reminder for the current user. <br>
	 * Method: put <br>
	 * Call: reminder
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminder/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * reminder_id Required The id of the reminder being edited. <br>
	 * title Required The title of the reminder <br>
	 * email Required "true" or "false". "true" if you want to this email to
	 * remind the user <br>
	 * emailDays Optional If email is set to "true", this is the number of days
	 * ahead of time the user will be emailed (1-7) <br>
	 * start_year Required the year of the start date (YYYY) <br>
	 * start_month Required the month of the start date (MM) <br>
	 * start_day Required the day of the start date (DD) <br>
	 * end_year Optional the year of the end date (YYYY) <br>
	 * end_month Optional the month of the end date (MM) <br>
	 * end_day Optional the day of the end date (DD) <br>
	 * occur_once Optional "true" if this reminder is only occuring once. <br>
	 * occur_repeating Optional "true" if this reminder is repeating <br>
	 * occur_floating Optional "true" if this reminder is floating <br>
	 * repeat_every Optional 1-4. 1=Day, 2=Week, 3=Month, 4=Year <br>
	 * repeat_every_num Optional Integer for how often this should repeat. If
	 * set to 2 and repeat_every = 3, this reminder will repeat every 2 months.
	 * <br>
	 * float_every Optional When this reminder floats. 0= Sunday, 1= Monday, 2=
	 * Tuesday, 3= Wednesday, 4= Thursday, 5= Friday, 6= Saturday, -1= Day <br>
	 * float_every_num Optional When this reminder floats. 1= First, 2= Second,
	 * 3= Third, 4= Fourth, -1= Last <br>
	 * transaction Required "true" or "false". "true" if there is a transaction
	 * associated with this reminder. <br>
	 * trans_amount Optional The amount associated with the transaction <br>
	 * trans_description Optional the description associated with the
	 * transaction <br>
	 * trans_payee Optional the payee associated with this transaction <br>
	 * trans_memo Optional the memo associated with this transaction <br>
	 * trans_check_num Optional the check number associated with this
	 * transaction <br>
	 * trans_account_id Optional the account_id associated with this transaction
	 * <br>
	 * trans_category_id Optional the category_id associated with this
	 * transaction <br>
	 * trans_transaction_type Optional The transaction type associated with this
	 * transaction. (0=withdrawal, 1=deposit, 3= transfer) <br>
	 * trans_accountFrom Optional If trans_transaction_type = 3, this is the
	 * account_id you're transferring from <br>
	 * trans_accountTo Optional If trans_transaction_type = 3, this is the
	 * account_id you're transferring to
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful edit or false/null on fail.
	 * NB - this appears to change the id so you cannot do edit (id) then get(id) as the id will change
	 */
	@Override
	public boolean edit(ReminderDataType dataType)
			throws ClearcheckbookException {
		return super.edit(dataType);
	}

	/**
	 * Delete a single occurrence of a reminder for the current user. <br>
	 * Method: delete <br>
	 * Call: reminder
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminder/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the reminder to delete
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful delete or false/null on fail
	 */
	@Override
	public boolean delete(ParsedNameValuePair input)
			throws ClearcheckbookException {
		return super.delete(input);
	}

	/**
	 * Delete all occurrences of a reminder for the current user. <br>
	 * Method: delete <br>
	 * Call: reminders
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reminders/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the reminder to delete
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful delete or false/null on fail
	 * 
	 * @return
	 * @throws ClearcheckbookException
	 */
	public boolean deleteAll(ParsedNameValuePair id)
			throws ClearcheckbookException {
		_logger.debug("delete: " + id.getValue());
		String returnString;
		try {
			returnString = this.getConnection().deletePage(getPluralUrl(), id);
			boolean ok = Boolean.valueOf(returnString);
			_logger.info("insert : deleted " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to delete "
					+ getUrlSuffix() + " id: " + id.getValue(), e);
		}
	}

}
