package nl.brothersome.ecm.module;

// import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

	private Logger logger;

	public Logging (Class cl) {
		logger = LoggerFactory.getLogger(cl);
		// logger = Logger.getLogger(cl);
	}
	public void info(String s) {
		logger.info(s);
	}
	public void debug(String s) {
		logger.debug(s);
	}
	public void error(String s) {
		logger.error(s);
	}
	public void info(Exception e) {
		logger.info(e.getMessage(),e);
	}
	public void debug(Exception e) {
		logger.debug(e.getMessage(),e);
	}
	public void error(Exception e) {
		logger.error(e.getMessage(),e);
	}
	public void error(String s,Exception e) {
		logger.error(s,e);
	}

}
