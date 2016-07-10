/**
 * Config
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The Class Config.
 *
 * @author Stephen Leonard
 * @since 31 Jan 2014
 */
public class Config {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(Config.class);

	/** The config name. */
	private final String configName = "config.properties";

	/** The properties. */
	private Properties properties;

	/**
	 * Instantiates a new config.
	 */
	public Config() {
		this.loadConfig();
	}

	/**
	 * Helper method to show where the config file is stored.
	 *
	 * @return the config location
	 */
	public URL getConfigLocation() {
		final URL propertiesFileURL = this.getClass().getClassLoader().getResource(this.configName);
		return propertiesFileURL;
	}

	/**
	 * Looks for value of the named parameter, using command line parameters in preference to those
	 * in the config if they exist. Fails if the parameter is not set.
	 *
	 * @param key
	 *            the key
	 * @return the mandatory property value
	 */
	public String getMandatoryPropertyValue(final String key) {
		final String value = this.getOptionalPropertyValue(key);
		if (value != null) {
			Config._logger.info("Using " + value + " for " + key);
			return value;
		}
		else {
			final String errorMsg = "Mandatory property [" + key
			        + "] is not defined in the environment";
			Config._logger.fatal(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
	}

	/**
	 * Parameters passed on the command line over-ride values from file.
	 *
	 * @param key
	 *            the key
	 * @return the optional property value
	 */
	public String getOptionalPropertyValue(final String key) {
		final String cliOverrideStr = System.getProperty(key);
		if (cliOverrideStr != null) {
			Config._logger.debug("Using command line override for [" + key + "]");
			return cliOverrideStr;
		}
		Config._logger.debug("Getting value of [" + key + "] from config file");
		return this.properties.getProperty(key);
	}

	/**
	 * Gets the property double value.
	 *
	 * @param key
	 *            the key
	 * @return the property double value
	 */
	public Double getPropertyDoubleValue(final String key) {
		return Double.valueOf(this.getMandatoryPropertyValue(key));
	}

	/**
	 * Gets the property integer value.
	 *
	 * @param key
	 *            the key
	 * @return the property integer value
	 */
	public Integer getPropertyIntegerValue(final String key) {
		return Integer.valueOf(this.getMandatoryPropertyValue(key));
	}

	/**
	 * Gets the property long value.
	 *
	 * @param key
	 *            the key
	 * @return the property long value
	 */
	public long getPropertyLongValue(final String key) {
		return Long.valueOf(this.getMandatoryPropertyValue(key));
	}

	/**
	 * This will read the config from src/main/resources/config.properties and store it for use in
	 * later calls by {@link Config#getMandatoryPropertyValue(String)}
	 *
	 */
	private void loadConfig() {
		final URL propertiesFileURL = this.getConfigLocation();
		Config._logger.info("Loading properties from '" + propertiesFileURL + "'");
		InputStream is;
		try {
			is = propertiesFileURL.openStream();
			// create the properties object
			this.properties = new Properties();
			this.properties.load(is);
			is.close();
		}
		catch (NullPointerException | IOException e) {
			final String errorMsg = "Failed to load properties file " + this.configName
			        + " into Properties object";
			Config._logger.fatal(errorMsg, e);
			throw new IllegalArgumentException(errorMsg, e);
		}
	}
}
