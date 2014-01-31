package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AccountDataType.Type;

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
public class LimitDataType extends AbstractDataType {

	private static final Logger _logger = Logger.getLogger(LimitDataType.class);

	enum Fields {
		ID, NAME, AMOUNT, ACCOUNT_ID, CATEGORY_ID, SPENT, ROLLOVER, RESET_DAY, TRANSFER, DEPOSIT, DURATION, START_DATE, THIS_START_DATE, THIS_END_DATE, ORIGINAL_LIMIT
	}

	public enum Duration {
		WEEKLY, BI_WEEKLY, MONTHLY, QUARTERLY, SEMI_ANNUALLY, ANNUALLY;

		public String toString() {
			return String.valueOf(ordinal());
		};

		public static Duration fromString(String ordinal)
				throws ClearcheckbookException {
			try {
				int i = Integer.valueOf(ordinal);
				return Duration.values()[i];
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

	public LimitDataType(Map<String, String> map) {
		super(map);
	}

	@Override
	protected Enum<?>[] getEditFields() {
		return new Fields[] { Fields.ACCOUNT_ID, Fields.CATEGORY_ID,
				Fields.AMOUNT, Fields.DURATION, Fields.RESET_DAY,
				Fields.START_DATE, Fields.ROLLOVER, Fields.TRANSFER,
				Fields.DEPOSIT };
	}

	@Override
	protected Enum<?>[] getInsertFields() {
		return new Fields[] { Fields.ACCOUNT_ID, Fields.CATEGORY_ID,
				Fields.AMOUNT, Fields.DURATION, Fields.RESET_DAY,
				Fields.START_DATE, Fields.ROLLOVER, Fields.TRANSFER,
				Fields.DEPOSIT };
	}

	private LimitDataType() {
		super();
	}

	public static LimitDataType create(long accountId, long categoryId,
			int amount, Duration duration, int reset_day,
			String start_date, boolean rollover, boolean transfer,
			boolean deposit) {
		LimitDataType limitDataType = new LimitDataType();
		limitDataType.setAccountId(accountId);
		limitDataType.setCategoryId(categoryId);
		limitDataType.setAmount(amount);
		limitDataType.setDuration(duration);
		limitDataType.setReset_day(reset_day);
		limitDataType.setStart_date(start_date);
		limitDataType.setRollover(rollover);
		limitDataType.setTransfer(transfer);
		limitDataType.setDeposit(deposit);

		_logger.debug("create: " + limitDataType);
		return limitDataType;
	}

	public long getAccountId() {
		return getLongValue(Fields.ACCOUNT_ID);
	}

	public void setAccountId(long accountId) {
		setValue(Fields.ACCOUNT_ID, accountId);
	}

	public long getCategoryId() {
		return getLongValue(Fields.CATEGORY_ID);
	}

	public void setCategoryId(long categoryId) {
		setValue(Fields.CATEGORY_ID, categoryId);
	}

	public Integer getAmount() {
		return getIntegerValue(Fields.AMOUNT);
	}

	public void setAmount(int amount) {
		setValue(Fields.AMOUNT, amount);
	}

	public Duration getDuration() throws ClearcheckbookException {
		return Duration.fromString(getValue(Fields.DURATION));
	}

	public String getDurationName() {
		return Duration.valueOf(getValue(Fields.DURATION)).name();
	}

	public void setDuration(Duration duration) {
		setValue(Fields.DURATION, String.valueOf(duration.ordinal()));
	}

	public int getReset_day() {
		return getIntegerValue(Fields.RESET_DAY);
	}

	public void setReset_day(int reset_day) {
		setValue(Fields.RESET_DAY, reset_day);
	}

	public String getStart_date() {
		return getValue(Fields.START_DATE);
	}

	public void setStart_date(String start_date) {
		setValue(Fields.START_DATE, start_date);
	}

	public Boolean getRollover() {
		return getBooleanValue(Fields.ROLLOVER);
	}

	public void setRollover(boolean rollover) {
		setValue(Fields.ROLLOVER, rollover);
	}

	public Boolean getTransfer() {
		return getBooleanValue(Fields.TRANSFER);
	}

	public void setTransfer(boolean transfer) {
		setValue(Fields.TRANSFER, transfer);
	}

	public Boolean getDeposit() {
		return getBooleanValue(Fields.DEPOSIT);
	}

	public void setDeposit(boolean deposit) {
		setValue(Fields.DEPOSIT, deposit);
	}
}
