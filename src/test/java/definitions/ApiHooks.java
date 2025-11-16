package definitions;

import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.restassured.ReportPortalRestAssuredLoggingFilter;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ApiHooks {

    @Before(order = 0)
    public void configureRestAssured() {
        RestAssured.reset();
        RestAssured.filters(
                new ReportPortalRestAssuredLoggingFilter(42, LogLevel.INFO),
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
                );
    }
}
