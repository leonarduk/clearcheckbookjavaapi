/**
 * PremiumDataType
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

/**
 * The Class PremiumDataType.
 *
 * @author Stephen Leonard
 * @since 28 Jan 2014
 */
public class PremiumDataType extends AbstractDataType<PremiumDataType> {

	/**
	 * Instantiates a new premium data type.
	 *
	 * @param map
	 *            the map
	 */
	public PremiumDataType(final Map<String, String> map) {
		super(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#getFields()
	 */
	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	/**
	 * Checks for check_num.
	 *
	 * @return true, if successful
	 */
	public boolean hasCheck_num() {
		return Boolean.valueOf(this.getValue(Fields.CHECK_NUM));
	}

	/**
	 * Checks for memo.
	 *
	 * @return true, if successful
	 */
	public boolean hasMemo() {
		return Boolean.valueOf(this.getValue(Fields.MEMO));
	}

	/**
	 * Checks for payee.
	 *
	 * @return true, if successful
	 */
	public boolean hasPayee() {
		return Boolean.valueOf(this.getValue(Fields.PAYEE));
	}

	/**
	 * Checks for premium.
	 *
	 * @return true, if successful
	 */
	public boolean hasPremium() {
		return Boolean.valueOf(this.getValue(Fields.STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leonarduk.clearcheckbook.dto.AbstractDataType#toString()
	 */
	@Override
	public String toString() {
		return "PremiumDataType [hasMemo()=" + this.hasMemo() + ", hasPremium()="
		        + this.hasPremium() + ", hasPayee()=" + this.hasPayee() + ", hasCheck_num()="
		        + this.hasCheck_num() + "]";
	}

	/**
	 * The Enum Fields.
	 */
	enum Fields {

		/** The memo. */
		MEMO, /** The status. */
		STATUS, /** The payee. */
		PAYEE, /** The check num. */
		CHECK_NUM
	}
}
