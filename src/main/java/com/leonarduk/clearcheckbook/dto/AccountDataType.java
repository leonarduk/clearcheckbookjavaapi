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
public class AccountDataType extends AbstractDataType<AccountDataType> {

	private static final Logger _logger = Logger
			.getLogger(AccountDataType.class);

	public enum Fields {
		ID, NAME, TYPE_ID, DEPOSIT, JIVE_DEPOSIT, WITHDRAWAL, JIVE_WITHDRAWAL, INITIAL_BALANCE
	}

	public enum Type {
		UNKNOWN, CASH, CHECKING, SAVINGS, CREDIT_CARD, INVESTMENT;

		public String toString() {
			return String.valueOf(ordinal());
		};

		public static Type fromString(String ordinal)
				throws ClearcheckbookException {
			try {
				int i = Integer.valueOf(ordinal);
				return Type.values()[i];
			} catch (Exception e) {
				throw new ClearcheckbookException(ordinal
						+ " is not a valid Type", e);
			}
		}

		public static Type fromOrdinal(int ordinal)
				throws ClearcheckbookException {
			try {
				return Type.values()[ordinal];
			} catch (Exception e) {
				throw new ClearcheckbookException(ordinal
						+ " is not a valid Type", e);
			}
		}
	}

	@Override
	protected Enum<?>[] getEditFields() {
		Fields[] insertFields = new Fields[] { Fields.ID, Fields.NAME,
				Fields.TYPE_ID };
		return insertFields;
	}

	@Override
	protected Enum<?>[] getInsertFields() {
		Fields[] insertFields = new Fields[] { Fields.NAME, Fields.TYPE_ID,
				Fields.INITIAL_BALANCE };
		return insertFields;
	}

	public AccountDataType(Map<String, String> map) {
		super(map);
	}

	private AccountDataType() {
		super();
	}

	/**
	 * 
	 * @param name
	 * @param type_id
	 * @param initial_balance
	 * @return
	 */
	public static AccountDataType create(String name, Type type_id,
			double initial_balance) {
		AccountDataType accountDataType = new AccountDataType();
		accountDataType.setName(name);
		accountDataType.setTypeId(type_id);
		accountDataType.setInitialBalance(initial_balance);

		_logger.debug("createUserDataType: " + name + " " + type_id + " "
				+ initial_balance + " -> " + accountDataType);

		return accountDataType;
	}

	private void setInitialBalance(double initial_balance) {
		setValue(Fields.INITIAL_BALANCE, initial_balance);
	}

	public String getName() {
		return getValue(Fields.NAME);
	}

	public void setName(String name) {
		setValue(Fields.NAME, name);
	}

	public Type getTypeId() throws ClearcheckbookException {
		return Type.fromString(getValue(Fields.TYPE_ID));
	}

	public void setTypeId(Type type_id) {
		setValue(Fields.TYPE_ID, type_id);
	}

	public Double getDeposit() {
		return getDoubleValue(Fields.DEPOSIT);
	}

	public void setDeposit(double value) {
		setValue(Fields.DEPOSIT, value);
	}

	public Double getWithdrawal() {
		return getDoubleValue(Fields.WITHDRAWAL);
	}

	public void setWithdrawal(Double value) {
		setValue(Fields.WITHDRAWAL, value);
	}

	public Double getJiveDeposit() {
		return getDoubleValue(Fields.JIVE_DEPOSIT);
	}

	public Double getJiveWithdrawal() {
		return getDoubleValue(Fields.JIVE_WITHDRAWAL);
	}

	public void setJiveDeposit(Double value) {
		setValue(Fields.JIVE_DEPOSIT, value);
	}

	public void setJiveWithdrawal(Double value) {
		setValue(Fields.JIVE_WITHDRAWAL, value);
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

}
