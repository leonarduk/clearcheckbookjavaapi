package com.leonarduk.clearcheckbook.calls;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.PremiumDataType;

public class PremiumCall extends AbstractCall<PremiumDataType> {

	private static final Logger _logger = Logger.getLogger(PremiumCall.class);

	public static final String TYPE = "premium";

	public PremiumCall(ClearCheckBookConnection connection) {
		super(connection, PremiumDataType.class);
	}

	@Override
	protected String getUrlSuffix() {
		return TYPE;
	}

	/**
	 * 
	 Determines whether or not this user is a premium member. If they are, it
	 * returns their custom field flags. <br>
	 * Method: get <br>
	 * Call: premium
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/premium/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * None
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * status true or false. true if the user has a valid premium membership <br>
	 * check_num 0 or 1. 1 if the user has the additional check number field
	 * enabled <br>
	 * memo 0 or 1. 1 if the user has the additional memo field enabled <br>
	 * payee 0 or 1. 1 if the user has the additional payee field enabled
	 */
	@Override
	public PremiumDataType get() throws ClearcheckbookException {
		PremiumDataType premiumDataType = super.get();
		_logger.debug("get:" + premiumDataType);
		return premiumDataType;
	}
}
