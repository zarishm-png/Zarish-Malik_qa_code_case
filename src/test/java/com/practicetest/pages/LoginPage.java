package com.practicetest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

// Page object for the mobile number + OTP login modal shown after clicking the login button.
public class LoginPage extends BasePage {

    private static final By PHONE_INPUT = By.id("phone-number-input");
    private static final By CONTINUE_BUTTON = By.id("create-otp-button");
    private static final By VERIFY_BUTTON = By.id("send-otp-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PHONE_INPUT)) != null;
    }

    public String getPageTitle() {
        return getTitle();
    }

    public LoginPage enterMobileNumber(String mobileNumber) {
        waitForVisible(PHONE_INPUT).sendKeys(mobileNumber);
        return this;
    }

    public LoginPage requestOtp() {
        jsClick(CONTINUE_BUTTON);
        return this;
    }

    // The promo popup can be injected while this step is waiting (it's timer-driven, not tied to
    // page load), so each poll clears it before checking for the OTP input.
    public boolean isOtpScreenDisplayed() {
        return wait.until(d -> {
            removeStrayPromoPopup();
            List<WebElement> inputs = d.findElements(otpDigitInput(0));
            return !inputs.isEmpty() && inputs.get(0).isDisplayed();
        });
    }

    public LoginPage enterOtp(String otp) {
        for (int i = 0; i < otp.length(); i++) {
            WebElement digit = waitForVisible(otpDigitInput(i));
            digit.click();
            digit.sendKeys(String.valueOf(otp.charAt(i)));
        }
        return this;
    }

    // Entering the final OTP digit can auto-verify and close the modal before this runs, so an
    // absent button counts as success; otherwise re-fetch and click, retrying through staleness
    // from the button's enabled-state re-render.
    public void submitOtp() {
        wait.until(d -> {
            List<WebElement> buttons = d.findElements(VERIFY_BUTTON);
            if (buttons.isEmpty()) {
                return true;
            }
            WebElement button = buttons.get(0);
            if (button.isEnabled()) {
                try {
                    ((JavascriptExecutor) d).executeScript("arguments[0].click();", button);
                } catch (StaleElementReferenceException ignored) {
                    // Re-rendered after the enabled check; next poll will re-fetch.
                }
            }
            return false;
        });
    }

    private By otpDigitInput(int index) {
        return By.id("otp-digit-" + index);
    }
}
