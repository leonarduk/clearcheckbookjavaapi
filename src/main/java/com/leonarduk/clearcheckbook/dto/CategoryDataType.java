/**
 * CategoryDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The Class CategoryDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class CategoryDataType extends AbstractDataType<CategoryDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(CategoryDataType.class);

	/**
	 * Creates the.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param parent
	 *            the parent
	 * @return the category data type
	 */
	public static CategoryDataType create(final Long id, final String name, final Long parent) {
		final CategoryDataType categoriesDataType = new CategoryDataType();
		categoriesDataType.setName(name);
		categoriesDataType.setParent(parent);
		categoriesDataType.setValue(Fields.ID, id);
		CategoryDataType._logger.debug("createCategoriesDataType: " + name + " " + parent + " "
		        + " -> " + categoriesDataType);
		return categoriesDataType;
	}

	/**
	 * Creates the.
	 *
	 * @param name
	 *            the name
	 * @param parent
	 *            the parent
	 * @return the category data type
	 */
	public static CategoryDataType create(final String name, final Long parent) {
		final CategoryDataType categoriesDataType = new CategoryDataType();
		categoriesDataType.setName(name);
		categoriesDataType.setParent(parent);
		CategoryDataType._logger.debug("createCategoriesDataType: " + name + " " + parent + " "
		        + " -> " + categoriesDataType);
		return categoriesDataType;
	}

	/**
	 * Instantiates a new category data type.
	 */
	private CategoryDataType() {
		super(new HashMap<String, String>());
	}

	/**
	 * Instantiates a new category data type.
	 *
	 * @param map
	 *            the map
	 */
	public CategoryDataType(final Map<String, String> map) {
		super(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getEditFields()
	 */
	@Override
	protected Enum<?>[] getEditFields() {
		final Fields[] insertFields = new Fields[] { Fields.ID, Fields.NAME, Fields.PARENT };
		return insertFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getFields()
	 */
	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getInsertFields()
	 */
	@Override
	protected Enum<?>[] getInsertFields() {
		final Fields[] insertFields = new Fields[] { Fields.NAME, Fields.PARENT };
		return insertFields;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.getValue(Fields.NAME);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Long getParent() {
		return this.getLongValue(Fields.PARENT);
	}

	/**
	 * Sets the name.
	 *
	 * @param value
	 *            the new name
	 */
	public void setName(final String value) {
		this.setValue(Fields.NAME, value);
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent
	 *            the new parent
	 */
	public void setParent(final Long parent) {
		this.setValue(Fields.PARENT, parent);
	}

	/**
	 * The Enum Fields.
	 */
	public enum Fields {

		/** The id. */
		ID, /** The name. */
		NAME, /** The parent. */
		PARENT
	}

}
