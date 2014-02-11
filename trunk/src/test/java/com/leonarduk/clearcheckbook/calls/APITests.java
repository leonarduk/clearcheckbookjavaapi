package com.leonarduk.clearcheckbook.calls;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountCallTest.class, CategoryCallTest.class,
		LimitCallTest.class, ParsedNameValuePairTest.class,
		PremiumCallTest.class, ReminderDataTypeTest.class,
		ReportCallTest.class, TransactionCallTest.class, UserCallTest.class })
public class APITests {

}
