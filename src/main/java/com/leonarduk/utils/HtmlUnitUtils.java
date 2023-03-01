/**
 * HtmlUnitUtils
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * The Class HtmlUnitUtils.
 */
public class HtmlUnitUtils {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(HtmlUnitUtils.class);

	/**
	 * Base64 encode.
	 *
	 * @param stringToEncode the string to encode
	 * @return the string
	 */
	private static String base64Encode(final String stringToEncode) {
		return java.util.Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
	}

	/**
	 * Gets the form by id.
	 *
	 * @param page the page
	 * @param id   the id
	 * @return the form by id
	 * @throws ElementNotFoundException the element not found exception
	 */
	public static HtmlForm getFormById(final HtmlPage page, final String id) throws ElementNotFoundException {
		final List<HtmlForm> forms = page.getForms();
		for (final HtmlForm htmlForm : forms) {
			if (htmlForm.getId().equals(id)) {
				return htmlForm;
			}
		}
		throw new ElementNotFoundException("Form", "id", id);
	}

	/**
	 * Gets the page.
	 *
	 * @param url        the url
	 * @param method     the method
	 * @param parameters the parameters
	 * @return the page
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws IOException                    Signals that an I/O exception has
	 *                                        occurred.
	 */
	public static HtmlPage getPage(final String url, final HttpMethod method, final NameValuePair... parameters)
			throws FailingHttpStatusCodeException, IOException {
		return HtmlUnitUtils.getPage(url, method, null, null, parameters);
	}

	/**
	 * Gets the page.
	 *
	 * @param url        the url
	 * @param method     the method
	 * @param userName   the user name
	 * @param password   the password
	 * @param parameters the parameters
	 * @return the page
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws IOException                    Signals that an I/O exception has
	 *                                        occurred.
	 */
	public static HtmlPage getPage(final String url, final HttpMethod method, final String userName,
			final String password, final NameValuePair... parameters)
			throws FailingHttpStatusCodeException, IOException {
		HtmlUnitUtils._logger.info("getPage : " + url + " " + method.name());
		final WebClient webClient = new WebClient();

		if (null != userName) {
			HtmlUnitUtils._logger.info("Setting username to " + userName);
			HtmlUnitUtils.setCredentials(webClient, userName, password);
		} else {
			HtmlUnitUtils._logger.info("Clearing cookies");
			webClient.getCookieManager().clearCookies();
		}
		// Instead of requesting the page directly we create a
		// WebRequestSettings object
		final WebRequest requestSettings = new WebRequest(new URL(url), method);

		if ((null != parameters) && (parameters.length > 0) && (parameters[0] != null)) {
			// Then we set the request parameters
			requestSettings.setRequestParameters(Arrays.asList(parameters));
		}

		// Finally, we can get the page
		final HtmlPage page = webClient.getPage(requestSettings);
		return page;
	}

	/**
	 * Gets the page text.
	 *
	 * @param url        the url
	 * @param method     the method
	 * @param parameters the parameters
	 * @return the page text
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws IOException                    Signals that an I/O exception has
	 *                                        occurred.
	 */
	public static String getPageText(final String url, final String method, final NameValuePair... parameters)
			throws FailingHttpStatusCodeException, IOException {
		return HtmlUnitUtils.getPage(url, Enum.valueOf(HttpMethod.class, method), parameters).toString();
	}

	/**
	 * Gets the page text.
	 *
	 * @param url        the url
	 * @param method     the method
	 * @param userName   the user name
	 * @param password   the password
	 * @param parameters the parameters
	 * @return the page text
	 * @throws FailingHttpStatusCodeException the failing http status code exception
	 * @throws IOException                    Signals that an I/O exception has
	 *                                        occurred.
	 */
	public static String getPageText(final String url, final String method, final String userName,
			final String password, final NameValuePair... parameters)
			throws FailingHttpStatusCodeException, IOException {
		return HtmlUnitUtils.getPage(url, Enum.valueOf(HttpMethod.class, method), userName, password, parameters)
				.toString();
	}

	/**
	 * Sets the credentials.
	 *
	 * @param webClient the web client
	 * @param username  the username
	 * @param password  the password
	 */
	private static void setCredentials(final WebClient webClient, final String username, final String password) {
		final String base64encodedUsernameAndPassword = HtmlUnitUtils.base64Encode(username + ":" + password);
		webClient.addRequestHeader("Authorization", "Basic " + base64encodedUsernameAndPassword);
	}

	/**
	 * Sets the field.
	 *
	 * @param form  the form
	 * @param field the field
	 * @param value the value
	 */
	public static void setField(final HtmlForm form, final String field, final String value) {
		final HtmlTextInput textField = form.getInputByName(field);
		textField.setValueAttribute(value);
	}

	/**
	 * Sets the password field.
	 *
	 * @param form  the form
	 * @param field the field
	 * @param value the value
	 */
	public static void setPasswordField(final HtmlForm form, final String field, final String value) {
		final HtmlPasswordInput textField = form.getInputByName(field);
		textField.setValueAttribute(value);
	}
}
