package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.CategoryDataType;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.utils.DateUtils;

public class CategoryCallTest {

	private static final Logger _logger = Logger
			.getLogger(CategoryCallTest.class);
	private CategoryCall call;

	@Before
	public void setUp() throws Exception {
		String username = "unittest_luk";
		String password = "unittest_luk";
		this.call = new CategoryCall(username, password);

	}

	@Test
	public void testGetAll() {
		List<CategoryDataType> categorys;
		try {
			categorys = this.call.getAll();
			_logger.info(categorys.size() + " category(s) : " + categorys);
		} catch (ClearcheckbookException e) {
			_logger.error("Failed to getAll", e);
			fail();
		}
	}

	@Test
	public void testInsertCategoryDataType() {
		String name = "Insert " + DateUtils.getNowyyyyMMddHHmm();
		try {
			List<CategoryDataType> categorys = this.call.getAll();
			String parent = categorys.get(0).getId();
			CategoryDataType input = CategoryDataType.create(name, parent);
			String id = this.call.insert(input);
			_logger.info("inserted " + id + ":" + input);
		} catch (ClearcheckbookException e) {
			_logger.fatal("failed to create category", e);
			fail("Failed to create category " + name);
		}
	}

	@Test
	public void testEditCategoryDataType() {
		try {
			List<CategoryDataType> categorys = this.call.getAll();
			CategoryDataType category = categorys.get(1);
			category.setName("Edit " + DateUtils.getNowyyyyMMddHHmm());
			boolean edited = this.call.edit(category);
			assertTrue("Failed to edit category " + category, edited);
			_logger.info("Edited " + category);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Categories list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testDeleteCategoryDataType() {
		try {
			List<CategoryDataType> categorys = this.call.getAll();
			CategoryDataType categoryDataType = categorys
					.get(categorys.size() - 1);
			ParsedNameValuePair idParameter = categoryDataType.getIdParameter();
			boolean deleted = this.call.delete(idParameter);
			assertTrue("Failed to delete category " + categoryDataType, deleted);
			_logger.info("Deleted " + categoryDataType);
		} catch (ClearcheckbookException e) {
			_logger.fatal("Failed to get Categories list needed to get id", e);
			fail();
		}
	}

	@Test
	public void testCategoryDataType() {
		String name = "Insert " + DateUtils.getNowyyyyMMddHHmm();
		String parent = "1";
		CategoryDataType input = CategoryDataType.create(name, parent);

		_logger.info(input);
		assertTrue(name + " vs " + input, name.equals(input.getName()));

		String newName = "new";
		input.setName(newName);
		assertTrue("Name not set: " + newName + " vs " + input.getName(),
				newName.equals(input.getName()));

		String newparent = "-1";
		input.setParent(newparent);
		assertTrue("Type not set", newparent.equals(input.getParent()));
	}

}
