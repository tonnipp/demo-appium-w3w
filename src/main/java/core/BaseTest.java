package core;

import driver.DriverFactory;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.logs.Log;

import java.time.Duration;

public abstract class BaseTest {

    protected AppiumDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {

        Log.info("========== TEST START ==========");

        driver = DriverFactory.getAndroidDriver();

        DriverManager.setDriver(driver);

        // Framework standard:
        // Explicit waits only
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ZERO);

        Log.info("Driver initialized successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {

            String testName =
                    result.getMethod().getMethodName();

            switch (result.getStatus()) {

                case ITestResult.SUCCESS ->
                        Log.info(
                                String.format(
                                        "TEST PASSED: %s",
                                        testName));

                case ITestResult.FAILURE ->
                        Log.error(
                                String.format(
                                        "TEST FAILED: %s",
                                        testName));

                case ITestResult.SKIP ->
                        Log.warn(
                                String.format(
                                        "TEST SKIPPED: %s",
                                        testName));

                default ->
                        Log.info(
                                String.format(
                                        "TEST FINISHED: %s",
                                        testName));
            }

        } catch (Exception e) {

            Log.warn(
                    "Failed to process test result: "
                            + e.getMessage());
        }

        try {

            DriverManager.quitDriver();

        } catch (Exception e) {

            Log.warn(
                    "Failed to quit driver: "
                            + e.getMessage());
        }

        driver = null;

        Log.info("========== TEST END ==========");
    }
}