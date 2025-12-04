# API Testing TAF (Rest-Assured + JUnit + Cucumber)

**Test Automation Framework** for testing CRUD endpoints of a demo PHP API (MAMP).

The framework is built using a layered architecture (helpers, endpoints, services, tests)
and includes both classic JUnit tests and BDD scenarios with Cucumber.

---

## ✨ Key Features

- CRUD tests for the demo PHP API (`product` endpoints)
- Layered structure: **support / endpoints / services / tests**
- Separate Cucumber steps with **a single assertion per step** and meaningful failure messages
- Verification of HTTP headers for the `GET` request (`ResponseHeaderTest` class)
- The main assertion library in new tests is **AssertJ** (for readable failure messages)
- **Detailed logging** of HTTP requests/responses and Cucumber steps (Log4j2 + Rest-Assured filters)
- **ReportPortal integration** for centralized reporting and log analysis

---

## 🔧 Tech Stack & Requirements

- **Java** 17
- **Maven** 3.9+
- **JUnit**, **Rest-Assured**, **AssertJ**
- **Cucumber JVM** (JUnit Platform engine)
- **Rest-Assured**
- **AssertJ**
- **Log4j2**
- **ReportPortal** Java agent & logging libraries
- **Lombok** (models / builders)

OS: Windows / macOS / Linux

---

## 🗄️ Backend Setup (MAMP + Demo API)

1. Install **MAMP**.
2. Start Apache/MySQL from MAMP.
3. Import the DB in **phpMyAdmin** using `ApiTestingDB.sql`.
4. Verify that `http://localhost:8888/api_testing/product/read.php` returns a list (HTTP 200).

> If port 8888 is occupied, change the Apache port in MAMP and update `base.url` accordingly.

---

## 🧾 Logging

The project is configured to log both HTTP calls and high-level test steps.

- **HTTP logging** (Rest-Assured filters):
    - Logs request/response details: method, URL, headers, cookies, body, status code.
- **Step-level logging** (Log4j2):
    - Each Cucumber step logs what it is doing and what is being asserted.

Logs are written to:

- **Console**
- **File:** `target/logs/Api.log`
- (optionally) **ReportPortal** via Log4j2 appender

Log configuration is defined in:  
`src/test/resources/log4j2.xml`

---

## 📊 ReportPortal Integration

The framework is integrated with **ReportPortal** using:

- `agent-java-cucumber7` (Cucumber + JUnit 5 agent)
- `logger-java-log4j` (Log4j2 appender for RP)
- `logger-java-rest-assured` (Rest-Assured logging to RP)

Cucumber runner: `runner.CucumberRunTest`  
Cucumber plugin for RP: `com.epam.reportportal.cucumber.ScenarioReporter`

To enable ReportPortal, create `src/test/resources/reportportal.properties`:

```properties
rp.endpoint = https://<your-reportportal-host>
rp.api.key = <your_personal_api_key>
rp.project = <your_project_name>
rp.launch = api-tests
rp.enable = true
```
---

## ▶️ Test Execution


Cucumber run:
```bash
mvn -Dtest=CucumberRunTest test
```