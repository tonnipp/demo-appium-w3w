package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();

    static {

        try (InputStream input =
                     Configuration.class.getClassLoader()
                             .getResourceAsStream("config/app.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Cannot find config/app.properties");
            }

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load configuration file",
                    e);
        }
    }

    private Configuration() {
    }

    public static String get(String key) {

        return properties.getProperty(key);

    }

    public static boolean getBoolean(String key) {

        return Boolean.parseBoolean(
                properties.getProperty(key));

    }

    public static int getInt(String key) {

        return Integer.parseInt(
                properties.getProperty(key));

    }

}