package com.leonarduk.clearcheckbook.calls;

import org.apache.log4j.Logger;

import com.leonarduk.clearcheckbook.ClearcheckbookException;
import com.leonarduk.clearcheckbook.dto.PremiumDataType;

public class PremiumCall extends AbstractCall<PremiumDataType> {

	private static final Logger _logger = Logger.getLogger(PremiumCall.class);

	public static final String TYPE = "premium";

	public PremiumCall(String username, String password) {
		super(TYPE, username, password);
	}

	@Override
	public PremiumDataType get() throws ClearcheckbookException {
		PremiumDataType premiumDataType = super.get();
		_logger.debug("get:" + premiumDataType);
		return premiumDataType;
	}
}
