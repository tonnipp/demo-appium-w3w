package pages;

import core.BasePage;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;

public class MainPage extends BasePage {
    //locators
    private final By searchBar = AppiumBy.accessibilityId("Search bar");
    private final By backButton = AppiumBy.accessibilityId("Back");
    private final By textboxSearch = By.id("com.what3words.android:id/mapSearchEditText");
    private final By btnShareAddress =
            AppiumBy.accessibilityId("Share this address");

    private final By sharePreviewText =
            By.id("android:id/content_preview_text");


    //action
    public SearchPage openSearch() {

        actions.safeClick(searchBar);

        return new SearchPage();
    }

    public MainPage clickShareAddress() {

        actions.safeClick(btnShareAddress);

        return this;
    }

    public String getSharePreviewText() {

        return actions.safeGetText(
                sharePreviewText);
    }

    public MainPage searchLocation(String text) {
        actions.safeSendKeys(textboxSearch, text);
        return this;
    }

    public MainPage goBack() {
        actions.safeClick(backButton);
        return this;
    }

    public MainPage() {
        super();
    }
}