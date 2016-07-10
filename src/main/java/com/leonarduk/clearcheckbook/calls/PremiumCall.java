/**
 * PremiumCall
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.PremiumDataType;

/**
 * The Class PremiumCall.
 */
public class PremiumCall extends AbstractCall<PremiumDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(PremiumCall.class);

	/** The Constant TYPE. */
	public static final String TYPE = "premium";

	/**
	 * Instantiates a new premium call.
	 *
	 * @param connection
	 *            the connection
	 */
	public PremiumCall(final ClearCheckBookConnection connection) {
		super(connection, PremiumDataType.class);
	}

	/**
	 * Determines whether or not this user is a premium member. If they are, it returns their custom
	 * field flags. <br>
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
	 * check_num 0 or 1. 1 if the user has the additional check number field enabled <br>
	 * memo 0 or 1. 1 if the user has the additional memo field enabled <br>
	 * payee 0 or 1. 1 if the user has the additional payee field enabled
	 *
	 * @return the premium data type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public PremiumDataType get() throws ClearcheckbookException {
		final PremiumDataType premiumDataType = super.get();
		PremiumCall._logger.debug("get:" + premiumDataType);
		return premiumDataType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getUrlSuffix()
	 */
	@Override
	protected String getUrlSuffix() {
		return PremiumCall.TYPE;
	}
}
