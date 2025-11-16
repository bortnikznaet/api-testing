package definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.service.LastProductService;
import trainingxyz.support.api.v1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ProductSteps {
    private static final Logger LOG = LogManager.getLogger(ProductSteps.class);


    Response response;
    Product product;

    ReadOneProductApi oneProductApi = new ReadOneProductApi();
    ReadProductsApi allProductsApi = new ReadProductsApi();
    ReadCategoriesApi categoriesApi = new ReadCategoriesApi();
    CreateProductApi createProductApi = new CreateProductApi();
    UpdateProductApi updateProductApi = new UpdateProductApi();
    DeleteProductApi deleteProductApi = new DeleteProductApi();
    LastProductService lastProductID = new LastProductService();

    @When("Send GET request to read product with id {int}")
    public void sendGETRequestToReadProductWithId(int id) {
        LOG.info("Sending GET request to read product with id={}", id);
        response = oneProductApi.get(id);

        LOG.info("Finished GET product request, id={}, status={}, body={}",
                id,
                response.statusCode(),
                response.getBody().asString());
    }

    @Then("Status code should be {int}")
    public void thenStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();

        LOG.info("Asserting HTTP status code: expected={}, actual={}", expectedStatusCode, actualStatusCode);
        assertThat(actualStatusCode)
                .as("Expected HTTP status code to be %s but was %s", expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @And("Product name should be {string}")
    public void productNameShouldBe(String expectedName) {
        String actualName = response.jsonPath().getString("name");

        LOG.info("Asserting product name: expected='{}', actual='{}'", expectedName, actualName);
        assertThat(actualName)
                .as("Expected product name to be '%s' but was '%s'", expectedName, actualName)
                .isEqualTo(expectedName);
    }

    @And("Product price should be {double}")
    public void productPriceShouldBe(double expectedPrice) {
        double actualPrice = response.jsonPath().getDouble("price");

        LOG.info("Asserting product price: expected={}, actual={}", expectedPrice, actualPrice);
        assertThat(actualPrice)
                .as("Expected product price to be %s but was %s", expectedPrice, actualPrice)
                .isEqualTo(expectedPrice);
    }

    @When("Send GET request to read all products")
    public void sendGETRequestToReadAllProducts() {
        LOG.info("Sending GET request to read all products");
        response = allProductsApi.get();

        LOG.info("Received response for GET all products: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @When("Send GET request to read categories")
    public void sendGETRequestToReadCategories() {
        LOG.info("Sending GET request to read all categories");
        response = categoriesApi.get();

        LOG.info("Received response for GET categories: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @Given("Product payload is prepared")
    public void productPayloadIsPrepared(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);
        LOG.info("Preparing product payload from data table row: {}", row);

        String id = row.get("id");
        String name = row.get("name");
        String description = row.get("description");
        double price = parseDouble(row.get("price"));
        int categoryId = parseInt(row.get("category_id"));

        if (id != null) {
            int idValue = parseInt(id);
            product = new Product(idValue, name, description, price, categoryId);
            LOG.info("Prepared product payload with id: {}", product);
        } else {
            product = new Product(name, description, price, categoryId);
            LOG.info("Prepared product payload without id: {}", product);
        }
    }

    @When("Send POST request to create product")
    public void sendPOSTRequestToCreateProduct() {
        LOG.info("Sending POST request to create product: {}", product);
        response = createProductApi.create(product);

        LOG.info("Received response for create product: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @When("Send PUT request to update product")
    public void sendPUTRequestToUpdateProduct() {
        LOG.info("Sending PUT request to update product: {}", product);
        response = updateProductApi.update(product);

        LOG.info("Received response for update product: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @When("Send DELETE request to delete product")
    public void sendDELETERequestToDeleteProduct() {

        LOG.info("Sending DELETE request to delete product with id={}", lastProductID.get().getId());
        response = deleteProductApi.delete(lastProductID.get());
        LOG.info("Sending DELETE request to delete product with id={}", lastProductID.get().getId());
    }

    @And("Message should be {string}")
    public void messageShouldBe(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        LOG.info("Asserting response message: expected='{}', actual='{}'",
                expectedMessage, actualMessage);

        assertThat(actualMessage)
                .as("Expected massage to be %s but was %s", expectedMessage, actualMessage)
                .isEqualTo(expectedMessage);
    }

    @Then("Response should contain expected headers for product read API")
    public void responseShouldContainExpectedHeadersForProductReadAPI() {
        LOG.info("Asserting response headers for product read API");
        response.then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .header("Connection", "Keep-Alive")
                .header("Content-Encoding", "gzip")
                .header("Server", containsString("Apache/2.4.33"));
        LOG.info("Headers for product read API response are valid");
    }
}
