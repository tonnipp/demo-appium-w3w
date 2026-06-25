package tests;

import com.aventstack.extentreports.ExtentReports;
import core.BaseTest;
import data.TestData;
import driver.DriverManager;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.MainPage;
import pages.SearchPage;
import utils.listeners.ReportListener;
import utils.logs.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Listeners(ReportListener.class)
public class EndToEndTest extends BaseTest {

    protected static ExtentReports extent;

    private SearchPage searchPage;

    @BeforeTest(alwaysRun = true)
    public void setupSuite() {

        extent = new ExtentReports();

        Log.info(
                "Test execution started: "
                        + DateTimeFormatter
                        .ofPattern("yyyy/MM/dd HH:mm:ss")
                        .format(LocalDateTime.now()));
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() throws Exception {

        super.setUp();

        this.driver = DriverManager.getDriver();

        searchPage =
                new MainPage()
                        .goBack()
                        .openSearch();
    }

    @Test(
            description =
                    "Verify shared address matches selected search result"
    )
    public void shouldShareCorrectSearchResult() {

        String query =
                TestData.getFirstValidSearch();

        String expectedAddress =
                searchPage
                        .searchLocation(query)
                        .waitForSearchResults()
                        .getFirstResultAddress();

        Assert.assertFalse(
                expectedAddress.isBlank(),
                "Address should not be empty"
        );

        searchPage.selectFirstResult();

        MainPage mainPage =
                new MainPage();

        mainPage.clickShareAddress();

        String sharedText =
                mainPage.getSharePreviewText();

        Assert.assertTrue(
                sharedText.contains(expectedAddress),
                String.format(
                        "Shared text [%s] does not contain [%s]",
                        sharedText,
                        expectedAddress
                )
        );
    }

    @Test(
            dataProvider = "validSearchData",
            dataProviderClass = TestData.class,
            description = "Verify user can search valid locations"
    )
    public void shouldSearchValidLocation(
            String query) {

        Log.info(
                "Searching valid query: "
                        + query);

        searchPage
                .searchLocation(query)
                .waitForSearchResults();

        Assert.assertTrue(
                searchPage.hasSearchResults(),
                "Search should return results for query: "
                        + query
        );

        Log.info(
                "Search successful: "
                        + query);
    }

    @Test(
            dataProvider = "invalidSearchData",
            dataProviderClass = TestData.class,
            description = "Verify invalid searches are handled correctly"
    )
    public void shouldHandleInvalidLocation(
            String query) {

        Log.info(
                "Searching invalid query: "
                        + query);

        searchPage
                .searchLocation(query)
                .waitForInvalidSearchResult();

        Assert.assertTrue(
                searchPage.isInvalidSearchHandled(),
                "Search tip should be displayed for invalid query: "
                        + query
        );

        Log.info(
                "Invalid search handled successfully: "
                        + query);
    }

    @Test(
            dataProvider = "recoverySearchData",
            dataProviderClass = TestData.class,
            description = "Verify user can recover from invalid search and continue searching"
    )
    public void shouldRecoverFromInvalidSearch(
            String invalidQuery,
            String validQuery) {

        Log.info(
                String.format(
                        "Recovery flow [%s] -> [%s]",
                        invalidQuery,
                        validQuery));

        searchPage
                .searchLocation(invalidQuery)
                .waitForInvalidSearchResult();

        Assert.assertTrue(
                searchPage.isInvalidSearchHandled(),
                "Search tip should be displayed for invalid query: "
                        + invalidQuery
        );

        searchPage.clearSearch();

        searchPage
                .searchLocation(validQuery)
                .waitForSearchResults();

        Assert.assertTrue(
                searchPage.hasSearchResults(),
                "Recovery search failed for query: "
                        + validQuery
        );

        Log.info(
                String.format(
                        "Recovery successful [%s] -> [%s]",
                        invalidQuery,
                        validQuery));
    }

    @Test(
            dataProvider = "sequentialSearchData",
            dataProviderClass = TestData.class
    )
    public void shouldSearchMultipleLocationsSequentially(
            String query1,
            String query2,
            String query3) {

        searchPage
                .searchLocation(query1)
                .waitForSearchResults();

        Assert.assertTrue(
                searchPage.hasSearchResults());

        searchPage.clearSearch();

        searchPage
                .searchLocation(query2)
                .waitForSearchResults();

        Assert.assertTrue(
                searchPage.hasSearchResults());

        searchPage.clearSearch();

        searchPage
                .searchLocation(query3)
                .waitForSearchResults();

        Assert.assertTrue(
                searchPage.hasSearchResults());
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown(
            ITestResult result) {

        Log.info(
                "Finished test: "
                        + result.getName());

        if (extent != null) {

            try {

                extent.flush();

            } catch (Exception e) {

                Log.error(
                        "Failed to flush extent report");
            }
        }

        super.tearDown(result);
    }
}