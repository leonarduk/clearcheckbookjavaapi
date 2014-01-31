package com.leonarduk.clearcheckbook.dto;

import java.util.Map;

import com.leonarduk.clearcheckbook.calls.AccountCall;
import com.leonarduk.clearcheckbook.calls.CategoryCall;
import com.leonarduk.clearcheckbook.calls.LimitCall;
import com.leonarduk.clearcheckbook.calls.PremiumCall;
import com.leonarduk.clearcheckbook.calls.ReminderCall;
import com.leonarduk.clearcheckbook.calls.ReportCall;
import com.leonarduk.clearcheckbook.calls.TransactionCall;
import com.leonarduk.clearcheckbook.calls.UserCall;

public class DataTypeFactory {
	public static AbstractDataType getDataType(Map<String, String> fieldMap,
			String type) {
		switch (type) {
		case AccountCall.TYPE:
			return new AccountDataType(fieldMap);
		case CategoryCall.TYPE:
			return new CategoryDataType(fieldMap);
		case LimitCall.TYPE:
			return new LimitDataType(fieldMap);
		case PremiumCall.TYPE:
			return new PremiumDataType(fieldMap);
		case ReminderCall.TYPE:
			return new ReminderDataType(fieldMap);
		case ReportCall.TYPE:
			return new ReportDataType(fieldMap);
		case TransactionCall.TYPE:
			return new TransactionDataType(fieldMap);
		case UserCall.TYPE:
			return new UserDataType(fieldMap);
		}
		throw new IllegalArgumentException("Cannot create " + type);
	}

}
