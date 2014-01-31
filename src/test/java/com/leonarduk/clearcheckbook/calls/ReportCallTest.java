package com.leonarduk.clearcheckbook.calls;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.leonarduk.clearcheckbook.ClearCheckBookConnectionTest;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.ReportDataType;

public class ReportCallTest {

	private static final Logger _logger = Logger
			.getLogger(ReportCallTest.class);

	private ReportCall call;

	@Before
	public void setUp() throws Exception {
		this.call = new ReportCall(
				ClearCheckBookConnectionTest.getTestConnection());

	}

	@Test
	public void testGetAll() {
		List<ReportDataType> reports;
		try {
			reports = this.call.getAll();
			_logger.info(reports.size() + " Report(s) : " + reports);

			for (Iterator<ReportDataType> iterator = reports.iterator(); iterator
					.hasNext();) {
				ReportDataType reportDataType = iterator.next();
				_logger.info("Report");
				_logger.info(reportDataType.getLabel());
				_logger.info(reportDataType.getUrl());

			}
		} catch (ClearcheckbookException e) {
			_logger.error("Failed to getAll", e);
			fail();
		}
	}

}
