package nl.brothersome.ecm.configuration;

import nl.brothersome.ecm.model.NumberContent;
import nl.brothersome.ecm.module.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GeneralConfiguration {

	Logging logger = new Logging(GeneralConfiguration.class);

	@Autowired
	Environment env;

	@Autowired
	NumberContent numberContent; // Does the initialization

	private String installationDirectory;
	private String numberFilename;

	public void initialize() throws ConfigurationException {
		logger.info("General Configuration started");
		numberFilename = env.getRequiredProperty("installation.numberfilename");
		if (numberFilename == null || numberFilename.isEmpty()) throw new ConfigurationException();
		installationDirectory = env.getRequiredProperty("installation.directory");
		if (installationDirectory == null || installationDirectory.isEmpty()) throw new ConfigurationException();
		numberContent.Initialize();
		logger.info("General Configuration successful");
	}

	public String getNumberFilename() {
		return numberFilename;
	}
	public String getInstallationDirectory() {
		return installationDirectory;
	}

}
