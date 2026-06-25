package waits;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private static final Duration DEFAULT_TIMEOUT =
            Duration.ofSeconds(10);

    private static final Duration POLLING_INTERVAL =
            Duration.ofMillis(500);

    private WaitUtils() {
    }

    public static WebDriverWait getWait(
            AppiumDriver driver) {

        return new WebDriverWait(
                driver,
                DEFAULT_TIMEOUT);
    }

    public static FluentWait<AppiumDriver> getFluentWait(
            AppiumDriver driver) {

        return new FluentWait<>(driver)
                .withTimeout(DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public static WebElement waitForVisible(
            AppiumDriver driver,
            By locator) {

        return getWait(driver)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(
            AppiumDriver driver,
            By locator) {

        return getWait(driver)
                .until(ExpectedConditions
                        .elementToBeClickable(locator));
    }

    public static boolean waitForInvisible(
            AppiumDriver driver,
            By locator) {

        return getWait(driver)
                .until(ExpectedConditions
                        .invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForPresence(
            AppiumDriver driver,
            By locator) {

        return getWait(driver)
                .until(ExpectedConditions
                        .presenceOfElementLocated(locator));
    }
    public static boolean waitUntil(
            AppiumDriver driver,
            java.util.function.Function<AppiumDriver, Boolean> condition) {

        return getFluentWait(driver)
                .until(condition);
    }
}