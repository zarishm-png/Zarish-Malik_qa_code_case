package com.practicetest.hooks;

import com.practicetest.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

// Lifecycle hooks shared by every scenario: driver setup/teardown and failure screenshots.
public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After
    public void attachFailureScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverFactory.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }

    // The browser session is shared across the whole run (not quit per-scenario) so a later
    // scenario, e.g. Home Cleaning, can detect it's already logged in from an earlier one.
    @AfterAll
    public static void tearDownSuite() {
        DriverFactory.quitDriver();
    }
}
