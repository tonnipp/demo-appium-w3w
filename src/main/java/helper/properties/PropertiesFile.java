package helper.properties;

import java.io.*;
import java.util.Properties;

public final class PropertiesFile {

    private static final String FILE_PATH =
            System.getProperty("user.dir")
                    + "/src/test/resources/configs.properties";

    private PropertiesFile() {
    }

    public static String get(String key) {

        Properties properties = new Properties();

        try (InputStream input =
                     new FileInputStream(FILE_PATH)) {

            properties.load(input);

            return properties.getProperty(key);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read properties file", e);

        }
    }

    public static synchronized void set(String key,
                                        String value) {

        Properties properties = new Properties();

        try (InputStream input =
                     new FileInputStream(FILE_PATH)) {

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

        properties.setProperty(key, value);

        try (OutputStream output =
                     new FileOutputStream(FILE_PATH)) {

            properties.store(output, null);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

}