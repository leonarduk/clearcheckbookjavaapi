package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;

public class ParsedNameValuePairTest {

	private static final Logger _logger = Logger
			.getLogger(ParsedNameValuePairTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testString() {
		String stringValue = "test";
		ParsedNameValuePair string = new ParsedNameValuePair("string",
				stringValue);
		assertTrue(stringValue.equals(string.getValue()));
		_logger.info(string);
	}

	@Test
	public void testBooleanTrue() {
		String booleanValue = "true";
		String expected = "1";
		ParsedNameValuePair parameter = new ParsedNameValuePair("string",
				booleanValue);
		assertTrue(expected.equals(parameter.getValue()));
		_logger.info(parameter);
		String toString = "Boolean value: string=true";
		assertTrue("[" + toString + "] vs [" + parameter + "]",
				toString.equals(parameter.toString()));	}

	@Test
	public void testBooleanFalse() {
		String booleanValue = "false";
		String expected = "0";
		ParsedNameValuePair parameter = new ParsedNameValuePair("string",
				booleanValue);
		assertTrue(expected.equals(parameter.getValue()));
		_logger.info(parameter);
		String toString = "Boolean value: string=false";
		assertTrue("[" + toString + "] vs [" + parameter + "]",
				toString.equals(parameter.toString()));

	}

}
