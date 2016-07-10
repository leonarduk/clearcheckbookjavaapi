/**
 * CategoryCall
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

/**
 * The Class CategoryCall.
 */
public class CategoryCall extends AbstractCall<CategoryDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(CategoryCall.class);

	/** The Constant TYPE. */
	public static final String TYPE = "category";

	/**
	 * Instantiates a new category call.
	 *
	 * @param connection
	 *            the connection
	 */
	public CategoryCall(final ClearCheckBookConnection connection) {
		super(connection, CategoryDataType.class);
	}

	/**
	 * Deletes a specific category for the current user <br>
	 * Method: delete <br>
	 * Call: category
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/category/
	 * <p>
	 * Parameters: Parameter Required Description <br>
	 * id Required The id of the category being deleted
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true upon removal of the category or false/null on fail
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public boolean delete(final ParsedNameValuePair id) throws ClearcheckbookException {
		final boolean delete = super.delete(id);
		CategoryCall._logger.debug("edit: OK?" + delete + " " + id);
		return delete;
	}

	/**
	 * Edit a specific category for the current user. <br>
	 * Method: put <br>
	 * Call: category
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/category/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * id Required The id of the category being edited <br>
	 * name Required The new name for the category <br>
	 * parent Required The new parent id for the category
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * true / false Returns true on a successful edit or false/null on fail.
	 *
	 * @param input
	 *            the input
	 * @return true, if successful
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public boolean edit(final CategoryDataType input) throws ClearcheckbookException {
		final boolean edit = super.edit(input);
		CategoryCall._logger.debug("edit: OK?" + edit + " " + input);
		return edit;
	}

	/**
	 * Gets all of the current users categories <br>
	 * Method: get <br>
	 * Call: categories
	 * <p>
	 * <br>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/categories/
	 * <p>
	 * <br>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * None
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id The id of the category <br>
	 * name The name of the category <br>
	 * parent The id of this category's parent. 0 if there is no parent.
	 *
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public List<CategoryDataType> getAll() throws ClearcheckbookException {
		final List<CategoryDataType> all = super.getAll();
		CategoryCall._logger.debug("getAll: " + all.size() + " returned");

		return all;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getPluralUrl()
	 */
	@Override
	protected String getPluralUrl() {
		return "categories";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getUrlSuffix()
	 */
	@Override
	protected String getUrlSuffix() {
		return CategoryCall.TYPE;
	}

	/**
	 * Adds a category to the current users accout. <br>
	 * Method: post <br>
	 * Call: category
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/category/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * name Required The name of the new category <br>
	 * parent Optional The id of this category's parent.
	 * <p>
	 * <br>
	 * Returned Values: <br>
	 * Value Description <br>
	 * id / false The id of the newly created category or false/null on fail.
	 *
	 * @param input
	 *            the input
	 * @return the string
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	@Override
	public String insert(final CategoryDataType input) throws ClearcheckbookException {
		final String insert = super.insert(input);
		CategoryCall._logger.debug("insert: id " + insert + " " + input);
		return insert;
	}

}
