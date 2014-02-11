package com.leonarduk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 
 *
 * @author 	Stephen Leonard
 * @since	31 Jan 2014
 *
 * @version	$Author::           $:  Author of last commit
 * @version $Rev::              $:  Revision of last commit
 * @version $Date::             $:  Date of last commit
 *
 */
public class Config {

	private static final Logger _logger = Logger.getLogger(Config.class);
	private String configName = "config.properties";
	private Properties properties;

	public Config() {
		loadConfig();
	}

	/**
	 * This will read the config from src/main/resources/config.properties and
	 * store it for use in later calls by
	 * {@link Config#getMandatoryPropertyValue(String)}
	 * 
	 */
	private void loadConfig() {
		URL propertiesFileURL = getConfigLocation();
		_logger.info("Loading properties from '" + propertiesFileURL + "'");
		InputStream is;
		try {
			is = propertiesFileURL.openStream();
			// create the properties object
			this.properties = new Properties();
			this.properties.load(is);
			is.close();
		} catch (NullPointerException | IOException e) {
			String errorMsg = "Failed to load properties file " + configName
					+ " into Properties object";
			_logger.fatal(errorMsg, e);
			throw new IllegalArgumentException(errorMsg, e);
		}
	}

	/**
	 * Looks for value of the named parameter, using command line parameters in
	 * preference to those in the config if they exist. Fails if the parameter
	 * is not set.
	 * 
	 * @param key
	 * @return
	 */
	public String getMandatoryPropertyValue(String key) {
		String value = getOptionalPropertyValue(key);
		if (value != null) {
			_logger.info("Using " + value + " for " + key);
			return value;
		} else {
			String errorMsg = "Mandatory property [" + key
					+ "] is not defined in the environment";
			_logger.fatal(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
	}

	/**
	 * Parameters passed on the command line over-ride values from file
	 * 
	 * @param key
	 * @return
	 */
	public String getOptionalPropertyValue(String key) {
		String cliOverrideStr = System.getProperty(key);
		if (cliOverrideStr != null) {
			_logger.debug("Using command line override for [" + key + "]");
			return cliOverrideStr;
		}
		_logger.debug("Getting value of [" + key + "] from config file");
		return properties.getProperty(key);
	}

	/**
	 * Helper method to show where the config file is stored.
	 * 
	 * @return
	 */
	public URL getConfigLocation() {
		URL propertiesFileURL = getClass().getClassLoader().getResource(
				this.configName);
		return propertiesFileURL;
	}

	public Integer getPropertyIntegerValue(String key) {
		return Integer.valueOf(this.getMandatoryPropertyValue(key));
	}

	public Double getPropertyDoubleValue(String key) {
		return Double.valueOf(this.getMandatoryPropertyValue(key));
	}

	public long getPropertyLongValue(String key) {
		return Long.valueOf(this.getMandatoryPropertyValue(key));
	}
}
