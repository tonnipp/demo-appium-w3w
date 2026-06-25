package handlers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import utils.logs.Log;

public final class KeyboardHandler {

    private final AppiumDriver driver;

    public KeyboardHandler(AppiumDriver driver) {
        this.driver = driver;
    }

    public void hideKeyboard() {

        try {

            if (driver instanceof AndroidDriver androidDriver) {

                if (androidDriver.isKeyboardShown()) {

                    androidDriver.hideKeyboard();

                    Log.info("Keyboard hidden (Android)");
                }

                return;
            }

            if (driver instanceof IOSDriver iosDriver) {

                if (iosDriver.isKeyboardShown()) {

                    iosDriver.hideKeyboard();

                    Log.info("Keyboard hidden (iOS)");
                }
            }

        } catch (RuntimeException e) {

            Log.debug(
                    "Keyboard not hidden: "
                            + e.getMessage());
        }
    }
}