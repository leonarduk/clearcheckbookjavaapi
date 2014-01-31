package com.leonarduk.clearcheckbook.calls;

import java.util.List;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearCheckBookConnection;
import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.ParsedNameValuePair;
import com.leonarduk.clearcheckbook.dto.ReportDataType;
import com.leonarduk.clearcheckbook.dto.ReportDataType.Fields;
import com.leonarduk.clearcheckbook.dto.ReportDataType.Type;

public class ReportCall extends AbstractCall<ReportDataType> {

	private static final Logger _logger = Logger.getLogger(ReportCall.class);

	public static final String TYPE = "report";

	public ReportCall(ClearCheckBookConnection connection) {
		super(TYPE, connection);
	}

	/**
	 * 
	 Returns an array of images containing reports for the current user <br>
	 * Method: get <br>
	 * Call: reports
	 * <p>
	 * Example: <br>
	 * https://username:password@www.clearcheckbook.com/api/reports/
	 * <p>
	 * Parameters: <br>
	 * Parameter Required Description <br>
	 * type Required "pie" or "line". No data will be returned if one of those
	 * is not sent. <br>
	 * months Optional Integer value representing the number of months worth of
	 * data to use. Default is 6. <br>
	 * bgcolor Optional Background color of the returned image. Send as a color
	 * hex code (eg: "F3F3F3"). Default is FFFFFF (white) <br>
	 * height Optional The integer height in pixels of the returned image.
	 * Default is 150 <br>
	 * width Optional The integer width in pixels of the returned image. Default
	 * is 320
	 * <p>
	 * Returned Values: <br>
	 * Value Description <br>
	 * label The name of the Account (if type="line") or the date in yyyy-mm-dd
	 * format (if type="pie") <br>
	 * url The URL to the image being returned.
	 * 
	 * @Override {@link AbstractCall#getAll()}
	 */
	public List<ReportDataType> getAll(Type type, int months, String bgcolor,
			int height, int width) throws ClearcheckbookException {
		ParsedNameValuePair[] params = new ParsedNameValuePair[] {
				new ParsedNameValuePair(Fields.TYPE.name().toLowerCase(), type
						.name().toLowerCase()),
				new ParsedNameValuePair(Fields.MONTHS.name().toLowerCase(),
						String.valueOf(months)),
				new ParsedNameValuePair(Fields.BGCOLOR.name().toLowerCase(),
						bgcolor),
				new ParsedNameValuePair(Fields.HEIGHT.name().toLowerCase(),
						String.valueOf(height)),
				new ParsedNameValuePair(Fields.WIDTH.name().toLowerCase(),
						String.valueOf(width)) };
		List<ReportDataType> all = super.getAll(params);
		_logger.debug("getAll: " + all);
		return all;
	}

	public List<ReportDataType> getAll(Type type, int months)
			throws ClearcheckbookException {
		ParsedNameValuePair[] params = new ParsedNameValuePair[] {
				new ParsedNameValuePair(Fields.TYPE.name().toLowerCase(), type
						.name().toLowerCase()),
				new ParsedNameValuePair(Fields.MONTHS.name().toLowerCase(),
						String.valueOf(months)) };
		List<ReportDataType> all = super.getAll(params);
		_logger.debug("getAll: " + all);
		return all;
	}

	/**
	 * Brings back a list of links to graphs in google graph
	 * 
	 * @param type
	 * @return
	 * @throws ClearcheckbookException
	 */
	public List<ReportDataType> getAll(Type type)
			throws ClearcheckbookException {
		ParsedNameValuePair[] params = new ParsedNameValuePair[] { new ParsedNameValuePair(
				Fields.TYPE.name().toLowerCase(), type.name().toLowerCase()) };
		List<ReportDataType> all = super.getAll(params);
		_logger.debug("getAll: " + all);
		return all;
	}

	public List<ReportDataType> getAll() throws ClearcheckbookException {
		ParsedNameValuePair[] params = new ParsedNameValuePair[] { new ParsedNameValuePair(
				Fields.TYPE.name().toLowerCase(), Type.LINE.name()
						.toLowerCase()) };
		List<ReportDataType> all = super.getAll(params);
		_logger.debug("getAll: " + all);
		return all;
	}

}
