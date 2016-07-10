/**
 * TransactionCall
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.TransactionDataType;

/**
 * The Class TransactionCall.
 */
public class TransactionCall extends AbstractCall<TransactionDataType>
        implements BulkProcessable<TransactionDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(TransactionCall.class);

	/** The Constant TYPE. */
	public static final String TYPE = "transaction";

	/**
	 * Instantiates a new transaction call.
	 *
	 * @param connection
	 *            the connection
	 */
	public TransactionCall(final ClearCheckBookConnection connection) {
		super(connection, TransactionDataType.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#bulkProcess(java.util.List)
	 */
	@Override
	public List<String> bulkProcess(final List<TransactionDataType> dataTypeList)
	        throws ClearcheckbookException {
		return super.bulkProcess(dataTypeList);
	}

	/**
	 * Delete a specific transaction for the current user. <br>
	 * Method: delete <br>
	 * Call: transaction
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/transaction/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the transaction to delete
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful delete or false/null on fail
	 *
	 * @param input
	 *            the input
	 * @return transaction
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public boolean delete(final ParsedNameValuePair input) throws ClearcheckbookException {
		return super.delete(input);
	}

	/**
	 * Edit a specific transaction for the current user. <br>
	 * Method: put <br>
	 * Call: transaction
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/transaction/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the transaction being edited <br>
	 * date Required The date for the transaction (Formatted as yyyy-mm-dd) <br>
	 * amount Required Float value formatted as (xxxx.xx) <br>
	 * transaction_type Required Whether the transaction is a deposit or withdrawal (0= Withdrawal,
	 * 1= Deposit) <br>
	 * account_id Required The id of the account for this transaction (0 if no account) <br>
	 * category_id Required The id of the category for this transaction (0 if no category) <br>
	 * description Required Text for the description of this transaction <br>
	 * jive Optional Optional value to set whether or not the transaction is jived (0= Un-jived, 1=
	 * Jived). If you just want to jive a transaction, use editJive() instead. <br>
	 * check_num Optional If the user has a premium membership and has the check_num field enabled,
	 * the system will accept a check number <br>
	 * memo Optional If the user has a premium membership and has the memo field enabled, the system
	 * will accept a memo <br>
	 * payee Optional If the user has a premium membership and has the payee field enabled, the
	 * system will accept a payee
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful edit or false/null on fail.
	 *
	 * @param input
	 *            the input
	 * @return transaction
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public boolean edit(final TransactionDataType input) throws ClearcheckbookException {
		return super.edit(input);
	}

	/**
	 * Jive or Un-jive a specific transaction for the current user. <br>
	 * Method: put <br>
	 * Call: jive
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/jive/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the transaction being updated <br>
	 * status Required The status of the jive (0= Un-jived, 1= Jived)
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful jive update or false/null on fail
	 *
	 * @param id
	 *            the id
	 * @param jiveStatus
	 *            the jive status
	 * @return true, if successful
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public boolean editJive(final ParsedNameValuePair id, final boolean jiveStatus)
	        throws ClearcheckbookException {
		final String status = (jiveStatus) ? "1" : "0";
		final ParsedNameValuePair statusParameter = new ParsedNameValuePair("status", status);
		try {
			final String exitStatus = this.getConnection().putPage("jive",
			        new ParsedNameValuePair[] { id, statusParameter });
			return Boolean.valueOf(exitStatus);
		}
		catch (final IOException e) {
			throw new ClearcheckbookException(
			        "failed to edit jive for transaction id: " + id.getValue(), e);
		}
	}

	/**
	 * Returns information about a specific transaction. <br>
	 * Method: get <br>
	 * Call: transaction
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/transaction/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the transaction you want
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The id of the transaction <br>
	 * date The date for the transaction (formatted as yyyy-mm-dd) <br>
	 * amount Float value for the amount of the transaction. <br>
	 * transaction_type Whether this transaction is a deposit or withdrawal (0=withdrawal,
	 * 1=deposit) <br>
	 * description The description for this transaction <br>
	 * account_id The account associated with this transaction. 0 = No account <br>
	 * category_id The category associated with this transaction. 0 = No category <br>
	 * jive Whether or not this transaction is jived (0= Not jived, 1= Jived) <br>
	 * specialstatus Text that is empty or says Transfer or Split <br>
	 * parent If this is a split from a split transaction, this is the id of the parent transaction
	 * <br>
	 * related_transfer A unique integer corresponding to its related transfer. <br>
	 * check_num Text from the check number field <br>
	 * memo Text from the memo field <br>
	 * payee Text from the payee field <br>
	 * initial_balance Boolean for whether or not this was set up as an initial balance for an
	 * account
	 *
	 * @param id
	 *            the id
	 * @return transaction
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public TransactionDataType get(final ParsedNameValuePair id) throws ClearcheckbookException {
		final TransactionDataType transactionDataType = super.get(id);
		TransactionCall._logger.debug("get: " + transactionDataType);
		return transactionDataType;

	}

	/**
	 * Returns an array of transactions for the current user <br>
	 * Method: get <br>
	 * Call: transactions
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/transactions/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * account_id Optional Get transactions for a specific account <br>
	 * page Optional integer used for limiting the number of transactions. Used in conjunction with
	 * limit. (eg: page=2, limit=10) <br>
	 * limit Optional The number of transactions you want to receive. Must be used with the page
	 * variable.
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The id of the transaction <br>
	 * date The date for the transaction (formatted as yyyy-mm-dd) <br>
	 * amount Float value for the amount of the transaction. <br>
	 * transaction_type Whether this transaction is a deposit or withdrawal (0=withdrawal,
	 * 1=deposit) <br>
	 * description The description for this transaction <br>
	 * account_id The account associated with this transaction. 0 = No account <br>
	 * category_id The category associated with this transaction. 0 = No category <br>
	 * jive Whether or not this transaction is jived (0= Not jived, 1= Jived) <br>
	 * specialstatus Text that is empty or says Transfer or Split <br>
	 * parent If this is a split from a split transaction, this is the id of the parent transaction
	 * <br>
	 * related_transfer A unique integer corresponding to its related transfer. <br>
	 * check_num Text from the check number field <br>
	 * memo Text from the memo field <br>
	 * payee Text from the payee field <br>
	 * initial_balance Boolean for whether or not this was set up as an initial balance for an
	 * account
	 *
	 * @return List of transactions
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public List<TransactionDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	/**
	 * Gets the all.
	 *
	 * @param account
	 *            the account
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getAll(final AccountDataType account)
	        throws ClearcheckbookException {
		return super.getAll(new ParsedNameValuePair("account_id", String.valueOf(account.getId())));
	}

	/**
	 * Gets the all.
	 *
	 * @param page
	 *            the page
	 * @param limit
	 *            the limit
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getAll(final int page, final int limit)
	        throws ClearcheckbookException {
		return super.getAll(AbstractDataType.getPageParameter(page),
		        AbstractDataType.getLimitParameter(limit));
	}

	/**
	 * Gets the all.
	 *
	 * @param accountId
	 *            the account id
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getAll(final long accountId) throws ClearcheckbookException {
		return super.getAll(AbstractDataType.getIdParameter(accountId));
	}

	/**
	 * Gets the all.
	 *
	 * @param accountId
	 *            the account id
	 * @param page
	 *            the page
	 * @param limit
	 *            the limit
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<TransactionDataType> getAll(final long accountId, final int page, final int limit)
	        throws ClearcheckbookException {
		return super.getAll(AbstractDataType.getIdParameter(accountId),
		        AbstractDataType.getPageParameter(page), AbstractDataType.getLimitParameter(limit));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getUrlSuffix()
	 */
	@Override
	protected String getUrlSuffix() {
		return TransactionCall.TYPE;
	}

	/**
	 * Inserts a transaction for the current user <br>
	 * Method: post <br>
	 * Call: transaction
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/transaction/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * date Required The date for the transaction (Formatted as yyyy-mm-dd) <br>
	 * amount Required Float value formatted as (xxxx.xx) <br>
	 * transaction_type Required Whether the transaction is a deposit or withdrawal (0= Withdrawal,
	 * 1= Deposit, 3= Transfer) <br>
	 * account_id Required* The id of the account for this transaction (0 if no account). * Not
	 * required if transaction_type=3 <br>
	 * category_id Required The id of the category for this transaction (0 if no category) <br>
	 * description Required Text for the description of this transaction <br>
	 * jive Optional If the transaction being added should be marked as jived. (0= un-jived, 1=
	 * jived) <br>
	 * from_account_id Optional If this transaction is a transfer, this is the id of the account
	 * you're transferring from. <br>
	 * to_account_id Optional If this transaction is a transfer, this is the id of the account
	 * you're transferring to. <br>
	 * check_num Optional If the user has a premium membership and has the check_num field enabled,
	 * the system will accept a check number <br>
	 * memo Optional If the user has a premium membership and has the memo field enabled, the system
	 * will accept a memo <br>
	 * payee Optional If the user has a premium membership and has the payee field enabled, the
	 * system will accept a payee
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * Multiple Responses* Returns the id of the newly created transaction, true (if the transaction
	 * is a transfer) or false/null on fail.
	 *
	 * @param input
	 *            the input
	 * @return transaction
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public String insert(final TransactionDataType input) throws ClearcheckbookException {
		return super.insert(input);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.leonarduk.clearcheckbook.calls.AbstractCall#process(com.leonarduk.clearcheckbook.dto.
	 * AbstractDataType)
	 */
	@Override
	public String process(final TransactionDataType dataType) throws ClearcheckbookException {
		return super.process(dataType);
	}
}
