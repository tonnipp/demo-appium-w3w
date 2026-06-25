package handlers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.logs.Log;

import java.time.Duration;

public final class LoadingHandler {

    private static final Duration TIMEOUT =
            Duration.ofSeconds(10);

    private final AppiumDriver driver;

    private final By androidLoader =
            By.xpath("//android.widget.ProgressBar");

    private final By iosLoader =
            By.xpath("//XCUIElementTypeActivityIndicator");

    public LoadingHandler(AppiumDriver driver) {
        this.driver = driver;
    }

    public void waitForLoadingToDisappear() {

        By loader = getLoaderLocator();

        if (loader == null) {
            return;
        }

        try {

            new WebDriverWait(driver, TIMEOUT)
                    .until(ExpectedConditions
                            .invisibilityOfElementLocated(loader));

        } catch (TimeoutException e) {

            Log.debug(
                    "Loading indicator still visible after timeout.");
        }
    }

    private By getLoaderLocator() {

        if (driver instanceof AndroidDriver) {
            return androidLoader;
        }

        if (driver instanceof IOSDriver) {
            return iosLoader;
        }

        return null;
    }
}