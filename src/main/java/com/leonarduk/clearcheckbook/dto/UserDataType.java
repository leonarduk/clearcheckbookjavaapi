/**
 * UserDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The Class UserDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class UserDataType extends AbstractDataType<UserDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(UserDataType.class);

	/**
	 * Creates the.
	 *
	 * @param username
	 *            the username
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @return the user data type
	 */
	public static UserDataType create(final String username, final String email,
	        final String password) {
		return UserDataType.create(username, email, password, "JAVA");
	}

	/**
	 * Create user passing the optional application parameter.
	 *
	 * @param username
	 *            the username
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @param application
	 *            the application
	 * @return the user data type
	 */
	public static UserDataType create(final String username, final String email,
	        final String password, final String application) {
		final UserDataType userDataType = new UserDataType();
		userDataType.addField(Fields.EMAIL, email);
		userDataType.addField(Fields.PASSWORD, password);
		userDataType.addField(Fields.USERNAME, username);
		userDataType.addField(Fields.APP, application);

		UserDataType._logger.debug("Created: " + userDataType);
		return userDataType;
	}

	/**
	 * Instantiates a new user data type.
	 */
	private UserDataType() {
	}

	/**
	 * Instantiates a new user data type.
	 *
	 * @param fieldMap
	 *            the field map
	 */
	public UserDataType(final Map<String, String> fieldMap) {
		super(fieldMap);
	}

	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public String getApplication() {
		return this.getValue(Fields.APP);
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.getValue(Fields.EMAIL);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getInsertFields()
	 */
	@Override
	protected Enum<?>[] getInsertFields() {
		return new Fields[] { Fields.EMAIL, Fields.PASSWORD, Fields.USERNAME, Fields.APP };
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.getValue(Fields.PASSWORD);
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.getValue(Fields.USERNAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#toString()
	 */
	@Override
	public String toString() {
		return "UserDataType [toString()=" + super.toString() + "]";
	}

	/**
	 * The Enum Fields.
	 */
	enum Fields {

		/** The email. */
		EMAIL, /** The password. */
		PASSWORD, /** The username. */
		USERNAME, /** The app. */
		APP
	}

}
