# Movie Booking — API Automation (RestAssured)

Lightweight, modular API test framework for the Movie Booking service using Java, RestAssured and TestNG. Includes token-based auth flow, data-driven tests and Allure reporting.

---

## Quick overview
- Language: Java (11+)
- Build: Maven
- Test runner: TestNG
- API client: RestAssured
- Reporting: Allure
- Data-driven tests: JSON files under `src/test/resources/testdata`

---

## Repo layout (important)
- src/test/java — test classes, helpers (e.g., `base/BaseTest`, utils)
- src/test/resources — configuration + test data
  - src/test/resources/testdata/bookingTestData.json
- testng.xml — TestNG suite configuration
- pom.xml — dependencies and build config
- allure-results/ & target/allure-results — Allure output (generated)
- README.md — this file

---

## Prerequisites (macOS)
- Java 11+: `brew install openjdk@11` (or use SDKMAN)
- Maven: `brew install maven`
- Optional Allure CLI: `brew install allure`

Verify:
- java -version
- mvn -v
- allure --version (optional)

---

## Configuration
Update config values used by `utils.ConfigLoader` (located in `src/test/resources` or project config file). Required keys typically:
- `base_url`
- `username`
- `password`
- `login_endpoint`
- `token_endpoint`
- endpoint keys (e.g., `add_booking`, `get_booking`)

BaseTest reference: `src/test/java/base/BaseTest.java` — it runs before tests:
1. POST to `login_endpoint` with username/password → extracts `auth_code`
2. POST to `token_endpoint` with `auth_code` → extracts `access_token`
Use `getAuthToken()` in tests/helpers to attach bearer token.

---

## Run tests (project root)
- Run full suite:
  mvn clean test

- Run using a specific TestNG suite file:
  mvn -DtestngXmlFile=testng.xml test

- Run a single test class:
  mvn -Dtest=fully.qualified.ClassName test

- Run with Maven and generate Allure results:
  mvn clean test

---

## Allure report (view locally)
After tests complete:
- Serve quickly:
  allure serve target/allure-results

- Generate & open:
  allure generate target/allure-results -o target/site/allure && allure open target/site/allure

Note: Some runs generate `allure-results/` at repo root — check both `target/allure-results` and repo root.

---

## Test data
Data-driven tests use JSON located at:
`src/test/resources/testdata/bookingTestData.json`

Each object includes:
- id, endpoint, bookingId, userId, movieTitle, bookingDate, ticketCount, expectedStatusCode

---

## Extending the framework
- Add endpoint helpers and request builders under `src/test/java`
- Add DataProviders that read JSON files
- Centralize assertions and schema validations in helper classes
- Keep new endpoints keys in config for `getEndpoint(...)` usage

---

## Troubleshooting
- 401/403: confirm credentials and endpoints in config
- Token not extracted: inspect login/token responses (use Postman/curl)
- Allure empty: ensure results are in `target/allure-results` or repo-level `allure-results`
- JSON errors: validate testdata with a JSON linter

---

## Notes
- BaseTest sets `RestAssured.baseURI` using `ConfigLoader.get("base_url")`.
- Token and auth code are available via `getAuthToken()` and `getAuthCode()`.

---

## Contact
Open an issue or update the repo. Author information is available in project metadata.
```// filepath: /Users/subramanyatn/Documents/learn/Rest_Assured_0.0 copy/RestAssuredS4/README.md
# Movie Booking — API Automation (RestAssured)

Lightweight, modular API test framework for the Movie Booking service using Java, RestAssured and TestNG. Includes token-based auth flow, data-driven tests and Allure reporting.

---

## Quick overview
- Language: Java (11+)
- Build: Maven
- Test runner: TestNG
- API client: RestAssured
- Reporting: Allure
- Data-driven tests: JSON files under `src/test/resources/testdata`

---

## Repo layout (important)
- src/test/java — test classes, helpers (e.g., `base/BaseTest`, utils)
- src/test/resources — configuration + test data
  - src/test/resources/testdata/bookingTestData.json
- testng.xml — TestNG suite configuration
- pom.xml — dependencies and build config
- allure-results/ & target/allure-results — Allure output (generated)
- README.md — this file

---

## Prerequisites (macOS)
- Java 11+: `brew install openjdk@11` (or use SDKMAN)
- Maven: `brew install maven`
- Optional Allure CLI: `brew install allure`

Verify:
- java -version
- mvn -v
- allure --version (optional)

---

## Configuration
Update config values used by `utils.ConfigLoader` (located in `src/test/resources` or project config file). Required keys typically:
- `base_url`
- `username`
- `password`
- `login_endpoint`
- `token_endpoint`
- endpoint keys (e.g., `add_booking`, `get_booking`)

BaseTest reference: `src/test/java/base/BaseTest.java` — it runs before tests:
1. POST to `login_endpoint` with username/password → extracts `auth_code`
2. POST to `token_endpoint` with `auth_code` → extracts `access_token`
Use `getAuthToken()` in tests/helpers to attach bearer token.

---

## Run tests (project root)
- Run full suite:
  mvn clean test

- Run using a specific TestNG suite file:
  mvn -DtestngXmlFile=testng.xml test

- Run a single test class:
  mvn -Dtest=fully.qualified.ClassName test

- Run with Maven and generate Allure results:
  mvn clean test

---

## Allure report (view locally)
After tests complete:
- Serve quickly:
  allure serve target/allure-results

- Generate & open:
  allure generate target/allure-results -o target/site/allure && allure open target/site/allure

Note: Some runs generate `allure-results/` at repo root — check both `target/allure-results` and repo root.

---

## Test data
Data-driven tests use JSON located at:
`src/test/resources/testdata/bookingTestData.json`

Each object includes:
- id, endpoint, bookingId, userId, movieTitle, bookingDate, ticketCount, expectedStatusCode

---

## Extending the framework
- Add endpoint helpers and request builders under `src/test/java`
- Add DataProviders that read JSON files
- Centralize assertions and schema validations in helper classes
- Keep new endpoints keys in config for `getEndpoint(...)` usage

---

## Troubleshooting
- 401/403: confirm credentials and endpoints in config
- Token not extracted: inspect login/token responses (use Postman/curl)
- Allure empty: ensure results are in `target/allure-results` or repo-level `allure-results`
- JSON errors: validate testdata with a JSON linter

---

## Notes
- BaseTest sets `RestAssured.baseURI` using `ConfigLoader.get("base_url")`.
- Token and auth code are available via `getAuthToken()` and `getAuthCode()`.

---

## Contact
Open an issue or update the repo. Author information is available in project