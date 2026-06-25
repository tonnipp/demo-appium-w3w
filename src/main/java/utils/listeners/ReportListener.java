package utils.listeners;

import com.aventstack.extentreports.Status;
import core.BaseSetup;
import helper.CaptureHelpers;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

public class ReportListener implements ITestListener {

    /**
     * Get test name.
     */
    private String getTestName(ITestResult result) {

        return result.getTestName() != null
                ? result.getTestName()
                : result.getMethod().getMethodName();

    }

    /**
     * Get test description.
     */
    private String getTestDescription(ITestResult result) {

        return result.getMethod().getDescription() != null
                ? result.getMethod().getDescription()
                : getTestName(result);

    }

    /**
     * Text attachment for Allure.
     */
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {

        return message;

    }

    /**
     * HTML attachment for Allure.
     */
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {

        return html;

    }

    /**
     * Screenshot attachment for Allure.
     */
    @Attachment(value = "Page Screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG(AppiumDriver driver) {

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

    }

    @Override
    public void onStart(ITestContext context) {

        Log.info("Start execution: " + context.getName());

        try {

            CaptureHelpers.startRecord(context.getName());

        } catch (Exception e) {

            Log.error("Cannot start recording: "
                    + e.getMessage());

        }
    }

    @Override
    public void onFinish(ITestContext context) {

        Log.info("Finish execution: " + context.getName());

        ExtentTestManager.flush();

        try {

            CaptureHelpers.stopRecord();

        } catch (Exception e) {

            Log.error("Cannot stop recording: "
                    + e.getMessage());

        }
    }

    @Override
    public void onTestStart(ITestResult result) {

        Log.info(getTestName(result) + " started.");

        ExtentTestManager.createTest(
                getTestName(result),
                getTestDescription(result));

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        Log.info(getTestName(result) + " passed.");

        ExtentTestManager.logMessage(
                Status.PASS,
                getTestDescription(result));

        ExtentTestManager.unload();

    }

    @Override
    public void onTestFailure(ITestResult result) {

        Log.error(getTestName(result) + " failed.");

        AppiumDriver driver = BaseSetup.getDriver();

        ExtentTestManager.logMessage(
                Status.FAIL,
                getTestDescription(result));

        if (driver == null) {

            ExtentTestManager.logMessage(
                    Status.FAIL,
                    "Driver is null. Screenshot skipped.");

            ExtentTestManager.unload();

            return;
        }

        // ===== Extent Report =====
        ExtentTestManager.addScreenShot(
                Status.FAIL,
                getTestName(result));

        // ===== Allure =====
        try {

            saveScreenshotPNG(driver);

            saveTextLog(
                    getTestName(result)
                            + " failed and screenshot captured.");

        } catch (Exception e) {

            Log.error(
                    "Cannot capture Allure attachment: "
                            + e.getMessage());

        }

        ExtentTestManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        Log.warn(getTestName(result) + " skipped.");

        ExtentTestManager.logMessage(
                Status.SKIP,
                getTestDescription(result));

        ExtentTestManager.unload();

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(
            ITestResult result) {

        Log.error(
                getTestName(result)
                        + " failed but within success percentage.");

        ExtentTestManager.logMessage(
                Status.WARNING,
                getTestName(result)
                        + " failed but within success percentage.");

    }
}