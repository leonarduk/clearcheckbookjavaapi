package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

/**
 * 
 * 
 * 
 * @author Stephen Leonard
 * @since 28 Jan 2014
 * 
 * @version $Author:: $: Author of last commit
 * @version $Rev:: $: Revision of last commit
 * @version $Date:: $: Date of last commit
 * 
 */
public class PremiumDataType extends AbstractDataType<PremiumDataType> {

	enum Fields {
		MEMO, STATUS, PAYEE, CHECK_NUM
	}

	@Override
	protected Enum<?>[] getFields() {
		return Fields.values();
	}

	public PremiumDataType(Map<String, String> map) {
		super(map);
	}

	@Override
	public String toString() {
		return "PremiumDataType [hasMemo()=" + hasMemo() + ", hasPremium()="
				+ hasPremium() + ", hasPayee()=" + hasPayee()
				+ ", hasCheck_num()=" + hasCheck_num() + "]";
	}

	public boolean hasMemo() {
		return Boolean.valueOf(getValue(Fields.MEMO));
	}

	public boolean hasPremium() {
		return Boolean.valueOf(getValue(Fields.STATUS));
	}

	public boolean hasPayee() {
		return Boolean.valueOf(getValue(Fields.PAYEE));
	}

	public boolean hasCheck_num() {
		return Boolean.valueOf(getValue(Fields.CHECK_NUM));
	}
}
