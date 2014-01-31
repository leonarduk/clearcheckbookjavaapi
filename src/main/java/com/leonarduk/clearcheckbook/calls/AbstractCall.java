package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.DataTypeFactory;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.HtmlUnitUtils;

/**
 * Abstract template class to hold the main processing shared by the API calls.
 * Methods are protected scope to be overriden by a public scope version in
 * concrete child class.
 * 
 * @param <U>
 * 
 * @author Stephen Leonard
 * @since 29 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
abstract public class AbstractCall<U extends AbstractDataType> {

	final protected String url;
	final protected String plurallUrl;
	final private String password;
	final private String userName;
	public final String baseurl = "https://www.clearcheckbook.com/api/";

	private static final Logger _logger = Logger.getLogger(AbstractCall.class);

	protected AbstractCall(String url, String pluralUrl, String userName,
			String password) {
		this.url = url;
		this.plurallUrl = pluralUrl;
		this.userName = userName;
		this.password = password;
	}

	protected AbstractCall(String url, String userName, String password) {
		this(url, url + "s", userName, password);
	}

	/**
	 * Calls the "get" method from the API that brings back the item by id
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws ClearcheckbookException
	 */
	protected U get(ParsedNameValuePair id) throws ClearcheckbookException {
		try {
			String jsonString = getPage(url, id);

			U dataType = getCore(jsonString);
			if (null == dataType.getIdParameter().getValue()) {
				throw new ClearcheckbookException("Could not get " + this.url);
			}
			return dataType;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to get " + this.url
					+ " id: " + id.getValue(), e);
		}
	}

	protected U get() throws ClearcheckbookException {
		try {
			String jsonString = getPage(url);
			return getCore(jsonString);
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to get " + this.url, e);
		}
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 * @throws ClearcheckbookException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	private U getCore(String jsonString) throws ClearcheckbookException,
			JsonParseException, JsonMappingException, IOException {
		if (jsonString.equals("null")) {
			throw new ClearcheckbookException("Failed to get " + this.url);
		}
		_logger.debug("getCore: " + jsonString);
		ObjectMapper mapper = new ObjectMapper();

		// convert JSON string to Map
		HashMap<String, String> map = mapper.readValue(jsonString,
				new TypeReference<HashMap<String, String>>() {
				});
		@SuppressWarnings("unchecked")
		U dataType = (U) DataTypeFactory.getDataType(map, this.url);
		return dataType;
	}

	/**
	 * Calls the "get" method from the API that brings back all the values
	 * 
	 * @param parameters
	 * 
	 * @return
	 * @throws ClearcheckbookException
	 */
	protected List<U> getAll() throws ClearcheckbookException {
		return getAll((ParsedNameValuePair) null);
	}

	protected List<U> getAll(ParsedNameValuePair... parameters)
			throws ClearcheckbookException {
		List<U> returnedList = new ArrayList<>();
		try {
			String jsonString = getPage(plurallUrl, parameters);
			_logger.debug("get: " + jsonString);

			ObjectMapper mapper = new ObjectMapper();

			// convert JSON string to Map
			List<HashMap<String, String>> list = mapper.readValue(jsonString,
					new TypeReference<List<HashMap<String, String>>>() {
					});

			for (Iterator<HashMap<String, String>> iterator = list.iterator(); iterator
					.hasNext();) {
				HashMap<String, String> map = iterator.next();
				@SuppressWarnings("unchecked")
				U dataType = (U) DataTypeFactory.getDataType(map, this.url);
				returnedList.add(dataType);
			}
			_logger.debug("get: " + jsonString + " -> " + list + " -> "
					+ returnedList);
		} catch (IOException e) {
			_logger.error("getAll : Failed to bring back values", e);
			throw new ClearcheckbookException(
					"getAll : Failed to bring back values", e);
		} finally {
		}
		return returnedList;
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @return if successful, returns the id, else throws exception
	 * @throws ClearcheckbookException
	 */
	protected String insert(U input) throws ClearcheckbookException {
		_logger.debug("insert: " + input);
		AbstractDataType dataType = (AbstractDataType) input;
		String returnString;
		try {
			returnString = postPage(this.url, dataType.getInsertParameters());

			Long id = Long.valueOf(returnString);
			_logger.info("insert : created " + id);
			return id.toString();
		} catch (NumberFormatException e) {
			throw new ClearcheckbookException("Failed to create " + this.url, e);
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to create " + this.url, e);
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws ClearcheckbookException
	 */
	protected boolean edit(U input) throws ClearcheckbookException {
		_logger.debug("edit: " + input);
		AbstractDataType dataType = (AbstractDataType) input;
		String returnString;
		try {
			returnString = putPage(this.url, dataType.getEditParameters());
			_logger.debug("returned: " + returnString);
			boolean ok = Boolean.valueOf(returnString);
			_logger.info("insert : edited " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to create " + this.url, e);
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws ClearcheckbookException
	 */
	protected boolean delete(ParsedNameValuePair id) throws ClearcheckbookException {
		_logger.debug("delete: " + id.getValue());
		String returnString;
		try {
			returnString = deletePage(this.url, id);
			boolean ok = Boolean.valueOf(returnString);
			_logger.info("insert : edited " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to delete " + this.url
					+ " id: " + id.getValue(), e);
		}
	}

	/**
	 * 
	 */
	protected void deleteAll() {
		throw new RuntimeException("not implemented");
	}

	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	protected String getPage(String url, ParsedNameValuePair... parameters)
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
	protected String postPage(String url, ParsedNameValuePair... parameters)
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
	protected String putPage(String url, ParsedNameValuePair... parameters)
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
	protected String deletePage(String url, ParsedNameValuePair... parameters)
			throws IOException {
		_logger.debug("URL:" + this.baseurl + url);
		HtmlPage page = HtmlUnitUtils.getPage(this.baseurl + url,
				HttpMethod.DELETE, userName, password, parameters);
		return page.asText();
	}
}
