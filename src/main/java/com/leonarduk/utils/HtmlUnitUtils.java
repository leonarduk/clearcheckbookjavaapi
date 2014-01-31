package com.leonarduk.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ajax.JSONObjectConvertor;

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

public class HtmlUnitUtils {

	private static final Logger _logger = Logger.getLogger(HtmlUnitUtils.class);

	public static HtmlForm getFormById(HtmlPage page, String id)
			throws ElementNotFoundException {
		List<HtmlForm> forms = page.getForms();
		for (Iterator<HtmlForm> iterator = forms.iterator(); iterator.hasNext();) {
			HtmlForm htmlForm = iterator.next();
			if (htmlForm.getId().equals(id)) {
				return htmlForm;
			}
		}
		throw new ElementNotFoundException("Form", "id", id);
	}

	public static void setField(final HtmlForm form, String field, String value) {
		final HtmlTextInput textField = form.getInputByName(field);
		textField.setValueAttribute(value);
	}

	public static void setPasswordField(HtmlForm form, String field,
			String value) {
		final HtmlPasswordInput textField = form.getInputByName(field);
		textField.setValueAttribute(value);
	}

	public static HtmlPage getPage(String url, HttpMethod method,
			String userName, String password, NameValuePair... parameters)
			throws FailingHttpStatusCodeException, IOException {
		_logger.info("getPage : " + url + " " + method.name());
		final WebClient webClient = new WebClient();

		if (null != userName) {
			_logger.info("Setting username to " + userName);
			setCredentials(webClient, userName, password);
		} else {
			_logger.info("Clearing cookies");
			webClient.getCookieManager().clearCookies();
		}
		// Instead of requesting the page directly we create a
		// WebRequestSettings object
		WebRequest requestSettings = new WebRequest(new URL(url), method);

		if (null != parameters && parameters.length > 0 && parameters[0] != null) {
			// Then we set the request parameters
			requestSettings.setRequestParameters(Arrays.asList(parameters));
		}

		// Finally, we can get the page
		HtmlPage page = webClient.getPage(requestSettings);
		return page;
	}

	public static HtmlPage getPage(String url, HttpMethod method,
			NameValuePair... parameters) throws FailingHttpStatusCodeException,
			IOException {
		return getPage(url, method, null, null, parameters);
	}

	public String convertJSON() {
		JSONObjectConvertor convertor;
		return null;
	}

	private static void setCredentials(WebClient webClient, String username,
			String password) {
		String base64encodedUsernameAndPassword = base64Encode(username + ":"
				+ password);
		webClient.addRequestHeader("Authorization", "Basic "
				+ base64encodedUsernameAndPassword);
	}

	private static String base64Encode(String stringToEncode) {
		return DatatypeConverter.printBase64Binary(stringToEncode.getBytes());
	}
}
