package core;

import actions.SafeActions;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;

public abstract class BasePage {

    protected final AppiumDriver driver;
    protected final SafeActions actions;

    protected BasePage() {

        this.driver = DriverManager.getDriver();

        if (this.driver == null) {

            throw new IllegalStateException(
                    "Driver is null. Initialize driver before creating page objects.");
        }

        this.actions = new SafeActions(driver);
    }

    protected AppiumDriver driver() {
        return driver;
    }
}