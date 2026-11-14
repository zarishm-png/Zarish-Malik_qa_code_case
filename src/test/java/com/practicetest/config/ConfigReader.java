package com.practicetest.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Loads config.properties once and exposes typed accessors.
public final class ConfigReader {

    private static final Properties PROPERTIES = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config.properties", e);
        }
        return properties;
    }

    public static String baseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static String browser() {
        return PROPERTIES.getProperty("browser", "chrome");
    }

    public static int implicitWaitSeconds() {
        return Integer.parseInt(PROPERTIES.getProperty("implicit.wait.seconds", "5"));
    }

    public static int explicitWaitSeconds() {
        return Integer.parseInt(PROPERTIES.getProperty("explicit.wait.seconds", "10"));
    }

    public static String mobileNumber() {
        return PROPERTIES.getProperty("Mobile_number");
    }

    public static String otp() {
        return PROPERTIES.getProperty("otp");
    }
}
