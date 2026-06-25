package retry;

import handlers.KeyboardHandler;
import handlers.LoadingHandler;
import handlers.PermissionHandler;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import popup.PopupHandler;
import utils.logs.Log;

import java.util.function.Supplier;

public final class RetryExecutor {

    private static final int MAX_RETRIES = 3;

    private RetryExecutor() {
    }

    public static <T> T executeWithRetry(
            Supplier<T> action,
            PopupHandler popupHandler,
            PermissionHandler permissionHandler,
            LoadingHandler loadingHandler,
            KeyboardHandler keyboardHandler) {

        int attempt = 0;

        while (attempt < MAX_RETRIES) {

            try {

                return action.get();

            } catch (NoSuchElementException
                     | TimeoutException
                     | StaleElementReferenceException
                     | ElementClickInterceptedException e) {

                attempt++;

                Log.warn(String.format(
                        "Action failed (%d/%d). Starting recovery...",
                        attempt,
                        MAX_RETRIES));

                recover(
                        popupHandler,
                        permissionHandler,
                        loadingHandler,
                        keyboardHandler);

                if (attempt == MAX_RETRIES) {

                    Log.error(
                            "Action failed after "
                                    + MAX_RETRIES
                                    + " attempts.");

                    throw e;
                }
            }
        }

        throw new IllegalStateException(
                "RetryExecutor terminated unexpectedly.");
    }

    public static void executeWithRetry(
            Runnable action,
            PopupHandler popupHandler,
            PermissionHandler permissionHandler,
            LoadingHandler loadingHandler,
            KeyboardHandler keyboardHandler) {

        executeWithRetry(() -> {

                    action.run();

                    return null;

                }, popupHandler,
                permissionHandler,
                loadingHandler,
                keyboardHandler);
    }

    private static void recover(
            PopupHandler popupHandler,
            PermissionHandler permissionHandler,
            LoadingHandler loadingHandler,
            KeyboardHandler keyboardHandler) {

        try {

            loadingHandler.waitForLoadingToDisappear();

        } catch (Exception ignored) {
        }

        try {

            permissionHandler.handlePermission();

        } catch (Exception ignored) {
        }

        try {

            popupHandler.handlePopup();

        } catch (Exception ignored) {
        }

        try {

            keyboardHandler.hideKeyboard();

        } catch (Exception ignored) {
        }
    }
}