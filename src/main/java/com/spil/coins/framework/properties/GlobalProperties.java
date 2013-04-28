/*
 * Class needed to get access to the framework properties. 
 */
package com.spil.coins.framework.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.spil.coins.framework.helper.IOHelper;

public class GlobalProperties {
	private static GlobalProperties properties;
	private Properties props;
	private String dataStorage;
	private String reportStorage;
	private String applicationPath;

	private GlobalProperties() {
		this.props = new Properties();
		try {
			props.load(new FileInputStream(new File("global.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setDataStorage(IOHelper.getAbsolutePath(props.getProperty("dataStorage")));
		setReportStorage(IOHelper.getAbsolutePath(props.getProperty("reportStorage")));
		setApplicationPath(props.getProperty("applicationPath"));
	}

	public static GlobalProperties getInstance() {
		if (properties == null) {
			properties = new GlobalProperties();
		}
		return properties;
	}

	public String getDataStorage() {
		return dataStorage;
	}

	public void setDataStorage(String DataStorage) {
		this.dataStorage = DataStorage;
	}

	public String getReportStorage() {
		return reportStorage;
	}

	public void setReportStorage(String ReportStorage) {
		this.reportStorage = ReportStorage;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

}
