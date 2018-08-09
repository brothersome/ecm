package nl.brothersome.ecm.controller;

import nl.brothersome.ecm.configuration.ConfigurationException;
import nl.brothersome.ecm.configuration.GeneralConfiguration;
import nl.brothersome.ecm.module.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AfterStartup implements ApplicationListener<ApplicationReadyEvent> {
	private static Logging logger = new Logging(AfterStartup.class);

	@Autowired
	GeneralConfiguration generalConfiguration;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		logger.info("Starting AfterStartup");
		try {
			generalConfiguration.initialize();
		} catch (ConfigurationException e) {
			logger.error(e.getMessage());
		}
		logger.info("End of AfterStartup");
	}

}
