package com.leonarduk.utils;

import java.math.BigDecimal;

/**
 * Helper class for such things as formatting numbers to 2 dp
 * 
 * 
 * @author Stephen Leonard
 * @since 6 Feb 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class NumberUtils {
	/**
	 * Formats to 2 decimal places/
	 * 
	 * @param money
	 * @return
	 */
	public static double formatMoney(Double money) {

		int decimalPlaces = 2;
		BigDecimal bd = new BigDecimal(money);

		// setScale is immutable
		bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
