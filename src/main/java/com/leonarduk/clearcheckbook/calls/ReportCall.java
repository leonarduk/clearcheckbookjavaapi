/**
 * ReportCall
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.ReportDataType;
import com.leonarduk.clearcheckbook.dto.ReportDataType.Fields;
import com.leonarduk.clearcheckbook.dto.ReportDataType.Type;

/**
 * The Class ReportCall.
 */
public class ReportCall extends AbstractCall<ReportDataType> {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(ReportCall.class);

	/** The Constant TYPE. */
	public static final String TYPE = "report";

	/**
	 * Instantiates a new report call.
	 *
	 * @param connection
	 *            the connection
	 */
	public ReportCall(final ClearCheckBookConnection connection) {
		super(connection, ReportDataType.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getAll()
	 */
	@Override
	public List<ReportDataType> getAll() throws ClearcheckbookException {
		final ParsedNameValuePair[] params = new ParsedNameValuePair[] { new ParsedNameValuePair(
		        Fields.TYPE.name().toLowerCase(), Type.LINE.name().toLowerCase()) };
		final List<ReportDataType> all = super.getAll(params);
		ReportCall._logger.debug("getAll: " + all);
		return all;
	}

	/**
	 * Brings back a list of links to graphs in google graph.
	 *
	 * @param type
	 *            the type
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<ReportDataType> getAll(final Type type) throws ClearcheckbookException {
		final ParsedNameValuePair[] params = new ParsedNameValuePair[] { new ParsedNameValuePair(
		        Fields.TYPE.name().toLowerCase(), type.name().toLowerCase()) };
		final List<ReportDataType> all = super.getAll(params);
		ReportCall._logger.debug("getAll: " + all);
		return all;
	}

	/**
	 * Gets the all.
	 *
	 * @param type
	 *            the type
	 * @param months
	 *            the months
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<ReportDataType> getAll(final Type type, final int months)
	        throws ClearcheckbookException {
		final ParsedNameValuePair[] params = new ParsedNameValuePair[] {
		        new ParsedNameValuePair(Fields.TYPE.name().toLowerCase(),
		                type.name().toLowerCase()),
		        new ParsedNameValuePair(Fields.MONTHS.name().toLowerCase(),
		                String.valueOf(months)) };
		final List<ReportDataType> all = super.getAll(params);
		ReportCall._logger.debug("getAll: " + all);
		return all;
	}

	/**
	 * Returns an array of images containing reports for the current user <br>
	 * Method: get <br>
	 * Call: reports
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reports/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * type Required "pie" or "line". No data will be returned if one of those is not sent. <br>
	 * months Optional Integer value representing the number of months worth of data to use. Default
	 * is 6. <br>
	 * bgcolor Optional Background color of the returned image. Send as a color hex code (eg:
	 * "F3F3F3"). Default is FFFFFF (white) <br>
	 * height Optional The integer height in pixels of the returned image. Default is 150 <br>
	 * width Optional The integer width in pixels of the returned image. Default is 320
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * label The name of the Account (if type="line") or the date in yyyy-mm-dd format (if
	 * type="pie") <br>
	 * url The URL to the image being returned.
	 *
	 * @param type
	 *            the type
	 * @param months
	 *            the months
	 * @param bgcolor
	 *            the bgcolor
	 * @param height
	 *            the height
	 * @param width
	 *            the width
	 * @return the all
	 * @throws ClearcheckbookException
	 *             the clearcheckbook exception
	 */
	public List<ReportDataType> getAll(final Type type, final int months, final String bgcolor,
	        final int height, final int width) throws ClearcheckbookException {
		final ParsedNameValuePair[] params = new ParsedNameValuePair[] {
		        new ParsedNameValuePair(Fields.TYPE.name().toLowerCase(),
		                type.name().toLowerCase()),
		        new ParsedNameValuePair(Fields.MONTHS.name().toLowerCase(), String.valueOf(months)),
		        new ParsedNameValuePair(Fields.BGCOLOR.name().toLowerCase(), bgcolor),
		        new ParsedNameValuePair(Fields.HEIGHT.name().toLowerCase(), String.valueOf(height)),
		        new ParsedNameValuePair(Fields.WIDTH.name().toLowerCase(), String.valueOf(width)) };
		final List<ReportDataType> all = super.getAll(params);
		ReportCall._logger.debug("getAll: " + all);
		return all;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.leonarduk.clearcheckbook.calls.AbstractCall#getUrlSuffix()
	 */
	@Override
	protected String getUrlSuffix() {
		return ReportCall.TYPE;
	}

}
