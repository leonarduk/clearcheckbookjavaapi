package com.leonarduk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getTodaysDateyyyyMMdd() {
		return getFormattedDate("yyyyMMdd");
	}

	public static String getNowyyyyMMddHHmm() {
		return getFormattedDate("yyyyMMddHHmm");
	}

	public static String getFormattedDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
