/**
 * TransactionDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;

/**
 * The Class TransactionDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class TransactionDataType extends AbstractDataType<TransactionDataType> {
	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(TransactionDataType.class);

	/**
	 * Version with id field.
	 *
	 * @param id
	 *            the id
	 * @param date
	 *            the date
	 * @param amount
	 *            the amount
	 * @param accountId
	 *            the account id
	 * @param categoryId
	 *            the category id
	 * @param description
	 *            the description
	 * @param jive
	 *            the jive
	 * @param fromAccountId
	 *            the from account id
	 * @param toAccountId
	 *            the to account id
	 * @param checkNum
	 *            the check num
	 * @param memo
	 *            the memo
	 * @param payee
	 *            the payee
	 * @return the transaction data type
	 */
	public static TransactionDataType create(final Long id, final String date, final double amount,
	        final Long accountId, final Long categoryId, final String description,
	        final boolean jive, final Long fromAccountId, final Long toAccountId,
	        final String checkNum, final String memo, final String payee) {
		final TransactionDataType create = TransactionDataType.create(date, amount, accountId,
		        categoryId, description, jive, fromAccountId, toAccountId, checkNum, memo, payee);
		create.setValue(Fields.ID, id);
		return create;
	}

	/**
	 * Creates the.
	 *
	 * @param date
	 *            the date
	 * @param amount
	 *            the amount
	 * @param accountId
	 *            the account id
	 * @param categoryId
	 *            the category id
	 * @param description
	 *            the description
	 * @param jive
	 *            the jive
	 * @param fromAccountId
	 *            the from account id
	 * @param toAccountId
	 *            the to account id
	 * @param checkNum
	 *            the check num
	 * @param memo
	 *            the memo
	 * @param payee
	 *            the payee
	 * @return the transaction data type
	 */
	public static TransactionDataType create(final String date, final Double amount,
	        final Long accountId, final Long categoryId, final String description,
	        final Boolean jive, final Long fromAccountId, final Long toAccountId,
	        final String checkNum, final String memo, final String payee) {
		final TransactionDataType transactionDataType = new TransactionDataType();
		transactionDataType.setDate(date);
		transactionDataType.setAmount(Double.valueOf(Math.abs(amount.doubleValue())));
		transactionDataType
		        .setTransactionType(TransactionDataType.getTransactionType(amount.doubleValue()));
		transactionDataType.setAccountId(accountId);
		transactionDataType.setCategoryId(categoryId);
		transactionDataType.setDescription(description);
		transactionDataType.setJive(jive);
		transactionDataType.setFromAccountId(fromAccountId);
		transactionDataType.setToAccountId(toAccountId);
		transactionDataType.setCheckNum(checkNum);
		transactionDataType.setMemo(memo);
		transactionDataType.setPayee(payee);

		TransactionDataType._logger.debug("createCategoriesDataType: " + transactionDataType);
		return transactionDataType;
	}

	/**
	 * Gets the file fields.
	 *
	 * @return the file fields
	 */
	public static Enum<?>[] getFileFields() {
		final Fields[] values = Fields.values();
		final List<Fields> asList = new LinkedList<>(Arrays.asList(values));
		asList.remove(Fields.TRANSACTION_TYPE.ordinal());
		return asList.toArray(new Fields[asList.size()]);
	}

	/**
	 * if amount is negative is WITHDRAWAL else DEPOSIT.
	 *
	 * @param amount
	 *            the amount
	 * @return the transaction type
	 */
	private static Type getTransactionType(final double amount) {
		if (amount < 0) {
			return Type.WITHDRAWAL;
		}
		return Type.DEPOSIT;
	}

	/**
	 * Instantiates a new transaction data type.
	 */
	private TransactionDataType() {
		super();
	}

	/**
	 * Instantiates a new transaction data type.
	 *
	 * @param map
	 *            the map
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public TransactionDataType(final Map<String, String> map) throws ClearcheckbookException {
		super(map);
		if (null == this.getJive()) {
			this.setJive(false);
		}
		this.setTransactionType(this.getTransactionType());
	}

	/**
	 * Instantiates a new transaction data type.
	 *
	 * @param original
	 *            the original
	 */
	public TransactionDataType(final TransactionDataType original) {
		super(original);
	}

	@Override
	public boolean equals(final Object obj) {
		if ((null == obj) || !obj.getClass().equals(this.getClass())) {
			TransactionDataType._logger.info("not equal " + this + " vs " + obj);
			return false;
		}
		final TransactionDataType that = (TransactionDataType) obj;
		if (this.getAmount() != that.getAmount()) {
			return false;
		}
		if (this.getDate() != that.getDate()) {
			return false;
		}
		if (this.getDescription() != that.getDescription()) {
			return false;
		}
		if (this.getCheckNum() != that.getCheckNum()) {
			return false;
		}
		if (this.getMemo() != that.getMemo()) {
			return false;
		}
		return this.getAccountId() == that.getAccountId();
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Long getAccountId() {
		return this.getLongValue(Fields.ACCOUNT_ID);
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return this.getDoubleValue(Fields.AMOUNT);
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public Long getCategoryId() {
		return this.getLongValue(Fields.CATEGORY_ID);
	}

	/**
	 * Gets the check num.
	 *
	 * @return the check num
	 */
	public String getCheckNum() {
		return this.getValue(Fields.CHECK_NUM);
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return this.getValue(Fields.DATE);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.getValue(Fields.DESCRIPTION);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getEditFields()
	 */
	@Override
	protected Enum<?>[] getEditFields() {
		final Fields[] insertFields = new Fields[] { Fields.ID, Fields.DATE, Fields.AMOUNT,
		        Fields.TRANSACTION_TYPE, Fields.ACCOUNT_ID, Fields.CATEGORY_ID, Fields.DESCRIPTION,
		        Fields.JIVE, Fields.CHECK_NUM, Fields.MEMO };
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

	@SuppressWarnings("rawtypes")
	@Override
	protected ArrayList<Enum> getFieldsToIgnoreInEqualsMethod() {
		final ArrayList<Enum> fields = new ArrayList<>();
		fields.add(TransactionDataType.Fields.ID);
<<<<<<< HEAD
		fields.add(TransactionDataType.Fields.SPECIALSTATUS);
		fields.add(TransactionDataType.Fields.JIVE);
		fields.add(TransactionDataType.Fields.ATTACHMENT);
		fields.add(TransactionDataType.Fields.USER_ID);
		fields.add(TransactionDataType.Fields.ADDITIONAL_USER_ID);

		fields.add(TransactionDataType.Fields.TO_ACCOUNT_ID);
		fields.add(TransactionDataType.Fields.FROM_ACCOUNT_ID);

		fields.add(TransactionDataType.Fields.INITIAL_BALANCE);
		fields.add(TransactionDataType.Fields.TRANSACTION_TYPE);
		fields.add(TransactionDataType.Fields.PARENT);
		fields.add(TransactionDataType.Fields.CCPARENT);
		fields.add(TransactionDataType.Fields.RELATED_TRANSFER);
		fields.add(TransactionDataType.Fields.CREATED_AT);
=======
		fields.add(TransactionDataType.Fields.JIVE);
		fields.add(TransactionDataType.Fields.TRANSACTION_TYPE);
		fields.add(TransactionDataType.Fields.PARENT);
		fields.add(TransactionDataType.Fields.RELATED_TRANSFER);
>>>>>>> branch '#12-dont-insert-duplicates' of https://github.com/leonarduk/clearcheckbookjavaapi.git
		return fields;
	}

	/**
	 * Gets the from account id.
	 *
	 * @return the from account id
	 */
	public Long getFromAccountId() {
		return this.getLongValue(NonoutputFields.FROM_ACCOUNT_ID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getInsertFields()
	 */
	@Override
	protected Enum<?>[] getInsertFields() {
		final Enum<?>[] insertFields = new Enum<?>[] { Fields.DATE, Fields.AMOUNT,
		        Fields.TRANSACTION_TYPE, Fields.ACCOUNT_ID, Fields.CATEGORY_ID, Fields.DESCRIPTION,
		        Fields.JIVE, NonoutputFields.FROM_ACCOUNT_ID, NonoutputFields.TO_ACCOUNT_ID,
		        Fields.CHECK_NUM, Fields.MEMO, Fields.PAYEE };
		return insertFields;
	}

	/**
	 * Gets the jive.
	 *
	 * @return the jive
	 */
	public Boolean getJive() {
		return this.getBooleanValue(Fields.JIVE);
	}

	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	public String getMemo() {
		return this.getValue(Fields.MEMO);
	}

	/**
	 * Gets the payee.
	 *
	 * @return the payee
	 */
	public String getPayee() {
		return this.getValue(Fields.PAYEE);
	}

	/**
	 * Gets the to account.
	 *
	 * @return the to account
	 */
	public Long getToAccount() {
		return this.getLongValue(NonoutputFields.TO_ACCOUNT_ID);
	}

	/**
	 * Gets the transaction type.
	 *
	 * @return the transaction type
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public Type getTransactionType() throws ClearcheckbookException {
		return Type.fromString(this.getValue(Fields.TRANSACTION_TYPE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getValues()
	 */
	@Override
	public String[] getValues() throws ClearcheckbookException {
		final Enum<?>[] fields = TransactionDataType.getFileFields();
		final String[] values = new String[fields.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = this.getNonNullValue(fields[i]);
		}
		return values;
	}

	/**
	 * Mark to be deleted.
	 */
	public void markToBeDeleted() {
		this.setValue(Fields.ID, -1 * this.getId());
	}

	/**
	 * Sets the account id.
	 *
	 * @param string
	 *            the new account id
	 */
	public void setAccountId(final Long string) {
		this.setValue(Fields.ACCOUNT_ID, string);
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(final Double amount) {
		this.setValue(Fields.AMOUNT, amount);
	}

	/**
	 * Sets the category id.
	 *
	 * @param string
	 *            the new category id
	 */
	public void setCategoryId(final Long string) {
		this.setValue(Fields.CATEGORY_ID, string);
	}

	/**
	 * Sets the check num.
	 *
	 * @param string
	 *            the new check num
	 */
	public void setCheckNum(final String string) {
		this.setValue(Fields.CHECK_NUM, string);
	}

	/**
	 * Sets the date.
	 *
	 * @param string
	 *            the new date
	 */
	public void setDate(final String string) {
		this.setValue(Fields.DATE, string);
	}

	/**
	 * Sets the description.
	 *
	 * @param string
	 *            the new description
	 */
	public void setDescription(final String string) {
		this.setValue(Fields.DESCRIPTION, string);
	}

	/**
	 * Sets the from account id.
	 *
	 * @param fromAccountId
	 *            the new from account id
	 */
	public void setFromAccountId(final Long fromAccountId) {
		this.setValue(NonoutputFields.FROM_ACCOUNT_ID, fromAccountId);
	}

	/**
	 * Sets the jive.
	 *
	 * @param jive
	 *            the new jive
	 */
	public void setJive(final Boolean jive) {
		this.setValue(Fields.JIVE, jive);
	}

	/**
	 * Sets the memo.
	 *
	 * @param string
	 *            the new memo
	 */
	public void setMemo(final String string) {
		this.setValue(Fields.MEMO, string);
	}

	/**
	 * Sets the payee.
	 *
	 * @param valString
	 *            the new payee
	 */
	public void setPayee(final String valString) {
		this.setValue(Fields.PAYEE, valString);
	}

	/**
	 * Sets the to account id.
	 *
	 * @param toAccountId
	 *            the new to account id
	 */
	public void setToAccountId(final Long toAccountId) {
		this.setValue(NonoutputFields.TO_ACCOUNT_ID, toAccountId);
	}

	/**
	 * Sets the transaction type.
	 *
	 * @param transactionType
	 *            the new transaction type
	 */
	public void setTransactionType(final Type transactionType) {
		this.setValue(Fields.TRANSACTION_TYPE, transactionType);
	}

	/**
	 * This holds the output fields.
	 */
	public enum Fields {

		/** The id. */
		ID, /** The date. */
		DATE, /** The amount. */
		AMOUNT, /** The description. */
		DESCRIPTION, /** The check num. */
		CHECK_NUM, /** The memo. */
		MEMO, /** The payee. */
		PAYEE, /** The account id. */
		ACCOUNT_ID, /** The category id. */
		CATEGORY_ID, /** The jive. */
		JIVE, /** The transaction type. */
		TRANSACTION_TYPE, /** The specialstatus. */
		SPECIALSTATUS, /** The parent. */
		PARENT, /** The related transfer. */
		RELATED_TRANSFER, /** The initial balance. */
		INITIAL_BALANCE, CCPARENT, CREATED_AT, ATTACHMENT, USER_ID, ADDITIONAL_USER_ID, TO_ACCOUNT_ID, FROM_ACCOUNT_ID;
	}

	/**
	 * The Enum NonoutputFields.
	 */
	public enum NonoutputFields {

		/** The from account id. */
		FROM_ACCOUNT_ID, /** The to account id. */
		TO_ACCOUNT_ID;
	}

	/**
	 * The Enum Type.
	 */
	public enum Type {
		/** The withdrawal. */
		WITHDRAWAL,

		/** The deposit. */
		DEPOSIT;

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

		/**
		 * Checks if is member.
		 *
		 * @param key
		 *            the key
		 * @return true, if is member
		 */
		public static boolean isMember(final String key) {
			final Type[] values = Type.values();
			for (final Type value : values) {
				if (value.name().equalsIgnoreCase(key)) {
					return true;
				}
			}
			return false;
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
