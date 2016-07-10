/**
 * AccountDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.utils.NumberUtils;

/**
 * The Class AccountDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class AccountDataType extends AbstractDataType<AccountDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(AccountDataType.class);

	/**
	 * Creates the.
	 *
	 * @param name
	 *            the name
	 * @param type_id
	 *            the type_id
	 * @param initial_balance
	 *            the initial_balance
	 * @return the account data type
	 */
	public static AccountDataType create(final String name, final Type type_id,
	        final double initial_balance) {
		final AccountDataType accountDataType = new AccountDataType();
		accountDataType.setName(name);
		accountDataType.setTypeId(type_id);
		accountDataType.setInitialBalance(initial_balance);

		AccountDataType._logger.debug("createUserDataType: " + name + " " + type_id + " "
		        + initial_balance + " -> " + accountDataType);

		return accountDataType;
	}

	/**
	 * Instantiates a new account data type.
	 */
	private AccountDataType() {
		super();
	}

	/**
	 * Instantiates a new account data type.
	 *
	 * @param map
	 *            the map
	 */
	public AccountDataType(final Map<String, String> map) {
		super(map);
	}

	/**
	 * Extension of the API to take the deposit - withdrawal values to give balance.
	 *
	 * @return the current balance
	 */
	public Double getCurrentBalance() {
		return NumberUtils
		        .formatMoney(this.getDeposit().doubleValue() - this.getWithdrawal().doubleValue());
	}

	/**
	 * Gets the deposit.
	 *
	 * @return the deposit
	 */
	public Double getDeposit() {
		return this.getDoubleValue(Fields.DEPOSIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getEditFields()
	 */
	@Override
	protected Enum<?>[] getEditFields() {
		final Fields[] insertFields = new Fields[] { Fields.ID, Fields.NAME, Fields.TYPE_ID };
		return insertFields;
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
		final Fields[] insertFields = new Fields[] { Fields.NAME, Fields.TYPE_ID,
		        Fields.INITIAL_BALANCE };
		return insertFields;
	}

	/**
	 * Gets the jive deposit.
	 *
	 * @return the jive deposit
	 */
	public Double getJiveDeposit() {
		return this.getDoubleValue(Fields.JIVE_DEPOSIT);
	}

	/**
	 * Gets the jive withdrawal.
	 *
	 * @return the jive withdrawal
	 */
	public Double getJiveWithdrawal() {
		return this.getDoubleValue(Fields.JIVE_WITHDRAWAL);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.getValue(Fields.NAME);
	}

	/**
	 * Gets the type id.
	 *
	 * @return the type id
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public Type getTypeId() throws ClearcheckbookException {
		return Type.fromString(this.getValue(Fields.TYPE_ID));
	}

	/**
	 * Gets the withdrawal.
	 *
	 * @return the withdrawal
	 */
	public Double getWithdrawal() {
		return this.getDoubleValue(Fields.WITHDRAWAL);
	}

	/**
	 * Sets the deposit.
	 *
	 * @param value
	 *            the new deposit
	 */
	public void setDeposit(final double value) {
		this.setValue(Fields.DEPOSIT, value);
	}

	/**
	 * Sets the initial balance.
	 *
	 * @param initial_balance
	 *            the new initial balance
	 */
	private void setInitialBalance(final double initial_balance) {
		this.setValue(Fields.INITIAL_BALANCE, initial_balance);
	}

	/**
	 * Sets the jive deposit.
	 *
	 * @param value
	 *            the new jive deposit
	 */
	public void setJiveDeposit(final Double value) {
		this.setValue(Fields.JIVE_DEPOSIT, value);
	}

	/**
	 * Sets the jive withdrawal.
	 *
	 * @param value
	 *            the new jive withdrawal
	 */
	public void setJiveWithdrawal(final Double value) {
		this.setValue(Fields.JIVE_WITHDRAWAL, value);
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(final String name) {
		this.setValue(Fields.NAME, name);
	}

	/**
	 * Sets the type id.
	 *
	 * @param type_id
	 *            the new type id
	 */
	public void setTypeId(final Type type_id) {
		this.setValue(Fields.TYPE_ID, type_id);
	}

	/**
	 * Sets the withdrawal.
	 *
	 * @param value
	 *            the new withdrawal
	 */
	public void setWithdrawal(final Double value) {
		this.setValue(Fields.WITHDRAWAL, value);
	}

	/**
	 * The Enum Fields.
	 */
	public enum Fields {

		/** The id. */
		ID, /** The name. */
		NAME, /** The type id. */
		TYPE_ID, /** The deposit. */
		DEPOSIT, /** The jive deposit. */
		JIVE_DEPOSIT, /** The withdrawal. */
		WITHDRAWAL, /** The jive withdrawal. */
		JIVE_WITHDRAWAL, /** The initial balance. */
		INITIAL_BALANCE
	}

	/**
	 * The Enum Type.
	 */
	public enum Type {

		/** The unknown. */
		UNKNOWN, /** The cash. */
		CASH, /** The checking. */
		CHECKING, /** The savings. */
		SAVINGS, /** The credit card. */
		CREDIT_CARD, /** The investment. */
		INVESTMENT;

		/**
		 * From ordinal.
		 *
		 * @param ordinal
		 *            the ordinal
		 * @return the type
		 * @throws ClearcheckbookException
		 *             the clearcheckbook exception
		 */
		public static Type fromOrdinal(final int ordinal) throws ClearcheckbookException {
			try {
				return Type.values()[ordinal];
			}
			catch (final Exception e) {
				throw new ClearcheckbookException(ordinal + " is not a valid Type", e);
			}
		};

		/**
		 * From string.
		 *
		 * @param ordinal
		 *            the ordinal
		 * @return the type
		 * @throws ClearcheckbookException
		 *             the clearcheckbook exception
		 */
		public static Type fromString(final String ordinal) throws ClearcheckbookException {
			try {
				final int i = Integer.valueOf(ordinal);
				return Type.values()[i];
			}
			catch (final Exception e) {
				throw new ClearcheckbookException(ordinal + " is not a valid Type", e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return String.valueOf(this.ordinal());
		}
	}

}
