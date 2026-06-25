package core;

import io.appium.java_client.AppiumDriver;

public class BaseSetup {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(AppiumDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void unload() {
        driver.remove();
    }
}
