package com.practicetest.pages;

import com.practicetest.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Page object for the Justlife Dubai homepage.
public class HomePageBeforeLogin extends BasePage {

    private static final By USER_MENU_TOGGLE = By.id("open-user-dropdown-button");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By COOKIE_OKAY_BUTTON = By.xpath("//button[normalize-space(.)='OKAY']");
    private static final By PROMO_POPUP = By.cssSelector("ct-web-popup-imageonly, ct-web-popup");
    private static final By HOME_CLEANING_SERVICE_TILE = By.id("service-home_cleaning");

    public HomePageBeforeLogin(WebDriver driver) {
        super(driver);
    }

    public HomePageBeforeLogin open() {
        driver.get(ConfigReader.baseUrl());
        dismissCookieBanner();
        dismissPromoPopup();
        return this;
    }

    public LoginPage clickLogin() {
        jsClick(USER_MENU_TOGGLE);
        waitForClickable(LOGIN_BUTTON).click();
        return new LoginPage(driver);
    }

    public HomeCleaningPage selectHomeCleaning() {
        jsClick(HOME_CLEANING_SERVICE_TILE);
        return new HomeCleaningPage(driver);
    }

    public boolean isDisplayed() {
        return !driver.findElements(HOME_CLEANING_SERVICE_TILE).isEmpty();
    }

    // The cookie banner shows immediately on load and its backdrop blocks the user menu toggle.
    // Only the first scenario of a run actually sees it, so the timeout stays short.
    private void dismissCookieBanner() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(d -> d.findElements(COOKIE_OKAY_BUTTON).size() > 0)
                    ;
            waitForClickable(COOKIE_OKAY_BUTTON).click();
        } catch (TimeoutException ignored) {
            // Banner didn't appear for this session.
        }
    }

    // The promo popup is injected a few seconds after load and sits above the header; removing it
    // avoids it silently swallowing the user menu click. Only the first scenario of a run actually
    // sees it, so the timeout stays short.
    private void dismissPromoPopup() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(d -> d.findElements(PROMO_POPUP).size() > 0);
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('ct-web-popup-imageonly, ct-web-popup').forEach(e => e.remove());");
        } catch (TimeoutException ignored) {
            // Popup didn't appear for this session.
        }
    }
}
