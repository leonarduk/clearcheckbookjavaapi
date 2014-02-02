package com.leonarduk.clearcheckbook.calls;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.leonarduk.clearcheckbook.dto.AbstractDataType.ControlField;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

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
abstract public class AbstractCall<U extends AbstractDataType<?>> {

	private ClearCheckBookConnection connection;

	@SuppressWarnings("rawtypes")
	private Class<? extends AbstractDataType> dataTypeClass;

	private static final Logger _logger = Logger.getLogger(AbstractCall.class);

	protected AbstractCall(
			ClearCheckBookConnection connection,
			@SuppressWarnings("rawtypes") Class<? extends AbstractDataType> dataTypeClass) {
		this.connection = connection;
		this.dataTypeClass = dataTypeClass;
	}

	abstract protected String getUrlSuffix();

	protected String getPluralUrl() {
		return getUrlSuffix() + "s";
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
			String jsonString = this.getConnection()
					.getPage(getUrlSuffix(), id);

			U dataType = getCore(jsonString);
			if (null == dataType.getIdParameter().getValue()) {
				throw new ClearcheckbookException("Could not get "
						+ getUrlSuffix());
			}
			return dataType;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to get " + getUrlSuffix()
					+ " id: " + id.getValue(), e);
		}
	}

	protected U get() throws ClearcheckbookException {
		try {
			String jsonString = this.getConnection().getPage(getUrlSuffix());
			return getCore(jsonString);
		} catch (IOException e) {
			throw new ClearcheckbookException(
					"Failed to get " + getUrlSuffix(), e);
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
			throw new ClearcheckbookException("Failed to get " + getUrlSuffix());
		}
		_logger.debug("getCore: " + jsonString);
		ObjectMapper mapper = new ObjectMapper();

		// convert JSON string to Map
		HashMap<String, String> map = mapper.readValue(jsonString,
				new TypeReference<HashMap<String, String>>() {
				});
		U dataType = createDataTypeInstance(map, this.dataTypeClass);
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
			String jsonString = this.getConnection().getPage(getPluralUrl(),
					parameters);
			_logger.debug("get: " + jsonString);

			ObjectMapper mapper = new ObjectMapper();

			// convert JSON string to Map
			List<HashMap<String, String>> list = mapper.readValue(jsonString,
					new TypeReference<List<HashMap<String, String>>>() {
					});

			for (Iterator<HashMap<String, String>> iterator = list.iterator(); iterator
					.hasNext();) {
				HashMap<String, String> map = iterator.next();
				U dataType = createDataTypeInstance(map, this.dataTypeClass);
				returnedList.add(dataType);
			}
			_logger.debug("get: " + jsonString + " -> " + list + " -> "
					+ returnedList);
		} catch (JsonMappingException e) {
			_logger.warn("No values returned");
			return returnedList;
		} catch (IOException e) {
			_logger.error("getAll : Failed to bring back values", e);
			throw new ClearcheckbookException(
					"getAll : Failed to bring back values", e);
		}
		return returnedList;
	}

	/**
	 * Helper class to create instance of this.dataTypeClass
	 * 
	 * @param map
	 * @return
	 * @throws ClearcheckbookException
	 */
	private U createDataTypeInstance(HashMap<String, String> map,
			Class<?> classType) throws ClearcheckbookException {
		try {
			@SuppressWarnings("unchecked")
			U dataType = (U) classType.getDeclaredConstructor(Map.class)
					.newInstance(map);
			return dataType;
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new ClearcheckbookException("Failed to find constructor for "
					+ classType.getName(), e);
		}

	}

	/**
	 * Helper wrapper function to iterate over a list of items to
	 * insert/edit/delete calling the relevant method on each.
	 * 
	 * @throws ClearcheckbookException
	 */
	protected void bulkProcess(List<U> dataTypeList)
			throws ClearcheckbookException {
		for (Iterator<U> iterator = dataTypeList.iterator(); iterator.hasNext();) {
			U u = iterator.next();
			String id = u.getIdParameter().getValue();

			// Inserts
			if ("".equals(id) || u.getId() == 0) {
				_logger.info("insert: " + u);
				insert(u);
			}
			// deletes
			else if (u.getId() < 0) {
				_logger.info("delete: " + u);
				delete(AbstractDataType.getIdParameter(-1 * u.getId()));
			} else {
				_logger.info("edit: " + u);
				edit(u);
			}
		}
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
		AbstractDataType<?> dataType = (AbstractDataType<?>) input;
		String returnString;
		try {
			returnString = this.getConnection().postPage(getUrlSuffix(),
					dataType.getInsertParameters());

			Long id = Long.valueOf(returnString);
			_logger.info("insert : created " + id);
			return id.toString();
		} catch (NumberFormatException e) {
			throw new ClearcheckbookException("Failed to create "
					+ getUrlSuffix(), e);
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to create "
					+ getUrlSuffix(), e);
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
		AbstractDataType<?> dataType = (AbstractDataType<?>) input;
		String returnString;
		try {
			returnString = this.getConnection().putPage(getUrlSuffix(),
					dataType.getEditParameters());
			_logger.debug("returned: " + returnString);
			boolean ok = Boolean.valueOf(returnString);
			_logger.info("insert : edited " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to create "
					+ getUrlSuffix(), e);
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws ClearcheckbookException
	 */
	protected boolean delete(ParsedNameValuePair id)
			throws ClearcheckbookException {
		_logger.debug("delete: " + id.getValue());
		String returnString;
		try {
			returnString = this.getConnection().deletePage(getUrlSuffix(), id);
			boolean ok = Boolean.valueOf(returnString);
			_logger.info("insert : deleted " + ok);
			return ok;
		} catch (IOException e) {
			throw new ClearcheckbookException("Failed to delete "
					+ getUrlSuffix() + " id: " + id.getValue(), e);
		}
	}

	/**
	 * 
	 */
	protected void deleteAll() {
		throw new RuntimeException("not implemented");
	}

	/**
	 * @return the connection
	 */
	public ClearCheckBookConnection getConnection() {
		return connection;
	}

}
