package popup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.logs.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class PopupLocatorLoader {

    private static final String FILE_PATH =
            "/popup-locators.json";

    private static final Map<String,
            List<PopupLocator>> LOCATORS =
            loadLocators();

    private PopupLocatorLoader() {
    }

    public static List<PopupLocator> getLocators(
            String platform) {

        return LOCATORS.getOrDefault(
                platform.toLowerCase(),
                Collections.emptyList());
    }

    private static Map<String,
            List<PopupLocator>> loadLocators() {

        try {

            InputStream inputStream =
                    PopupLocatorLoader.class
                            .getResourceAsStream(FILE_PATH);

            if (inputStream == null) {

                Log.error(
                        "Popup locator file not found: "
                                + FILE_PATH);

                return Collections.emptyMap();
            }

            try (InputStreamReader reader =
                         new InputStreamReader(inputStream)) {

                Type type =
                        new TypeToken<Map<String,
                                List<PopupLocator>>>() {
                        }.getType();

                return new Gson().fromJson(
                        reader,
                        type);
            }

        } catch (RuntimeException e) {

            Log.error(
                    "Failed to parse popup locator file: "
                            + FILE_PATH
                            + ". "
                            + e.getMessage());

            return Collections.emptyMap();
        } catch (Exception e) {

            Log.error(
                    "Failed to load popup locator file: "
                            + FILE_PATH
                            + ". "
                            + e.getMessage());

            return Collections.emptyMap();
        }
    }
}