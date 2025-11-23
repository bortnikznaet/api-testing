package trainingxyz.support.api;

import io.restassured.response.Response;
import models.Product;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    final String HOST = "http://localhost";
    final String PORT = "8888";
    final String URL = HOST + ":" + PORT;

    public Response get(String endpoint) {
        return given()
                .when()
                .get(URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public Response get(String endpoint, int id) {
        return given()
                .queryParams("id", id)
                .when()
                .get(URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public Response post(String endpoint, Product product) {
        return given()
                .body(product)
                .when()
                .post(URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public Response put(String endpoint, Product product) {
        return given()
                .body(product)
                .when()
                .put(URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public Response delete(String endpoint, Product productId) {
        return given()
                .body(productId)
                .when()
                .delete(URL + endpoint)
                .then()
                .extract()
                .response();
    }

    public Response getWithoutLogging(String endpoint) {
        return given()
                .noFilters()
                .when()
                .get(URL + endpoint)
                .then()
                .extract()
                .response();
    }
}
