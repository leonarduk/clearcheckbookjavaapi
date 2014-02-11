package com.leonarduk.clearcheckbook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClearCheckBookClientTest.class,
		ClearCheckBookConnectionTest.class, ClearCheckBookFileHandlerTest.class })
public class ClientTests {

}
