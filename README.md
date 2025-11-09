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

---

## 🔧 Tech Stack & Requirements

- **Java** 11+
- **Maven** 3.9+
- **JUnit**, **Rest-Assured**, **AssertJ**
- **Cucumber JVM** (JUnit Platform engine)
- OS: Windows / macOS / Linux

---

## 🗄️ Backend Setup (MAMP + Demo API)

1. Install **MAMP**.
2. Start Apache/MySQL from MAMP.
3. Import the DB in **phpMyAdmin** using `ApiTestingDB.sql`.
4. Verify that `http://localhost:8888/api_testing/product/read.php` returns a list (HTTP 200).

> If port 8888 is occupied, change the Apache port in MAMP and update `base.url` accordingly.

---

## ▶️ Test Execution

Basic run:

```bash
mvn -Dtest=ProductApiCrudTest test
mvn -Dtest=TaskLessonSweatbandTest test
mvn -Dtest=ResponseHeaderTest test 
```

Cucumber run:
```bash
mvn -Dtest=CucumberRunTest test
```