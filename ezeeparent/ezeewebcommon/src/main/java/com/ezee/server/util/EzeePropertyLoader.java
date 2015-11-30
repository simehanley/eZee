package com.ezee.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author siborg
 *
 */
public class EzeePropertyLoader {

	private static final Logger log = LoggerFactory.getLogger(EzeePropertyLoader.class);

	private Properties properties = new Properties();

	public EzeePropertyLoader(final String filename) {
		InputStream stream = this.getClass().getResourceAsStream("/" + filename);
		if (stream != null) {
			try {
				properties.load(stream);
			} catch (IOException e) {
				log.error("Error loading properties from stream.", e);
			}
		}
	}

	public String getProperty(final String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return null;
	}
}