package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.UserDataType;

public class UserCall extends AbstractCall<UserDataType> {

	private static final Logger _logger = Logger.getLogger(UserCall.class);

	public static final String TYPE = "user";

	public UserCall(ClearCheckBookConnection connection) {
		super(TYPE, connection);
	}

	/**
	 * Gets the details for the current user. <br>
	 * Method: get <br>
	 * Call: user
	 * <p>
	 * Example: https://username:password@www.clearcheckbook.com/api/user/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * None
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The current users id in the ClearCheckbook system <br>
	 * username The current users username <br>
	 * password MD5 hash of current users password <br>
	 * email Email address of current user
	 * 
	 * @throws ClearcheckbookException
	 */
	public UserDataType get() throws ClearcheckbookException {
		return super.get();
	}

	/**
	 * Creates a new ClearCheckbook.com user account <br>
	 * Method: post <br>
	 * Call: user
	 * <p>
	 * Example: <br>
	 * https://www.clearcheckbook.com/api/user/
	 * <p>
	 * Notes: <br>
	 * You do not pass any authentication information when creating a user. This
	 * is the only function that behaves this way. If the username or email
	 * address exists in the system, this function returns false. <br>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * username Required The desired username for the new user <br>
	 * xpassword Required an unencoded password for the new user <br>
	 * email Required an email address for the new user <br>
	 * app Optional A name for your app (eg: "iPhone App" for an iPhone app).
	 * Lets us know where users are coming from.
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * Multiple Responses* The new users id in the ClearCheckbook system on
	 * successfull insert or false if the username or email already exists in
	 * system.
	 * 
	 * @param
	 * @return
	 * @throws ClearcheckbookException
	 */
	@Override
	public String insert(UserDataType dataType) throws ClearcheckbookException {
		_logger.debug("insert: " + dataType);
		String returnString;
		try {
			returnString = this.getConnection().postPage(this.url,
					dataType.getInsertParameters());

			Long id = Long.valueOf(returnString);
			_logger.info("insert: created id " + id);
			return id.toString();
		} catch (NumberFormatException e) {
			throw new ClearcheckbookException(
					"Failed to create user - already exists", e);
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to create user ", e);
		}
	}
}
