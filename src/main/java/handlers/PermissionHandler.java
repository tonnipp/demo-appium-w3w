package handlers;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.logs.Log;

import java.util.List;

public final class PermissionHandler {

    private final AppiumDriver driver;

    private final By allowButton =
            By.id(
                    "com.android.permissioncontroller:id/permission_allow_button");

    private final By allowWhileUsingAppButton =
            By.id(
                    "com.android.permissioncontroller:id/permission_allow_foreground_only_button");

    private final By iosAllowButton =
            AppiumBy.accessibilityId("Allow");

    public PermissionHandler(AppiumDriver driver) {
        this.driver = driver;
    }

    public boolean handlePermission() {

        By[] locators = {
                allowButton,
                allowWhileUsingAppButton,
                iosAllowButton
        };

        for (By locator : locators) {

            try {

                List<WebElement> elements =
                        driver.findElements(locator);

                if (elements.isEmpty()) {
                    continue;
                }

                WebElement button =
                        elements.get(0);

                if (!button.isDisplayed()) {
                    continue;
                }

                Log.info(
                        "Permission dialog detected.");

                button.click();

                Log.info(
                        "Permission granted.");

                return true;

            } catch (RuntimeException e) {

                Log.debug(
                        "Permission locator failed: "
                                + e.getMessage());
            }
        }

        return false;
    }
}