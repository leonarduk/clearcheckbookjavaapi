/**
 * ClearCheckBookConnection
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.calls.AbstractCall;
import com.leonarduk.clearcheckbook.calls.AccountCall;
import com.leonarduk.clearcheckbook.calls.CategoryCall;
import com.leonarduk.clearcheckbook.calls.LimitCall;
import com.leonarduk.clearcheckbook.calls.PremiumCall;
import com.leonarduk.clearcheckbook.calls.ReminderCall;
import com.leonarduk.clearcheckbook.calls.ReportCall;
import com.leonarduk.clearcheckbook.calls.TransactionCall;
import com.leonarduk.clearcheckbook.calls.UserCall;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.HtmlUnitUtils;

/**
 * The Class ClearCheckBookConnection.
 */
public class ClearCheckBookConnection {

	private static final String CCB_URL = "www.clearcheckbook.com/api/";

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(AbstractCall.class);

	/** The password. */
	final private String password;

	/** The user name. */
	final private String userName;

	/** The baseurl. */
	public final String baseurl = "https://" + ClearCheckBookConnection.CCB_URL;

	/** The account call. */
	private final AccountCall accountCall;

	/** The category call. */
	private final CategoryCall categoryCall;

	/** The limit call. */
	private final LimitCall limitCall;

	/** The premium call. */
	private final PremiumCall premiumCall;

	/** The reminder call. */
	private final ReminderCall reminderCall;

	/** The report call. */
	private final ReportCall reportCall;

	/** The transaction call. */
	private final TransactionCall transactionCall;

	/** The user call. */
	private final UserCall userCall;

	/**
	 * Instantiates a new clear check book connection.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	public ClearCheckBookConnection(final String userName, final String password) {
		this.userName = userName;
		this.password = password;

		this.accountCall = new AccountCall(this);
		this.categoryCall = new CategoryCall(this);
		this.limitCall = new LimitCall(this);
		this.premiumCall = new PremiumCall(this);
		this.reminderCall = new ReminderCall(this);
		this.reportCall = new ReportCall(this);
		this.transactionCall = new TransactionCall(this);
		this.userCall = new UserCall(this);

	}

	/**
	 * Account.
	 *
	 * @return the account call
	 */
	public AccountCall account() {
		return this.accountCall;
	}

	/**
	 * Adds the get parameters.
	 *
	 * @param url
	 *            the url
	 * @param parameters
	 *            the parameters
	 * @return the string
	 */
	private String addGetParameters(String url, final ParsedNameValuePair... parameters) {
		url += "?";
		if (null != parameters[0]) {
			for (final ParsedNameValuePair parameter : parameters) {
				url += parameter.getName() + "=" + parameter.getValue() + "&";
			}
		}
		return url;
	}

	/**
	 * Category.
	 *
	 * @return the category call
	 */
	public CategoryCall category() {
		return this.categoryCall;
	}

	/**
	 * Delete page.
	 *
	 * @param url
	 *            the url
	 * @param parameters
	 *            the parameters
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String deletePage(final String url, final ParsedNameValuePair... parameters)
	        throws IOException {
		ClearCheckBookConnection._logger.debug("URL:" + this.baseurl + url);
		final String page = HtmlUnitUtils.getPageText(this.baseurl + url, "DELETE", this.userName,
		        this.password, parameters);
		return page;
	}

	/**
	 * Gets the baseurl.
	 *
	 * @return the baseurl
	 */
	public String getBaseurl() {
		return this.baseurl;
	}

	/**
	 * Gets the full url.
	 *
	 * @param url
	 *            the url
	 * @return the full url
	 */
	private String getFullUrl(final String url) {
		final String fullPath = "https://" + this.userName.getBytes() + ":"
		        + this.password.getBytes() + "@" + ClearCheckBookConnection.CCB_URL + url;
		return fullPath;
	}

	/**
	 * Gets the page.
	 *
	 * @param url
	 *            the url
	 * @param parameters
	 *            the parameters
	 * @return the page
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getPage(final String url, final ParsedNameValuePair... parameters)
	        throws IOException {
		ClearCheckBookConnection._logger.debug("URL:" + this.getFullUrl(url) + parameters);
		final String page = HtmlUnitUtils.getPageText(this.getFullUrl(url), "GET", this.userName,
		        this.password, parameters);
		return page;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Limit.
	 *
	 * @return the limit call
	 */
	public LimitCall limit() {
		return this.limitCall;
	}

	/**
	 * Post page.
	 *
	 * @param url
	 *            the url
	 * @param parameters
	 *            the parameters
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String postPage(final String url, final ParsedNameValuePair... parameters)
	        throws IOException {
		final String fullPath = this.getFullUrl(url);
		ClearCheckBookConnection._logger.debug("URL:" + fullPath + " " + parameters);
		final String page = HtmlUnitUtils.getPageText(fullPath, "POST", this.userName,
		        this.password, parameters);
		return page;
	}

	/**
	 * Premium.
	 *
	 * @return the premium call
	 */
	public PremiumCall premium() {
		return this.premiumCall;
	}

	/**
	 * Put page.
	 *
	 * @param url
	 *            the url
	 * @param parameters
	 *            the parameters
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String putPage(String url, final ParsedNameValuePair... parameters) throws IOException {
		url = this.addGetParameters(url, parameters);
		final String fullUrl = this.getFullUrl(url);

		ClearCheckBookConnection._logger.debug("URL:" + fullUrl);
		final String page = HtmlUnitUtils.getPageText(fullUrl, "PUT", this.userName, this.password,
		        parameters);
		return page;
	}

	/**
	 * Reminder.
	 *
	 * @return the reminder call
	 */
	public ReminderCall reminder() {
		return this.reminderCall;
	}

	/**
	 * Report.
	 *
	 * @return the report call
	 */
	public ReportCall report() {
		return this.reportCall;
	}

	/**
	 * Transaction.
	 *
	 * @return the transaction call
	 */
	public TransactionCall transaction() {
		return this.transactionCall;
	}

	/**
	 * User.
	 *
	 * @return the user call
	 */
	public UserCall user() {
		return this.userCall;
	}

}
