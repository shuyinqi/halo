package org.mapbar.halo.log;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mapbar.halo.ApplicationContext;
import org.mapbar.halo.TestHalo;
import org.mapbar.halo.log.LoggerFactory.HaloLogConfig;

public class LoggerFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void testLoggerFactory() {
		// 实例化Logger
	HaloLogConfig logConfig = new LoggerFactory.HaloLogConfig(TestHalo.getHaloContraints());
	LoggerFactory loggerFactory = new LoggerFactory(logConfig);
	ILogger logger = loggerFactory.getLogger(LoggerFactoryTest.class);
//	logger.info("1111111111111");
	logger.debug("1111111111111");
	logger.warn("1111111111111");
	logger.error("1111111111111");
	}

	@Test
	@Ignore
	public void testGetLoggerString() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetLoggerClassOfQ() {
		fail("Not yet implemented");
	}

}
