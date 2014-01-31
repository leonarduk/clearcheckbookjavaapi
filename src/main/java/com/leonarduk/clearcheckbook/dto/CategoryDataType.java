package com.leonarduk.clearcheckbook.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.dto.AccountDataType.Fields;

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

	public enum Fields {
		ID, NAME, PARENT
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
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

	public static CategoryDataType create(String name, Long parent) {
		CategoryDataType categoriesDataType = new CategoryDataType();
		categoriesDataType.setName(name);
		categoriesDataType.setParent(parent);
		_logger.debug("createCategoriesDataType: " + name + " " + parent + " "
				+ " -> " + categoriesDataType);
		return categoriesDataType;
	}

	public static CategoryDataType create(Long id, String name, Long parent) {
		CategoryDataType categoriesDataType = new CategoryDataType();
		categoriesDataType.setName(name);
		categoriesDataType.setParent(parent);
		categoriesDataType.setValue(Fields.ID, id);
		_logger.debug("createCategoriesDataType: " + name + " " + parent + " "
				+ " -> " + categoriesDataType);
		return categoriesDataType;
	}

	public String getName() {
		return getValue(Fields.NAME);
	}

	public Long getParent() {
		return getLongValue(Fields.PARENT);
	}

	public void setName(String value) {
		setValue(Fields.NAME, value);
	}

	public void setParent(Long parent) {
		setValue(Fields.PARENT, parent);
	}

}
