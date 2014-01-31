package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.AbstractDataType;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

public class CategoryCall extends AbstractCall<CategoryDataType> {

	private static final Logger _logger = Logger.getLogger(CategoryCall.class);

	public static final String TYPE = "category";

	public CategoryCall(ClearCheckBookConnection connection) {
		super(TYPE, "categories", connection);
	}

	/**
	 * 
	 Deletes a specific category for the current user <br>
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
	 * true / false Returns true upon removal of the category or false/null on
	 * fail
	 * 
	 * @Override {@link AbstractCall#delete(ParsedNameValuePair)}
	 * @return
	 * @throws ClearcheckbookException
	 */
	public boolean delete(ParsedNameValuePair id)
			throws ClearcheckbookException {
		boolean delete = super.delete(id);
		_logger.debug("edit: OK?" + delete + " " + id);
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
	 * @Override {@link AbstractCall#edit(AbstractDataType)}
	 * @return
	 * @throws ClearcheckbookException
	 */
	public boolean edit(CategoryDataType input) throws ClearcheckbookException {
		boolean edit = super.edit(input);
		_logger.debug("edit: OK?" + edit + " " + input);
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
	 * @Override {@link AbstractCall#getAll()}
	 * @return
	 */
	public List<CategoryDataType> getAll() throws ClearcheckbookException {
		List<CategoryDataType> all = super.getAll();
		_logger.debug("getAll: " + all.size() + " returned");

		return all;
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
	 * @Override {@link AbstractCall#getAll()}
	 * @return
	 */
	public String insert(CategoryDataType input) throws ClearcheckbookException {
		String insert = super.insert(input);
		_logger.debug("insert: id " + insert + " " + input);
		return insert;
	}

}
