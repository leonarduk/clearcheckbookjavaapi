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
public class UserDataType extends AbstractDataType {

	enum Fields {
		EMAIL, PASSWORD, USERNAME, APP
	}

	private static final Logger _logger = Logger.getLogger(UserDataType.class);

	public static UserDataType create(String username, String email,
			String password) {
		return UserDataType.create(username, email, password, "JAVA");
	}

	/**
	 * Create user passing the optional application parameter.
	 * 
	 * @param username
	 * @param email
	 * @param password
	 * @param application
	 * @return
	 */
	public static UserDataType create(String username, String email,
			String password, String application) {
		UserDataType userDataType = new UserDataType();
		userDataType.addField(Fields.EMAIL, email);
		userDataType.addField(Fields.PASSWORD, password);
		userDataType.addField(Fields.USERNAME, username);
		userDataType.addField(Fields.APP, application);

		_logger.debug("Created: " + userDataType);
		return userDataType;
	}

	private UserDataType() {
	}

	public UserDataType(Map<String, String> fieldMap) {
		super(fieldMap);
	}

	public String getApplication() {
		return getValue(Fields.APP);
	}

	public String getEmail() {
		return getValue(Fields.EMAIL);
	}

	@Override
	protected Enum<?>[] getInsertFields() {
		return new Fields[] { Fields.EMAIL, Fields.PASSWORD, Fields.USERNAME,
				Fields.APP };
	}

	public String getPassword() {
		return getValue(Fields.PASSWORD);
	}

	public String getUsername() {
		return getValue(Fields.USERNAME);
	}

	@Override
	public String toString() {
		return "UserDataType [toString()=" + super.toString() + "]";
	}

}
