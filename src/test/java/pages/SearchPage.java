package pages;

import core.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import waits.WaitUtils;

import java.util.List;

public class SearchPage extends BasePage {

    public SearchPage() {
        super();
    }

    // Search Input
    private final By txtSearch =
            By.id("com.what3words.android:id/mapSearchEditText");

    // Search Results
    private final By searchResultsRecyclerView =
            AppiumBy.id("com.what3words.android:id/searchResultsRecyclerView");

    private final By searchItems =
            AppiumBy.id("com.what3words.android:id/searchItem");

    private final By searchTitle =
            AppiumBy.id("com.what3words.android:id/search_item_google_search");

    private final By searchLocation =
            AppiumBy.id("com.what3words.android:id/searchItemLocation");

    // Invalid Search State
    private final By searchTipLayout =
            By.id("com.what3words.android:id/searchTipLayout");
    // Button
    private final By btnClear =
            AppiumBy.accessibilityId("Clear");

    private final By selectedAddress =
            By.id("com.what3words.android:id/addressTextView");

    // Actions
    public String getFirstResultAddress() {

        List<WebElement> addresses =
                driver.findElements(selectedAddress);

        return addresses.isEmpty()
                ? ""
                : addresses.get(0).getText();
    }

    public SearchPage searchLocation(String query) {

        actions.safeSendKeys(txtSearch, query);

        return this;
    }

    public SearchPage clearSearch() {

        if (actions.isDisplayed(btnClear)) {

            actions.safeClick(btnClear);
        }
        else {
            actions.safeClear(txtSearch);
        }

        return this;
    }

    public SearchPage selectFirstResult() {

        List<WebElement> results = getSearchResults();

        if (!results.isEmpty()) {
            results.get(0).click();
        }

        return this;
    }

    // Waits

    public SearchPage waitForSearchResults() {

        WaitUtils.waitForVisible(
                driver,
                searchResultsRecyclerView);

        WaitUtils.waitForPresence(
                driver,
                searchItems);

        return this;
    }

    public SearchPage waitForInvalidSearchResult() {

        WaitUtils.waitForVisible(
                driver,
                searchTipLayout);

        return this;
    }

    // Business Validations

    public boolean hasSearchResults() {

        return isSearchResultsDisplayed()
                && hasResults()
                && areAllResultsDisplayed();
    }

    public boolean isInvalidSearchHandled() {

        return actions.isDisplayed(
                searchTipLayout);
    }

    public String getFirstResultTitle() {

        List<WebElement> titles =
                driver.findElements(searchTitle);

        return titles.isEmpty()
                ? ""
                : titles.get(0).getText();
    }

    public String getFirstResultLocation() {

        List<WebElement> locations =
                driver.findElements(searchLocation);

        return locations.isEmpty()
                ? ""
                : locations.get(0).getText();
    }

    // Internal Helpers

    private boolean isSearchResultsDisplayed() {

        return actions.isDisplayed(
                searchResultsRecyclerView);
    }

    private boolean hasResults() {

        return getResultCount() > 0;
    }

    private boolean areAllResultsDisplayed() {

        return getSearchResults()
                .stream()
                .allMatch(WebElement::isDisplayed);
    }

    private int getResultCount() {

        return getSearchResults().size();
    }

    private List<WebElement> getSearchResults() {

        return driver.findElements(searchItems);
    }
}