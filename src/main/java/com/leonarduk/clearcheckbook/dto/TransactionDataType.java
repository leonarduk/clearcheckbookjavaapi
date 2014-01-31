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
public class TransactionDataType extends AbstractDataType {

	enum Fields {
		ID, DATE, AMOUNT, TRANSACTION_TYPE, DESCRIPTION, ACCOUNT_ID, CATEGORY_ID, JIVE, SPECIALSTATUS, PARENT, RELATED_TRANSFER, FROM_ACCOUNT_ID, TO_ACCOUNT_ID, CHECK_NUM, MEMO, PAYEE, INITIAL_BALANCE
	}

	public enum Type {
		WITHDRAWAL, DEPOSIT;

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

	private static final Logger _logger = Logger
			.getLogger(TransactionDataType.class);

	public static TransactionDataType create(String date, double amount,
			Type transactionType, long accountId, long categoryId,
			String description, boolean jive, long fromAccountId,
			long toAccountId, String checkNum, String memo, String payee) {
		TransactionDataType transactionDataType = new TransactionDataType();
		transactionDataType.setDate(date);
		transactionDataType.setAmount(amount);
		transactionDataType.setTransactionType(transactionType);
		transactionDataType.setAccountId(accountId);
		transactionDataType.setCategoryId(categoryId);
		transactionDataType.setDescription(description);
		transactionDataType.setJive(jive);
		transactionDataType.setFromAccountId(fromAccountId);
		transactionDataType.setToAccountId(toAccountId);
		transactionDataType.setCheckNum(checkNum);
		transactionDataType.setMemo(memo);
		transactionDataType.setPayee(payee);

		_logger.debug("createCategoriesDataType: " + transactionDataType);
		return transactionDataType;
	}

	private TransactionDataType() {
		super();
	}

	public TransactionDataType(Map<String, String> map) {
		super(map);
	}

	public Long getAccountId() {
		return getLongValue(Fields.ACCOUNT_ID);
	}

	public Double getAmount() {
		return getDoubleValue(Fields.AMOUNT);
	}

	public Long getCategoryId() {
		return getLongValue(Fields.CATEGORY_ID);
	}

	public String getCheckNum() {
		return getValue(Fields.CHECK_NUM);
	}

	public String getDate() {
		return getValue(Fields.DATE);
	}

	public String getDescription() {
		return getValue(Fields.DESCRIPTION);
	}

	@Override
	protected Enum<?>[] getEditFields() {
		Fields[] insertFields = new Fields[] { Fields.ID, Fields.DATE,
				Fields.AMOUNT, Fields.TRANSACTION_TYPE, Fields.ACCOUNT_ID,
				Fields.CATEGORY_ID, Fields.DESCRIPTION, Fields.JIVE,
				Fields.CHECK_NUM, Fields.MEMO };
		return insertFields;
	}

	public Long getFromAccountId() {
		return getLongValue(Fields.FROM_ACCOUNT_ID);
	}

	protected Enum<?>[] getInsertFields() {
		Fields[] insertFields = new Fields[] { Fields.DATE, Fields.AMOUNT,
				Fields.TRANSACTION_TYPE, Fields.ACCOUNT_ID, Fields.CATEGORY_ID,
				Fields.DESCRIPTION, Fields.JIVE, Fields.FROM_ACCOUNT_ID,
				Fields.TO_ACCOUNT_ID, Fields.CHECK_NUM, Fields.MEMO,
				Fields.PAYEE };
		return insertFields;
	}

	public Boolean getJive() {
		return getBooleanValue(Fields.JIVE);
	}

	public String getMemo() {
		return getValue(Fields.MEMO);
	}

	public String getPayee() {
		return getValue(Fields.PAYEE);
	}

	public Long getToAccount() {
		return getLongValue(Fields.TO_ACCOUNT_ID);
	}

	public Type getTransactionType() throws ClearcheckbookException {
		return Type.fromString(getValue(Fields.TRANSACTION_TYPE));
	}

	public void setAccountId(long string) {
		setValue(Fields.ACCOUNT_ID, string);
	}

	public void setAmount(double amount) {
		setValue(Fields.AMOUNT, amount);
	}

	public void setCategoryId(long string) {
		setValue(Fields.CATEGORY_ID, string);
	}

	public void setCheckNum(String string) {
		setValue(Fields.CHECK_NUM, string);
	}

	public void setDate(String string) {
		setValue(Fields.DATE, string);
	}

	public void setDescription(String string) {
		setValue(Fields.DESCRIPTION, string);
	}

	public void setFromAccountId(long fromAccountId) {
		setValue(Fields.FROM_ACCOUNT_ID, fromAccountId);
	}

	public void setJive(boolean jive) {
		setValue(Fields.JIVE, jive);
	}

	public void setMemo(String string) {
		setValue(Fields.MEMO, string);
	}

	public void setPayee(String valString) {
		setValue(Fields.PAYEE, valString);
	}

	public void setToAccountId(long toAccountId) {
		setValue(Fields.TO_ACCOUNT_ID, toAccountId);
	}

	public void setTransactionType(Type transactionType) {
		setValue(Fields.TRANSACTION_TYPE, transactionType);
	}

}
