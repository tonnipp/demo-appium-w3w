package utils.listeners;

import com.aventstack.extentreports.Status;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.extentreports.ExtentManager;
import utils.logs.Log;

import static utils.extentreports.ExtentTestManager.getTest;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult result) {

        return result.getMethod()
                .getConstructorOrMethod()
                .getName();
    }

    @Override
    public void onStart(ITestContext context) {

        Log.info("Execution started : " + context.getName());

    }

    @Override
    public void onFinish(ITestContext context) {

        Log.info("Execution finished : " + context.getName());

        ExtentManager.extentReports.flush();

    }

    @Override
    public void onTestStart(ITestResult result) {

        Log.info(getTestMethodName(result) + " started.");

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        Log.info(getTestMethodName(result) + " passed.");

        getTest().log(Status.PASS, "Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        Log.info(getTestMethodName(result) + " failed.");

        getTest().log(Status.FAIL, result.getThrowable());

        attachScreenshot();

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        Log.info(getTestMethodName(result) + " skipped.");

        getTest().log(Status.SKIP, "Test Skipped");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        Log.info(getTestMethodName(result)
                + " failed but within success percentage.");

    }

    /**
     * Attach screenshot to Extent Report.
     */
    private void attachScreenshot() {

        AppiumDriver driver = DriverManager.getDriver();

        if (driver == null) {

            Log.warn("Driver is null. Screenshot skipped.");

            return;
        }

        try {

            String base64Screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BASE64);

            getTest().addScreenCaptureFromBase64String(
                    base64Screenshot,
                    "Failure Screenshot");

        } catch (Exception e) {

            Log.error("Unable to capture screenshot: "
                    + e.getMessage());

        }
    }
}