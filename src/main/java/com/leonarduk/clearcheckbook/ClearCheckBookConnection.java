package com.leonarduk.clearcheckbook;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leonarduk.clearcheckbook.calls.AbstractCall;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.HtmlUnitUtils;

public class ClearCheckBookConnection {

	final private String password;
	final private String userName;
	public final String baseurl = "https://www.clearcheckbook.com/api/";

	private static final Logger _logger = Logger.getLogger(AbstractCall.class);

	public ClearCheckBookConnection(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public String getBaseurl() {
		return baseurl;
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String getPage(String url, ParsedNameValuePair... parameters)
			throws IOException {
		_logger.debug("URL:" + getFullUrl(url));
		HtmlPage page = HtmlUnitUtils.getPage(getFullUrl(url), HttpMethod.GET,
				userName, password, parameters);
		return page.asText();
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String postPage(String url, ParsedNameValuePair... parameters)
			throws IOException {
		String fullPath = getFullUrl(url);
		_logger.debug("URL:" + fullPath + " " + parameters);
		HtmlPage page = HtmlUnitUtils.getPage(fullPath, HttpMethod.POST,
				userName, password, parameters);
		return page.asText();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private String getFullUrl(String url) {
		String fullPath = "https://" + userName + ":" + password
				+ "@www.clearcheckbook.com/api/" + url;
		return fullPath;
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String putPage(String url, ParsedNameValuePair... parameters)
			throws IOException {
		url += "?";
		for (int i = 0; i < parameters.length; i++) {
			url += parameters[i].getName() + "=" + parameters[i].getValue()
					+ "&";
		}
		String fullUrl = getFullUrl(url);

		_logger.debug("URL:" + fullUrl);
		HtmlPage page = HtmlUnitUtils.getPage(fullUrl, HttpMethod.PUT,
				userName, password, parameters);
		return page.asText();
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String deletePage(String url, ParsedNameValuePair... parameters)
			throws IOException {
		_logger.debug("URL:" + this.baseurl + url);
		HtmlPage page = HtmlUnitUtils.getPage(this.baseurl + url,
				HttpMethod.DELETE, userName, password, parameters);
		return page.asText();
	}

}
