package com.kh.spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
// 로그를 사용 할라면
	private Logger logger = LoggerFactory.getLogger(Log4jTest.class);
	
	public void test() {
		logger.error("error 로그");
		logger.warn("warn 로그");
		logger.info("info 로그");
		logger.debug("debug 로그");
		logger.trace("trace 로그");
		
	}
	   public static void main(String[] args) {
		      new Log4jTest().test();
		   }
}
