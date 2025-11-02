# API Testing TAF (Rest-Assured + JUnit)

**Test Automation Framework** for testing CRUD endpoints of a demo PHP API (MAMP).

The framework has been updated and split into clean layers (helpers, endpoints, services, tests).
<br> Legacy tests from the first publication were preserved and moved to the OLD package.

---

## 🔧 Tech Stack & Requirements

- **Java** 11+
- **Maven** 3.9+
- **JUnit**, **Rest-Assured**, **AssertJ**
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
mvn -Dtest=LayerTest test
mvn -Dtest=LayerSweatbandTest test
```