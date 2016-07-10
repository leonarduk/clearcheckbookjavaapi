/**
 * DateUtils
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateUtils.
 */
public class DateUtils {

	/**
	 * Gets the date.
	 *
	 * @param dateString
	 *            the date string
	 * @param format
	 *            the format
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date getDate(final String dateString, final String format) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateString);
	}

	/**
	 * Gets the formatted date.
	 *
	 * @param format
	 *            the format
	 * @return the formatted date
	 */
	public static String getFormattedDate(final String format) {
		return DateUtils.getFormattedDate(format, new Date());
	}

	/**
	 * Gets the formatted date.
	 *
	 * @param format
	 *            the format
	 * @param date
	 *            the date
	 * @return the formatted date
	 */
	public static String getFormattedDate(final String format, final Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Gets the nowyyyy m mdd h hmm.
	 *
	 * @return the nowyyyy m mdd h hmm
	 */
	public static String getNowyyyyMMddHHmm() {
		return DateUtils.getFormattedDate("yyyyMMddHHmm");
	}

	/**
	 * Gets the nowyyyy m mdd h hmmss.
	 *
	 * @return the nowyyyy m mdd h hmmss
	 */
	public static String getNowyyyyMMddHHmmss() {
		return DateUtils.getFormattedDate("yyyyMMddhhmmss.000");
	}

	/**
	 * Gets the todays dateyyyy m mdd.
	 *
	 * @return the todays dateyyyy m mdd
	 */
	public static String getTodaysDateyyyyMMdd() {
		return DateUtils.getFormattedDate("yyyyMMdd");
	}

}
