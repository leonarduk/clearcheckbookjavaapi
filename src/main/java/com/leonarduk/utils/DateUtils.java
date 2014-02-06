package com.leonarduk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getTodaysDateyyyyMMdd() {
		return getFormattedDate("yyyyMMdd");
	}

	public static String getNowyyyyMMddHHmm() {
		return getFormattedDate("yyyyMMddHHmm");
	}

	public static String getNowyyyyMMddHHmmss() {
		return getFormattedDate("yyyyMMddhhmmss.000");
	}

	public static String getFormattedDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getFormattedDate(String format) {
		return DateUtils.getFormattedDate(format, new Date());
	}

	public static Date getDate(String dateString, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateString);
	}

}
