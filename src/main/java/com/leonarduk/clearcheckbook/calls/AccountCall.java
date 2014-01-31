package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.AccountDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

public class AccountCall extends AbstractCall<AccountDataType> {

	private static final Logger _logger = Logger.getLogger(AccountCall.class);

	public static final String TYPE = "account";

	public AccountCall(String username, String password) {
		super(TYPE, username, password);
	}

	/**
	 * Returns an array of all accounts and account balances for this user. <br>
	 * Method: get <br>
	 * Call: accounts
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/accounts/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * is_overview Optional Should be set to "true" if you want to return all
	 * accounts that just have a balance (Including transactions not assigned to
	 * an account)
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The account id <br>
	 * name the name of the account <br>
	 * type_id the type of account (1= Cash, 2= Checking Account, 3= Savings
	 * Account, 4= Credit Card, 5= Investment) <br>
	 * deposit the float value containing the amount of deposits in this
	 * account. <br>
	 * jive_deposit the float value containing the amount of jived deposits in
	 * this account. <br>
	 * withdrawal the float value containing the amount of withdrawals in this
	 * account. <br>
	 * jive_withdrawal the float value containing the amount of jived
	 * withdrawals in this account.
	 * 
	 * @Override {@link AbstractCall#getAll()}
	 * @return returns a list of accounts.
	 */
	public List<AccountDataType> getAll() throws ClearcheckbookException {
		return super.getAll();
	}

	/**
	 * 
	 Returns an array of information for a single account <br>
	 * Method: get <br>
	 * Call: account
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/account/
	 * <p>
	 * Parameters: vParameter Required Description <br>
	 * id Required the id of the account you're getting information for
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The account id <br>
	 * name the name of the account <br>
	 * type_id the type of account (1= Cash, 2= Checking Account, 3= Savings
	 * Account, 4= Credit Card, 5= Investment) <br>
	 * deposit the float value containing the amount of deposits in this
	 * account. <br>
	 * jive_deposit the float value containing the amount of jived deposits in
	 * this account. <br>
	 * withdrawal the float value containing the amount of withdrawals in this
	 * account. <br>
	 * jive_withdrawal the float value containing the amount of jived
	 * withdrawals in this account.
	 * 
	 * @Override {@link AbstractCall#get(ParsedNameValuePair)}
	 * @param ParsedNameValuePair
	 *            id
	 * @return AccountDataType
	 */
	@Override
	public AccountDataType get(ParsedNameValuePair id) throws ClearcheckbookException {
		AccountDataType accountDataType = super.get(id);
		_logger.debug("get: " + id + " -> " + accountDataType);
		return accountDataType;
	}

/**
	 * 
	 Adds an account to the site <br>
	 * Method: post <br>
	 * Call: account
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/account/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * name Required The name of the account <br>
	 * type_id Required the type of account (1= Cash, 2= Checking Account, 3=
	 * Savings Account, 4= Credit Card, 5= Investment) <br>
	 * initial_balance Optional A float value identifying the existing balance
	 * in this account (if negative, put a '-' symbol before the number...
	 * -100.00)
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * Multiple Responses* The newly created id for the account on success or
	 * false/null on fail.
	 * 
	 * @Override {@link AbstractCall#insert(AbstractDataType)
	 * @param AccountDataType input
	 * @return id
	 */
	public String insert(AccountDataType input) throws ClearcheckbookException {
		return super.insert(input);
	}

	/**
	 * Edit the details of an account <br>
	 * Method: put <br>
	 * Call: account
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/account/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required the id of the account being edited <br>
	 * name Required the new name of the account <br>
	 * type_id Required the new type_id of the account (1= Cash, 2= Checking
	 * Account, 3= Savings Account, 4= Credit Card, 5= Investment)
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true/false returns true on a successful edit or false on fail.
	 * 
	 * @Override {@link AbstractCall#edit(AbstractDataType)}
	 * @param AccountDataType input
	 * @return boolean - ok
	 * @throws ClearcheckbookException 
	 */
	
	public boolean edit(AccountDataType input) throws ClearcheckbookException {
		return super.edit(input);
	}

	/**
	 * Deletes a specific account <br>
	 * Method: delete <br>
	 * Call: account
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/account/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required the id of the account being deleted
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false returns true upon successfull delete or false/null on fail
	 * 
	 * @Override {@link AbstractCall#delete(ParsedNameValuePair)}
	 * @param ParsedNameValuePair idParameter
	 * @return boolean - ok
	 * @throws ClearcheckbookException 
	 */
	public boolean delete(ParsedNameValuePair idParameter) throws ClearcheckbookException {
		return super.delete(idParameter);
	}

}
