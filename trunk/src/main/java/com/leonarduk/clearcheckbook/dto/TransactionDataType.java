package com.leonarduk.clearcheckbook.dto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
public class TransactionDataType extends AbstractDataType<TransactionDataType> {

	/**
	 * 
	 * This holds the output fields
	 */
	public enum Fields {
		ID, DATE, AMOUNT, DESCRIPTION, CHECK_NUM, MEMO, PAYEE, ACCOUNT_ID, CATEGORY_ID, JIVE, TRANSACTION_TYPE, SPECIALSTATUS, PARENT, RELATED_TRANSFER, INITIAL_BALANCE;
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	public static Enum<?>[] getFileFields() {
		Fields[] values = Fields.values();
		List<Fields> asList = new LinkedList<>(Arrays.asList(values));
		asList.remove(Fields.TRANSACTION_TYPE.ordinal());
		return asList.toArray(new Fields[asList.size()]);
	}

	public enum NonoutputFields {
		FROM_ACCOUNT_ID, TO_ACCOUNT_ID;
	}

	@Override
	public String[] getValues() throws ClearcheckbookException {
		Enum<?>[] fields = getFileFields();
		String[] values = new String[fields.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = getNonNullValue(fields[i]);
		}
		return values;
	}

	public enum Type {
		WITHDRAWAL, DEPOSIT;

		public String toString() {
			return String.valueOf(ordinal());
		};

		public static boolean isMember(String key) {
			Type[] values = values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].name().equalsIgnoreCase(key)) {
					return true;
				}
			}
			return false;
		}

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

	/**
	 * Version with id field.
	 * 
	 * @param id
	 * @param date
	 * @param amount
	 * @param transactionType
	 * @param accountId
	 * @param categoryId
	 * @param description
	 * @param jive
	 * @param fromAccountId
	 * @param toAccountId
	 * @param checkNum
	 * @param memo
	 * @param payee
	 * @return
	 */
	public static TransactionDataType create(Long id, String date,
			double amount, long accountId, long categoryId, String description,
			boolean jive, long fromAccountId, long toAccountId,
			String checkNum, String memo, String payee) {
		TransactionDataType create = TransactionDataType.create(date, amount,
				accountId, categoryId, description, jive, fromAccountId,
				toAccountId, checkNum, memo, payee);
		create.setValue(Fields.ID, id);
		return create;
	}

	public static TransactionDataType create(String date, Double amount,
			Long accountId, Long categoryId, String description, Boolean jive,
			Long fromAccountId, Long toAccountId, String checkNum, String memo,
			String payee) {
		TransactionDataType transactionDataType = new TransactionDataType();
		transactionDataType.setDate(date);
		transactionDataType.setAmount(amount);
		transactionDataType.setTransactionType(getTransactionType(amount));
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

	/**
	 * if amount is negative is WITHDRAWAL else DEPOSIT
	 * 
	 * @param amount
	 * @return
	 */
	private static Type getTransactionType(double amount) {
		if (amount < 0) {
			return Type.WITHDRAWAL;
		}
		return Type.DEPOSIT;
	}

	private TransactionDataType() {
		super();
	}

	public TransactionDataType(TransactionDataType original){
		super(original);
	}
	public TransactionDataType(Map<String, String> map)
			throws ClearcheckbookException {
		super(map);
		if (null == getJive()) {
			setJive(false);
		}
		setTransactionType(getTransactionType(getAmount()));
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
		return getLongValue(NonoutputFields.FROM_ACCOUNT_ID);
	}

	protected Enum<?>[] getInsertFields() {
		Enum<?>[] insertFields = new Enum<?>[] { Fields.DATE, Fields.AMOUNT,
				Fields.TRANSACTION_TYPE, Fields.ACCOUNT_ID, Fields.CATEGORY_ID,
				Fields.DESCRIPTION, Fields.JIVE,
				NonoutputFields.FROM_ACCOUNT_ID, NonoutputFields.TO_ACCOUNT_ID,
				Fields.CHECK_NUM, Fields.MEMO, Fields.PAYEE };
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
		return getLongValue(NonoutputFields.TO_ACCOUNT_ID);
	}

	public Type getTransactionType() throws ClearcheckbookException {
		return Type.fromString(getValue(Fields.TRANSACTION_TYPE));
	}

	public void setAccountId(Long string) {
		setValue(Fields.ACCOUNT_ID, string);
	}

	public void setAmount(Double amount) {
		setValue(Fields.AMOUNT, amount);
	}

	public void setCategoryId(Long string) {
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

	public void setFromAccountId(Long fromAccountId) {
		setValue(NonoutputFields.FROM_ACCOUNT_ID, fromAccountId);
	}

	public void setJive(Boolean jive) {
		setValue(Fields.JIVE, jive);
	}

	public void setMemo(String string) {
		setValue(Fields.MEMO, string);
	}

	public void setPayee(String valString) {
		setValue(Fields.PAYEE, valString);
	}

	public void setToAccountId(Long toAccountId) {
		setValue(NonoutputFields.TO_ACCOUNT_ID, toAccountId);
	}

	public void setTransactionType(Type transactionType) {
		setValue(Fields.TRANSACTION_TYPE, transactionType);
	}

	public void markToBeDeleted() {
		setValue(Fields.ID, -1 * getId());
	}

}
