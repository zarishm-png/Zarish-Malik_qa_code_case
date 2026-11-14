# Practice Test — Cucumber + Selenium (Java)

Automated UI test suite for the **Justlife Dubai** website, built with
**Cucumber (BDD)**, **Selenium WebDriver**, and **JUnit 4**, following the
**Page Object Model**.

## Tech stack

| Tool | Purpose |
|---|---|
| Java 17 | Language |
| Maven | Build & dependency management |
| Selenium WebDriver 4.25 | Browser automation |
| WebDriverManager 5.9 | Auto-downloads the correct browser driver binary |
| Cucumber 7.18 | BDD feature files & step definitions |
| JUnit 4 | Test runner |

## Project structure

```
src/test/java/com/practicetest/
├── config/
│   └── ConfigReader.java          # reads config.properties
├── driver/
│   └── DriverFactory.java         # thread-safe WebDriver creation/teardown
├── pages/
│   ├── BasePage.java              # shared explicit-wait helpers
│   ├── HomePageBeforeLogin.java   # Page Object: homepage (pre-login)
│   ├── LoginPage.java             # Page Object: mobile number + OTP login
│   └── HomeCleaningPage.java      # Page Object: Home Cleaning booking flow
├── hooks/
│   └── Hooks.java                 # @Before/@After — driver lifecycle, failure screenshots
├── stepdefinitions/
│   └── Steps.java                 # Gherkin step implementations
└── runner/
    └── TestRunner.java            # Cucumber + JUnit entry point

src/test/resources/
├── config.properties              # base.url, mobile number, OTP, browser, wait timeouts
└── features/
    ├── 1_Login.feature            # Login via mobile number + OTP
    └── 2_HomeCleaning.feature     # Home Cleaning booking through to checkout
```

## Setup

1. **Prerequisites**: JDK 17+, Maven 3.8+, a Chrome (or Firefox) installation.
2. Clone/open the project and let Maven resolve dependencies:
   ```bash
   mvn clean install -DskipTests
   ```
3. No manual driver download is required — `WebDriverManager` resolves the
   correct ChromeDriver/GeckoDriver version at runtime.

### Configuration

Edit `src/test/resources/config.properties` to change target site, test
credentials, or browser:

```properties
base.url=https://www.justlife.com/en-AE
Mobile_number= 501234567
otp= 4040
browser=chrome
implicit.wait.seconds=5
explicit.wait.seconds=10
```

## Running the tests

```bash
mvn test
```

This executes `TestRunner`, which drives Cucumber against every scenario
in `src/test/resources/features/`.

## Test scenarios

- **`1_Login.feature`** — logs in to Justlife Dubai using a mobile number
  and OTP, verifying the login page, OTP screen, and successful login.
- **`2_HomeCleaning.feature`** — starts from the homepage, books a Home
  Cleaning service, confirms the address, skips add-ons, confirms a
  one-time frequency, and verifies the checkout page is reached.

## Reports

After running `mvn test`, reports are generated at:

- `target/cucumber-reports/cucumber.html`
- `target/cucumber-reports/cucumber.json`

Screenshots are automatically attached to the Cucumber report for any
failed scenario (see `Hooks.tearDown`).

## Design notes

- **Page Object Model**: locators and page interactions live in `pages/`,
  never in step definitions — step definitions only orchestrate page
  objects and make assertions.
- **Thread-safe driver**: `DriverFactory` uses a `ThreadLocal<WebDriver>`
  so the suite is safe to parallelize later.
- **Externalized config**: base URL, test credentials, browser, and
  timeouts are not hardcoded — they come from `config.properties`.
