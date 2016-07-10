/**
 * LimitDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AccountDataType.Type;

/**
 * The Class LimitDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class LimitDataType extends AbstractDataType<LimitDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(LimitDataType.class);

	/**
	 * Creates the.
	 *
	 * @param accountId
	 *            the account id
	 * @param categoryId
	 *            the category id
	 * @param amount
	 *            the amount
	 * @param duration
	 *            the duration
	 * @param reset_day
	 *            the reset_day
	 * @param start_date
	 *            the start_date
	 * @param rollover
	 *            the rollover
	 * @param transfer
	 *            the transfer
	 * @param deposit
	 *            the deposit
	 * @return the limit data type
	 */
	public static LimitDataType create(final long accountId, final long categoryId,
	        final int amount, final Duration duration, final int reset_day, final String start_date,
	        final boolean rollover, final boolean transfer, final boolean deposit) {
		final LimitDataType limitDataType = new LimitDataType();
		limitDataType.setAccountId(accountId);
		limitDataType.setCategoryId(categoryId);
		limitDataType.setAmount(amount);
		limitDataType.setDuration(duration);
		limitDataType.setReset_day(reset_day);
		limitDataType.setStart_date(start_date);
		limitDataType.setRollover(rollover);
		limitDataType.setTransfer(transfer);
		limitDataType.setDeposit(deposit);

		LimitDataType._logger.debug("create: " + limitDataType);
		return limitDataType;
	}

	/**
	 * Instantiates a new limit data type.
	 */
	private LimitDataType() {
		super();
	}

	/**
	 * Instantiates a new limit data type.
	 *
	 * @param map
	 *            the map
	 */
	public LimitDataType(final Map<String, String> map) {
		super(map);
		this.setStart_date(this.getStart_date());
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public long getAccountId() {
		return this.getLongValue(Fields.ACCOUNT_ID);
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Integer getAmount() {
		Integer limit = this.getIntegerValue(Fields.AMOUNT);
		if (null == limit) {
			limit = this.getIntegerValue(Fields.LIMIT_AMOUNT);
		}
		return limit;
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public long getCategoryId() {
		return this.getLongValue(Fields.CATEGORY_ID);
	}

	/**
	 * Gets the deposit.
	 *
	 * @return the deposit
	 */
	public Boolean getDeposit() {
		return this.getBooleanValue(Fields.DEPOSIT);
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public Duration getDuration() throws ClearcheckbookException {
		return Duration.fromString(this.getValue(Fields.DURATION));
	}

	/**
	 * Gets the duration name.
	 *
	 * @return the duration name
	 */
	public String getDurationName() {
		return Duration.valueOf(this.getValue(Fields.DURATION)).name();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getEditFields()
	 */
	@Override
	protected Enum<?>[] getEditFields() {
		return new Fields[] { Fields.ID, Fields.AMOUNT, Fields.DURATION, Fields.RESET_DAY,
		        Fields.START_DATE, Fields.ROLLOVER, Fields.TRANSFER, Fields.DEPOSIT };
	}

	/**
	 * Gets the end_date.
	 *
	 * @return the end_date
	 */
	public String getEnd_date() {
		final String startDate = this.getValue(Fields.THIS_END_DATE);
		return startDate;
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
		return new Fields[] { Fields.ACCOUNT_ID, Fields.CATEGORY_ID, Fields.AMOUNT, Fields.DURATION,
		        Fields.RESET_DAY, Fields.START_DATE, Fields.ROLLOVER, Fields.TRANSFER,
		        Fields.DEPOSIT };
	}

	/**
	 * Gets the reset_day.
	 *
	 * @return the reset_day
	 */
	public int getReset_day() {
		return this.getIntegerValue(Fields.RESET_DAY);
	}

	/**
	 * Gets the rollover.
	 *
	 * @return the rollover
	 */
	public Boolean getRollover() {
		return this.getBooleanValue(Fields.ROLLOVER);
	}

	/**
	 * Gets the start_date.
	 *
	 * @return the start_date
	 */
	public String getStart_date() {
		String startDate = this.getValue(Fields.START_DATE);
		if (null == startDate) {
			startDate = this.getValue(Fields.THIS_START_DATE);
		}
		if (null != startDate) {
			return startDate.substring(0, 10);
		}
		return null;
	}

	/**
	 * Gets the transfer.
	 *
	 * @return the transfer
	 */
	public Boolean getTransfer() {
		return this.getBooleanValue(Fields.TRANSFER);
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(final long accountId) {
		this.setValue(Fields.ACCOUNT_ID, accountId);
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(final int amount) {
		this.setValue(Fields.AMOUNT, amount);
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId
	 *            the new category id
	 */
	public void setCategoryId(final long categoryId) {
		this.setValue(Fields.CATEGORY_ID, categoryId);
	}

	/**
	 * Sets the deposit.
	 *
	 * @param deposit
	 *            the new deposit
	 */
	public void setDeposit(final boolean deposit) {
		this.setValue(Fields.DEPOSIT, deposit);
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(final Duration duration) {
		this.setValue(Fields.DURATION, String.valueOf(duration.ordinal()));
	}

	/**
	 * Sets the reset_day.
	 *
	 * @param reset_day
	 *            the new reset_day
	 */
	public void setReset_day(final int reset_day) {
		this.setValue(Fields.RESET_DAY, reset_day);
	}

	/**
	 * Sets the rollover.
	 *
	 * @param rollover
	 *            the new rollover
	 */
	public void setRollover(final boolean rollover) {
		this.setValue(Fields.ROLLOVER, rollover);
	}

	/**
	 * Sets the start_date.
	 *
	 * @param start_date
	 *            the new start_date
	 */
	public void setStart_date(final String start_date) {
		this.setValue(Fields.START_DATE, start_date);
	}

	/**
	 * Sets the transfer.
	 *
	 * @param transfer
	 *            the new transfer
	 */
	public void setTransfer(final boolean transfer) {
		this.setValue(Fields.TRANSFER, transfer);
	}

	/**
	 * The Enum Duration.
	 */
	public enum Duration {

		/** The weekly. */
		WEEKLY, /** The bi weekly. */
		BI_WEEKLY, /** The monthly. */
		MONTHLY, /** The quarterly. */
		QUARTERLY, /** The semi annually. */
		SEMI_ANNUALLY, /** The annually. */
		ANNUALLY;

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
		 * @return the duration
		 * @throws ClearcheckbookException
		 *             the clearcheckbook exception
		 */
		public static Duration fromString(final String ordinal) throws ClearcheckbookException {
			try {
				final int i = Integer.valueOf(ordinal);
				return Duration.values()[i];
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

	/**
	 * The Enum Fields.
	 */
	public enum Fields {

		/** The id. */
		ID, /** The name. */
		NAME, /** The amount. */
		AMOUNT, /** The account id. */
		ACCOUNT_ID, /** The category id. */
		CATEGORY_ID, /** The spent. */
		SPENT, /** The rollover. */
		ROLLOVER, /** The reset day. */
		RESET_DAY, /** The transfer. */
		TRANSFER, /** The deposit. */
		DEPOSIT, /** The duration. */
		DURATION, /** The start date. */
		START_DATE, /** The this start date. */
		THIS_START_DATE, /** The this end date. */
		THIS_END_DATE, /** The original limit. */
		ORIGINAL_LIMIT, /** The limit amount. */
		LIMIT_AMOUNT
	}
}
