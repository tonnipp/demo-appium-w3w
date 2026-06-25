package utils.listeners;

import com.aventstack.extentreports.Status;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static utils.extentreports.ExtentTestManager.getTest;

public class Retry implements IRetryAnalyzer {

    // Number of retries already executed
    private int count = 0;

    // Maximum retry count
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {

        if (!result.isSuccess() && count < MAX_RETRY) {

            count++;

            // Capture screenshot before retry
            attachFailureScreenshot();

            return true;
        }

        return false;
    }

    /**
     * Attach screenshot to Extent Report when test fails.
     */
    private void attachFailureScreenshot() {

        AppiumDriver driver = DriverManager.getDriver();

        if (driver == null) {
            return;
        }

        String base64Screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BASE64);

        getTest().log(
                Status.FAIL,
                "Retrying failed test (" + count + "/" + MAX_RETRY + ")"
        );

        getTest().addScreenCaptureFromBase64String(
                base64Screenshot,
                "Failure Screenshot"
        );
    }
}