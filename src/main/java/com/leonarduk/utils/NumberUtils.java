package com.leonarduk.utils;

import java.math.BigDecimal;

public class NumberUtils {
	public static double formatMoney(Double money) {

		int decimalPlaces = 2;
		BigDecimal bd = new BigDecimal(money);

		// setScale is immutable
		bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
