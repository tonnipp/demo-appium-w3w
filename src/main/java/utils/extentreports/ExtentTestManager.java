package utils.extentreports;

import com.aventstack.extentreports.*;
import core.BaseSetup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ExtentTestManager {

    private static final ExtentReports EXTENT =
            ExtentManager.createExtentReports();

    private static final ThreadLocal<ExtentTest> TEST =
            new ThreadLocal<>();

    private ExtentTestManager() {
    }

    /**
     * Create ExtentTest for current thread.
     */
    public static ExtentTest createTest(String testName,
                                        String description) {

        ExtentTest test =
                EXTENT.createTest(testName, description);

        TEST.set(test);

        return test;
    }

    /**
     * Get current ExtentTest.
     */
    public static ExtentTest getTest() {

        return TEST.get();

    }

    /**
     * Remove current thread test.
     */
    public static void unload() {

        TEST.remove();

    }

    /**
     * Add screenshot into Extent report.
     */
    public static void addScreenShot(Status status,
                                     String message) {

        ExtentTest test = getTest();

        if (test == null) {
            return;
        }

        WebDriver driver = BaseSetup.getDriver();

        if (driver == null) {

            test.log(status,
                    message + " (Driver is null)");

            return;
        }

        try {

            String base64Image =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BASE64);

            test.log(
                    status,
                    message,
                    MediaEntityBuilder
                            .createScreenCaptureFromBase64String(base64Image)
                            .build());

        } catch (Exception e) {

            test.log(
                    status,
                    message +
                            " (Capture failed: "
                            + e.getMessage()
                            + ")");

        }
    }

    /**
     * INFO log.
     */
    public static void logMessage(String message) {

        logMessage(Status.INFO, message);

    }

    /**
     * Log with specific status.
     */
    public static void logMessage(Status status,
                                  String message) {

        ExtentTest test = getTest();

        if (test != null) {

            test.log(status, message);

        }
    }

    /**
     * Flush report.
     */
    public static void flush() {

        EXTENT.flush();

    }
}