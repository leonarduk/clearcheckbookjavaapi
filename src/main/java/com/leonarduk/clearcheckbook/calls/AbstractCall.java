/**
 * AbstractCall
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

/**
 * Abstract template class to hold the main processing shared by the API calls. Methods are
 * protected scope to be overriden by a public scope version in concrete child class.
 *
 * @author Stephen Leonard
 * @param <T>
 *            the generic type
 * @since 29 Jan 2014
 */
abstract public class AbstractCall<T extends AbstractDataType<?>> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(AbstractCall.class);

	/** The connection. */
	private final ClearCheckBookConnection connection;

	/** The data type class. */
	private final Class<T> dataTypeClass;

	/**
	 * Instantiates a new abstract call.
	 *
	 * @param connection
	 *            the connection
	 * @param dataTypeClass
	 *            the data type class
	 */
	protected AbstractCall(final ClearCheckBookConnection connection,
	        final Class<T> dataTypeClass) {
		this.connection = connection;
		this.dataTypeClass = dataTypeClass;
	}

	/**
	 * Helper wrapper function to iterate over a list of items to insert/edit/delete calling the
	 * relevant method on each.
	 *
	 * @param dataTypeList
	 *            the data type list
	 * @return the list
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected List<String> bulkProcess(final List<T> dataTypeList) throws ClearcheckbookException {
		final List<String> returnStatusList = new LinkedList<>();
		for (final T t : dataTypeList) {
			returnStatusList.add(this.process(t));
		}
		return returnStatusList;
	}

	/**
	 * Helper class to create instance of this.dataTypeClass
	 *
	 * @param map
	 *            the map
	 * @param classType
	 *            the class type
	 * @return {@link T extends AbstractDataType}
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	private T createDataTypeInstance(final HashMap<String, String> map, final Class<?> classType)
	        throws ClearcheckbookException {
		try {
			@SuppressWarnings("unchecked")
			final T dataType = (T) classType.getDeclaredConstructor(Map.class).newInstance(map);
			return dataType;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException
		        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new ClearcheckbookException(
			        "Failed to find constructor for " + classType.getName(), e);
		}

	}

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return boolean - true means delete was successful
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected boolean delete(final ParsedNameValuePair id) throws ClearcheckbookException {
		AbstractCall._logger.debug("delete: " + id.getValue());
		String returnString;
		try {
			returnString = this.getConnection().deletePage(this.getUrlSuffix(), id);
			final boolean ok = Boolean.valueOf(returnString);
			AbstractCall._logger.info("insert : deleted " + ok);
			return ok;
		}
		catch (final IOException e) {
			throw new ClearcheckbookException(
			        "Failed to delete " + this.getUrlSuffix() + " id: " + id.getValue(), e);
		}
	}

	/**
	 * Edits the.
	 *
	 * @param input
	 *            the input
	 * @return boolean - true means edit was successful
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected boolean edit(final T input) throws ClearcheckbookException {
		AbstractCall._logger.debug("edit: " + input);
		final AbstractDataType<?> dataType = input;
		String returnString;
		try {
			returnString = this.getConnection().putPage(this.getUrlSuffix(),
			        dataType.getEditParameters());
			AbstractCall._logger.debug("returned: " + returnString);
			final boolean ok = Boolean.valueOf(returnString);
			AbstractCall._logger.info("edit : edited " + ok);
			return ok;
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to create " + this.getUrlSuffix(), e);
		}
	}

	/**
	 * Gets the.
	 *
	 * @return {@link T extends AbstractDataType}
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected T get() throws ClearcheckbookException {
		try {
			final String jsonString = this.getConnection().getPage(this.getUrlSuffix());
			return this.getCore(jsonString);
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to get " + this.getUrlSuffix(), e);
		}
	}

	/**
	 * Calls the "get" method from the API that brings back the item by id.
	 *
	 * @param id
	 *            the id
	 * @return {@link T extends AbstractDataType}
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected T get(final ParsedNameValuePair id) throws ClearcheckbookException {
		try {
			final String jsonString = this.getConnection().getPage(this.getUrlSuffix(), id);

			final T dataType = this.getCore(jsonString);
			if (null == dataType.getIdParameter().getValue()) {
				throw new ClearcheckbookException(
				        "Could not get " + this.getUrlSuffix() + " for id " + id.getValue());
			}
			return dataType;
		}
		catch (IOException | ClearcheckbookException e) {
			throw new ClearcheckbookException(
			        "Failed to get " + this.getUrlSuffix() + " id: " + id.getValue(), e);
		}
	}

	/**
	 * Calls the "get" method from the API that brings back all the values.
	 *
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected List<T> getAll() throws ClearcheckbookException {
		return this.getAll((ParsedNameValuePair) null);
	}

	/**
	 * Calls the "get" method from the API that brings back all the values.
	 *
	 * @param parameters
	 *            the parameters
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected List<T> getAll(final ParsedNameValuePair... parameters)
	        throws ClearcheckbookException {
		final List<T> returnedList = new ArrayList<>();
		String jsonString = "FAILED";
		try {
			jsonString = this.getConnection().getPage(this.getPluralUrl(), parameters);
			AbstractCall._logger.debug("get: " + jsonString);

			// No values returned
			if (jsonString.equals("")) {
				return returnedList;
			}
			final ObjectMapper mapper = new ObjectMapper();

			// convert JSON string to Map
			final List<HashMap<String, String>> list = mapper.readValue(jsonString,
			        new TypeReference<List<HashMap<String, String>>>() {
			        });

			for (final HashMap<String, String> map : list) {
				final T dataType = this.createDataTypeInstance(map, this.dataTypeClass);
				returnedList.add(dataType);
			}
			AbstractCall._logger
			        .debug("get: " + jsonString + " -> " + list + " -> " + returnedList);
		}
		catch (final JsonMappingException e) {
			AbstractCall._logger.warn("No values returned");
			return returnedList;
		}
		catch (final IOException e) {
			AbstractCall._logger.error("getAll : Failed to bring back values [" + jsonString + "]",
			        e);
			throw new ClearcheckbookException("getAll : Failed to bring back values", e);
		}
		return returnedList;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public ClearCheckBookConnection getConnection() {
		return this.connection;
	}

	/**
	 * Gets the core.
	 *
	 * @param jsonString
	 *            the json string
	 * @return {@link T extends AbstractDataType}
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private T getCore(final String jsonString)
	        throws ClearcheckbookException, JsonParseException, JsonMappingException, IOException {
		if (jsonString.equals("null") || jsonString.trim().equals("")) {
			throw new ClearcheckbookException("Failed to get " + this.getUrlSuffix());
		}
		AbstractCall._logger.debug("getCore: " + jsonString);
		final ObjectMapper mapper = new ObjectMapper();

		// convert JSON string to Map
		final HashMap<String, String> map = mapper.readValue(jsonString,
		        new TypeReference<HashMap<String, String>>() {
		        });
		final T dataType = this.createDataTypeInstance(map, this.dataTypeClass);
		return dataType;
	}

	/**
	 * Gets the plural url.
	 *
	 * @return the plural url
	 */
	protected String getPluralUrl() {
		return this.getUrlSuffix() + "s";
	}

	/**
	 * Gets the url suffix.
	 *
	 * @return the url suffix
	 */
	abstract protected String getUrlSuffix();

	/**
	 * Insert.
	 *
	 * @param input
	 *            the input
	 * @return String if successful, returns the id, else throws exception
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected String insert(final T input) throws ClearcheckbookException {
		AbstractCall._logger.debug("insert: " + input);
		final AbstractDataType<?> dataType = input;
		String returnString;
		try {
			returnString = this.getConnection().postPage(this.getUrlSuffix(),
			        dataType.getInsertParameters());

			final Long id = Long.valueOf(returnString);
			AbstractCall._logger.info("insert : created " + id);
			return id.toString();
		}
		catch (final NumberFormatException e) {
			throw new ClearcheckbookException("Failed to create " + this.getUrlSuffix(), e);
		}
		catch (final IOException e) {
			throw new ClearcheckbookException("Failed to create " + this.getUrlSuffix(), e);
		}
	}

	/**
	 * Process.
	 *
	 * @param dataType
	 *            the data type
	 * @return the string
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	protected String process(final T dataType) throws ClearcheckbookException {
		AbstractCall._logger.debug("process " + dataType);
		String id = dataType.getIdParameter().getValue();

		// Inserts
		if ("".equals(id) || (dataType.getId() == 0)) {
			AbstractCall._logger.info("insert: " + dataType);
			id = this.insert(dataType);
			return "Inserted " + id;
		}
		// deletes
		else if (dataType.getId() < 0) {
			AbstractCall._logger.info("delete: " + dataType);
			final boolean status = this
			        .delete(AbstractDataType.getIdParameter(-1 * dataType.getId()));
			if (status) {
				return "Deleted " + id;
			}
			else {
				return "Failed to delete " + id;
			}
		}
		else {
			AbstractCall._logger.info("edit: " + dataType);
			final boolean status = this.edit(dataType);
			if (status) {
				return "Edited " + id;
			}
			else {
				return "Failed to edit " + id;
			}
		}
	}

}
