package actions;

import handlers.KeyboardHandler;
import handlers.LoadingHandler;
import handlers.PermissionHandler;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import popup.PopupHandler;
import retry.RetryExecutor;
import waits.WaitUtils;

import java.util.List;
import java.util.function.Supplier;

public final class SafeActions {

    private final AppiumDriver driver;

    private final PopupHandler popupHandler;
    private final PermissionHandler permissionHandler;
    private final LoadingHandler loadingHandler;
    private final KeyboardHandler keyboardHandler;

    public SafeActions(AppiumDriver driver) {

        this.driver = driver;

        this.popupHandler = new PopupHandler(driver);
        this.permissionHandler = new PermissionHandler(driver);
        this.loadingHandler = new LoadingHandler(driver);
        this.keyboardHandler = new KeyboardHandler(driver);
    }

    public void safeClick(By locator) {

        execute(() -> {

            WebElement element =
                    WaitUtils.waitForClickable(driver, locator);

            element.click();
        });
    }

    public void safeSendKeys(By locator,
                             String text) {

        execute(() -> {

            WebElement element =
                    WaitUtils.waitForVisible(driver, locator);

            element.clear();

            element.sendKeys(text);
        });
    }

    public String safeGetText(By locator) {

        return executeWithResult(() -> {

            WebElement element =
                    WaitUtils.waitForVisible(driver, locator);

            return element.getText();
        });
    }

    public boolean isDisplayed(By locator) {

        try {

            List<WebElement> elements =
                    driver.findElements(locator);

            return !elements.isEmpty()
                    && elements.get(0).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    public void safeClear(By locator) {

        execute(() -> {

            WebElement element =
                    WaitUtils.waitForVisible(driver, locator);

            element.clear();
        });
    }

    private void execute(Runnable action) {

        RetryExecutor.executeWithRetry(
                action,
                popupHandler,
                permissionHandler,
                loadingHandler,
                keyboardHandler);
    }

    private <T> T executeWithResult(
            Supplier<T> action) {

        return RetryExecutor.executeWithRetry(
                action,
                popupHandler,
                permissionHandler,
                loadingHandler,
                keyboardHandler);
    }
}