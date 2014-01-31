package com.leonarduk.clearcheckbook.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 28 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class CategoryDataType extends AbstractDataType {

	private static final Logger _logger = Logger
			.getLogger(CategoryDataType.class);

	enum Fields {
		ID, NAME, PARENT
	}

	@Override
	protected Enum<?>[] getEditFields() {
		Fields[] insertFields = new Fields[] { Fields.ID, Fields.NAME,
				Fields.PARENT };
		return insertFields;
	}

	@Override
	protected Enum<?>[] getInsertFields() {
		Fields[] insertFields = new Fields[] { Fields.NAME, Fields.PARENT };
		return insertFields;
	}

	public CategoryDataType(Map<String, String> map) {
		super(map);
	}

	private CategoryDataType() {
		super(new HashMap<String, String>());
	}

	public static CategoryDataType create(String name, String parent) {
		CategoryDataType categoriesDataType = new CategoryDataType();
		categoriesDataType.setName(name);
		categoriesDataType.setParent(parent);
		_logger.debug("createCategoriesDataType: " + name + " " + parent + " "
				+ " -> " + categoriesDataType);
		return categoriesDataType;
	}

	public String getName() {
		return getValue(Fields.NAME);
	}

	public String getParent() {
		return getValue(Fields.PARENT);
	}

	public void setName(String value) {
		setValue(Fields.NAME, value);
	}

	public void setParent(String value) {
		setValue(Fields.PARENT, value);
	}

}
