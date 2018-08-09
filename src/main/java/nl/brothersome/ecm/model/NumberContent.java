package nl.brothersome.ecm.model;

import nl.brothersome.ecm.configuration.ConfigurationException;
import nl.brothersome.ecm.configuration.GeneralConfiguration;
import nl.brothersome.ecm.module.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Cannot hold number - it would cost a lot of memory
 * It can use the cache of the operatingsystem
 */

@Component
public class NumberContent implements Closeable {

	Logging logger = new Logging(NumberContent.class);
	RandomAccessFile aFile;

	@Autowired
	GeneralConfiguration generalConfiguration;

	public void Initialize() throws ConfigurationException {
		// InstallationDirectory and name
		// Every Info is 8 Byte containing a long integer
		try {
			logger.info("Initialize begin");
			String name = generalConfiguration.getInstallationDirectory() + File.separator + generalConfiguration.getNumberFilename();
			logger.info("filename: " + name);
			aFile = new RandomAccessFile(name, "rw");
			logger.info("filelength: " + aFile.length());
			if (aFile.length() < 8) {
				aFile.seek(0);
				logger.info("Write new" );
				aFile.writeLong(0L);
			}
			logger.info("Initialize end");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConfigurationException();
		}
	}

	public long newNumberContent() throws NumberContentException {
		long result = -1;
		synchronized(NumberContent.class) {
			try {
				aFile.seek(0);
				long lastIndex = aFile.readLong();
				result = lastIndex+1;
				aFile.seek(0);
				aFile.writeLong(result);
			} catch(IOException e) {
				e.printStackTrace();
				logger.error("Disk full for content file?");
				throw new NumberContentException();
			}
		}
		return result;
	}



	@Override
	public void close() throws IOException {
		aFile.close();
	}
}
