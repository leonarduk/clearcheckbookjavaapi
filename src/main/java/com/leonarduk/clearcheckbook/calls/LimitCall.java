package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.LimitDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 30 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class LimitCall extends AbstractCall<LimitDataType> {

	private static final Logger _logger = Logger.getLogger(LimitCall.class);

	public static final String TYPE = "limit";

	@Override
	protected String getUrlSuffix() {
		return TYPE;
	}

	public LimitCall(ClearCheckBookConnection connection) {
		super(connection, LimitDataType.class);
	}

	/**
	 * Returns an array of limits for the current user<br>
	 * Method: get<br>
	 * Call: limits
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/limits/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * None
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The id of the limit <br>
	 * name The Account or Category name this limit is assigned to. <br>
	 * limit_amount The amount this limit is set to. <br>
	 * account_id If this is a limit for an account, this will be the id of that
	 * account (0 if it's not set for an account) <br>
	 * category_id If this is a limit for a category, this is the id of that
	 * category (0 if it's not set for a category) <br>
	 * spent The amount spent so far for this transaction. <br>
	 * rollover Whether or not this limit rolls over on its reset day <br>
	 * reset_day The day of the month this limit is set to reset. <br>
	 * transfer Whether or not this limit includes transfers in the amount
	 * spent. <br>
	 * deposit Whether or not this limit uses deposits to reduce the amount
	 * spent. <br>
	 * duration The duration of this budget based on when it resets. (0=Weekly;
	 * 1=Bi-Weekly; 2=Monthly; 3=Quarterly; 4=Semi-Annually; 5=Annually) <br>
	 * start_date If the duration is not 2, this will be when the budget
	 * originally started. <br>
	 * this_start_date This is when the budget for the current time period
	 * started. <br>
	 * this_end_date This is when the budget for the current time period ends. <br>
	 * original_limit The original amount of the limit (in case rollover is set
	 * to true).
	 */
	@Override
	public List<LimitDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	/**
	 * Returns information about a specific limit. <br>
	 * Method: get <br>
	 * Call: limit
	 * <p>
	 * <br>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/limit/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the limit you want
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The id of the limit <br>
	 * amount The amount this limit is set for <br>
	 * reset_day The day of the month this limit is going to reset. <br>
	 * rollover TRUE or FALSE for whether or not this limit rollsover <br>
	 * transfer TRUE or FALSE for whether or not this limit includes transfers
	 * in the amount spent. <br>
	 * deposit TRUE or FALSE for whether or not this limit uses deposits to
	 * reduce the amount spent. <br>
	 * duration The duration of this budget based on when it resets. (0=Weekly;
	 * 1=Bi-Weekly; 2=Monthly; 3=Quarterly; 4=Semi-Annually; 5=Annually) <br>
	 * start_date If the duration is not 2, this will be when the budget
	 * originally started. <br>
	 * this_start_date This is when the budget for the current time period
	 * started. <br>
	 * this_end_date This is when the budget for the current time period ends. <br>
	 * original_limit The original amount of the limit (in case rollover is set
	 * to true).
	 */
	@Override
	public LimitDataType get(ParsedNameValuePair id)
			throws ClearcheckbookException {
		LimitDataType limitDataType = super.get(id);
		_logger.debug(limitDataType);
		return limitDataType;
	}

	/**
	 * Inserts a limit for the current user <br>
	 * Method: post <br>
	 * Call: limit
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/limit/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * account_id Required If the limit is for an account, the id of the
	 * account. Default is 0. <br>
	 * category_id Required If the limit is for a category, the id of the
	 * category. Default is 0. <br>
	 * amount Required The amount this limit should be for (eg: 300) <br>
	 * duration Required The duration of this budget based on when it resets.
	 * (0=Weekly; 1=Bi-Weekly; 2=Monthly; 3=Quarterly; 4=Semi-Annually;
	 * 5=Annually) <br>
	 * reset_day Required Integer 1-31 for the day this limit should reset on.
	 * If this number is higher than the last day of the month, the limit will
	 * reset on the last day of the month. <br>
	 * start_date Required If the duration is not 2, this will be when the
	 * budget originally starts (formatted as yyyy-mm-dd). <br>
	 * rollover Required Whether or not this limit rolls over any unused money
	 * to the next month. 0=false, 1=true <br>
	 * transfer Required Whether or not this limit includes transfers in the
	 * amount spent. 0=false, 1=true <br>
	 * deposit Required Whether or not this limit uses deposits to reduce the
	 * amount spent. 0=false, 1=true
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id / false Returns the id of the newly created limit on success or
	 * false/null on fail
	 */
	@Override
	public String insert(LimitDataType input) throws ClearcheckbookException {
		String insert = super.insert(input);
		_logger.debug(insert);
		return insert;
	}

	/**
	 * Edit a specific limit for the current user. <br>
	 * Method: put <br>
	 * Call: limit
	 * <p>
	 * <br>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/limit/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the limit being edited. <br>
	 * amount Required The amount this limit should be for (eg: 300) <br>
	 * duration Required The duration of this budget based on when it resets.
	 * (0=Weekly; 1=Bi-Weekly; 2=Monthly; 3=Quarterly; 4=Semi-Annually;
	 * 5=Annually) <br>
	 * reset_day Required Integer 1-31 for the day this limit should reset on.
	 * If this number is higher than the last day of the month, the limit will
	 * reset on the last day of the month. <br>
	 * start_date Required If the duration is not 2, this will be when the
	 * budget originally starts (formatted as yyyy-mm-dd). <br>
	 * rollover Required Whether or not this limit rolls over any unused money
	 * to the next month. 0=false, 1=true <br>
	 * transfer Required Whether or not this limit includes transfers in the
	 * amount spent. 0=false, 1=true <br>
	 * deposit Required Whether or not this limit uses deposits to reduce the
	 * amount spent. 0=false, 1=true
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful edit or false/null on fail.
	 * ACTUALLY - it returns "null" on success and "" on fail
	 */
	@Override
	public boolean edit(LimitDataType dataType) throws ClearcheckbookException {
		String returnString;
		try {
			returnString = this.getConnection().putPage(getUrlSuffix(),
					dataType.getEditParameters());
			_logger.debug("returned: [" + returnString + "]");
			boolean ok = (returnString.equals("null"));
			_logger.info("edit : edited " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to edit "
					+ getUrlSuffix(), e);
		}
	}

	/**
	 * Delete a specific limit for the current user. <br>
	 * Method: delete <br>
	 * Call: limit
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/limit/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the limit to delete
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful delete or false/null on fail
	 */
	@Override
	public boolean delete(ParsedNameValuePair input)
			throws ClearcheckbookException {
		boolean delete = super.delete(input);
		_logger.debug(delete);
		return delete;
	}

}
