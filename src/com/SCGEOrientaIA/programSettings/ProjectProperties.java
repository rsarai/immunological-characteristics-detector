package com.SCGEOrientaIA.programSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
	public static Properties getProperties() {
		Properties properties = new Properties();
		FileInputStream propertiesFile;
		try {
			propertiesFile = new FileInputStream("res/config.properties");
			properties.load(propertiesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}	
