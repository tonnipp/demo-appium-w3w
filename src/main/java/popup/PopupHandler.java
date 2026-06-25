package popup;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.logs.Log;

import java.util.List;

public final class PopupHandler {

    private final AppiumDriver driver;
    private final List<PopupLocator> locators;

    public PopupHandler(AppiumDriver driver) {

        this.driver = driver;

        String platform =
                driver instanceof AndroidDriver
                        ? "android"
                        : "ios";

        this.locators =
                PopupLocatorLoader.getLocators(platform);
    }

    public boolean handlePopup() {

        for (PopupLocator locator : locators) {

            try {

                By by = convertToBy(locator);

                List<WebElement> elements =
                        driver.findElements(by);

                if (elements.isEmpty()) {
                    continue;
                }

                WebElement popupButton =
                        elements.get(0);

                if (!popupButton.isDisplayed()) {
                    continue;
                }

                Log.info(
                        "Popup detected. Type="
                                + locator.getType()
                                + ", Value="
                                + locator.getValue());

                popupButton.click();

                Log.info("Popup dismissed successfully.");

                return true;

            } catch (RuntimeException e) {

                Log.debug(
                        "Popup locator failed: "
                                + locator.getValue()
                                + " - "
                                + e.getMessage());
            }
        }

        return false;
    }

    private By convertToBy(PopupLocator locator) {

        return switch (
                locator.getType().toLowerCase()) {

            case "accessibilityid" ->
                    AppiumBy.accessibilityId(
                            locator.getValue());

            case "id" ->
                    By.id(locator.getValue());

            case "uiautomator" ->
                    AppiumBy.androidUIAutomator(
                            locator.getValue());

            case "xpath" ->
                    By.xpath(locator.getValue());

            default ->
                    throw new IllegalArgumentException(
                            "Unknown locator type: "
                                    + locator.getType());
        };
    }
}