package org.fis.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties properties;

    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
