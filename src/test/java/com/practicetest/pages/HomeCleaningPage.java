package com.practicetest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Page object for the Home Cleaning booking funnel: details -> add-ons -> date & time -> checkout.
public class HomeCleaningPage extends BasePage {

    private static final By ANIMATED_TITLE = By.id("animated-title");
    private static final By NEXT_BUTTON = By.id("funnel-next-button");
    private static final By ADDRESS_SELECT_BUTTON = By.id("addresses-button");
    private static final By FREQUENCY_ONE_TIME_OPTION = By.id("frequency-id-1");
    private static final By FREQUENCY_SELECT_BUTTON = By.id("frequency-button");

    public HomeCleaningPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return "Home Cleaning".equals(waitForVisible(ANIMATED_TITLE).getText());
    }

    // The address picker auto-opens on this step whenever the account has saved addresses, with the
    // nearest one pre-selected; it stays closed if an address is already confirmed for the session.
    public HomeCleaningPage confirmDefaultAddressIfPrompted() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(ADDRESS_SELECT_BUTTON));
            jsClick(ADDRESS_SELECT_BUTTON);
        } catch (TimeoutException ignored) {
            // Address was already confirmed for this session.
        }
        return this;
    }

    // The frequency picker auto-opens on the Date & Time step; "One Time" plus the pre-selected
    // nearest date/time slot is enough to proceed, so only the frequency needs picking here.
    public HomeCleaningPage confirmOneTimeFrequencyIfPrompted() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(FREQUENCY_ONE_TIME_OPTION));
            jsClick(FREQUENCY_ONE_TIME_OPTION);
            jsClick(FREQUENCY_SELECT_BUTTON);
        } catch (TimeoutException ignored) {
            // Frequency was already confirmed for this session.
        }
        return this;
    }

    // "Next" shares the same id across the details, add-ons and date & time steps of the funnel.
    public HomeCleaningPage clickNext() {
        jsClick(NEXT_BUTTON);
        return this;
    }

    public boolean isCheckoutDisplayed() {
        return "Checkout".equals(waitForVisible(ANIMATED_TITLE).getText());
    }
}
