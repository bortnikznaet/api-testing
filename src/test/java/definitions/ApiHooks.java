package definitions;

import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.restassured.ReportPortalRestAssuredLoggingFilter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiHooks {
    private static final Logger LOG = LogManager.getLogger(ApiHooks.class);

    @Before(order = 0)
    public void configureRestAssured() {
        RestAssured.reset();
        RestAssured.filters(
                new ReportPortalRestAssuredLoggingFilter(42, LogLevel.INFO),
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
                );
        LOG.debug("Configuring RestAssured for API tests (reset + logging filters).");
    }

    @After
    public void resetConfigureRestAssured(){
        RestAssured.reset();
        LOG.debug("RestAssured configuration reset after API test.");
    }
}
