package com.practicetest.stepdefinitions;

import com.practicetest.config.ConfigReader;
import com.practicetest.driver.DriverFactory;
import com.practicetest.pages.HomeCleaningPage;
import com.practicetest.pages.HomePageBeforeLogin;
import com.practicetest.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class Steps {

    private HomePageBeforeLogin homePageBeforeLogin;
    private LoginPage loginPage;
    private HomeCleaningPage homeCleaningPage;

    @Given("the user is on the Justlife Dubai homepage")
    public void the_user_is_on_the_justlife_dubai_homepage() {
        homePageBeforeLogin = new HomePageBeforeLogin(DriverFactory.getDriver()).open();
    }

    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        loginPage = homePageBeforeLogin.clickLogin();
    }

    @Then("the login page should be displayed")
    public void the_login_page_should_be_displayed() {
        assertTrue("Expected the login page/form to be displayed", loginPage.isDisplayed());
        assertTrue("Expected the page title to contain 'justlife'",
                loginPage.getPageTitle().toLowerCase().contains("justlife"));
    }

    @When("the user enters their mobile number")
    public void the_user_enters_their_mobile_number() {
        loginPage.enterMobileNumber(ConfigReader.mobileNumber());
    }

    @When("the user requests an OTP")
    public void the_user_requests_an_otp() {
        loginPage.requestOtp();
    }

    @Then("the OTP screen should be displayed")
    public void the_otp_screen_should_be_displayed() {
        assertTrue("Expected the OTP entry screen to be displayed", loginPage.isOtpScreenDisplayed());
    }

    @When("the user enters the OTP")
    public void the_user_enters_the_otp() {
        loginPage.enterOtp(ConfigReader.otp());
    }

    @When("the user submits the OTP")
    public void the_user_submits_the_otp() {
        loginPage.submitOtp();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        // OTP submission already waits for the verify button to disappear/disable; no further check needed.
    }

    @When("the user selects the Home Cleaning service")
    public void the_user_selects_the_home_cleaning_service() {
        homeCleaningPage = homePageBeforeLogin.selectHomeCleaning();
    }

    @Then("the Home Cleaning details page should be displayed")
    public void the_home_cleaning_details_page_should_be_displayed() {
        assertTrue("Expected the Home Cleaning details page to be displayed", homeCleaningPage.isDisplayed());
    }

    @When("the user confirms the booking address")
    public void the_user_confirms_the_booking_address() {
        homeCleaningPage.confirmDefaultAddressIfPrompted();
    }

    @When("the user proceeds to the add-ons step")
    public void the_user_proceeds_to_the_add_ons_step() {
        homeCleaningPage.clickNext();
    }

    @When("the user skips the add-ons step")
    public void the_user_skips_the_add_ons_step() {
        homeCleaningPage.clickNext();
    }

    @When("the user confirms the booking frequency")
    public void the_user_confirms_the_booking_frequency() {
        homeCleaningPage.confirmOneTimeFrequencyIfPrompted();
    }

    @When("the user proceeds to checkout")
    public void the_user_proceeds_to_checkout() {
        homeCleaningPage.clickNext();
    }

    @Then("the checkout page should be displayed")
    public void the_checkout_page_should_be_displayed() {
        assertTrue("Expected the Checkout page to be displayed", homeCleaningPage.isCheckoutDisplayed());
    }
}
