/**
 * NumberUtils
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.utils;

import java.math.BigDecimal;

/**
 * Helper class for such things as formatting numbers to 2 dp.
 *
 * @author Stephen Leonard
 * @since 6 Feb 2014
 */
public class NumberUtils {

	/**
	 * Formats to 2 decimal places/.
	 *
	 * @param money
	 *            the money
	 * @return the double
	 */
	public static double formatMoney(final Double money) {

		final int decimalPlaces = 2;
		BigDecimal bd = new BigDecimal(money);

		// setScale is immutable
		bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
