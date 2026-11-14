# Practice Test — Cucumber + Selenium (Java)

Automated UI test suite for Google search, built with **Cucumber (BDD)**, **Selenium WebDriver**, and **JUnit 4**, following the **Page Object Model**.

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
│   └── ConfigReader.java        # reads config.properties
├── driver/
│   └── DriverFactory.java       # thread-safe WebDriver creation/teardown
├── pages/
│   ├── BasePage.java            # shared explicit-wait helpers
│   ├── GoogleHomePage.java      # Page Object: homepage search box
│   └── GoogleResultsPage.java   # Page Object: results page
├── hooks/
│   └── Hooks.java               # @Before/@After — driver lifecycle, failure screenshots
├── stepdefinitions/
│   └── GoogleSearchSteps.java   # Gherkin step implementations
└── runner/
    └── TestRunner.java          # Cucumber + JUnit entry point

src/test/resources/
├── config.properties            # base.url, browser, wait timeouts
└── features/
    └── GoogleSearch.feature     # Scenario Outline with Examples table
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

Edit `src/test/resources/config.properties` to change target site or browser:

```properties
base.url=https://www.google.com
browser=chrome
implicit.wait.seconds=5
explicit.wait.seconds=10
```

## Running the tests

```bash
mvn test
```

This executes `TestRunner`, which drives Cucumber against every scenario
in `src/test/resources/features/GoogleSearch.feature`.

## Test scenario

`GoogleSearch.feature` uses a **Scenario Outline** to run the same search
flow against multiple search terms via an `Examples` table:

```gherkin
Scenario Outline: Search for a term and verify results
  When the user searches for "<searchTerm>"
  Then the results page title should contain "<searchTerm>"
  And at least one search result should be displayed

  Examples:
    | searchTerm          |
    | Selenium WebDriver   |
    | Cucumber Java        |
    | Justlife App         |
```

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
- **Externalized config**: base URL, browser, and timeouts are not
  hardcoded — they come from `config.properties`.
- `base.url` currently points to a placeholder/dummy value
  (`https://www.google.com`); replace it with your target environment URL.
