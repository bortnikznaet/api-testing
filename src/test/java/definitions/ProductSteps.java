package definitions;

import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trainingxyz.support.api.service.LastProductService;
import trainingxyz.support.api.v1.*;

import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSteps {
    private static final Logger LOG = LogManager.getLogger(ProductSteps.class);
    private static final Gson GSON = new Gson();

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

        LOG.debug("Finished GET product request, id={}, status={}, body={}",
                id,
                response.statusCode(),
                GSON.toJson(response.getBody().asString()));
    }

    @Then("Status code should be {int}")
    public void thenStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();

        LOG.debug("Asserting HTTP status code: expected={}, actual={}", expectedStatusCode, actualStatusCode);
        assertThat(actualStatusCode)
                .as("Expected HTTP status code to be %s but was %s", expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @And("Product name should be {string}")
    public void productNameShouldBe(String expectedName) {
        String actualName = response.jsonPath().getString("name");

        LOG.debug("Asserting product name: expected='{}', actual='{}'", expectedName, actualName);
        assertThat(actualName)
                .as("Expected product name to be '%s' but was '%s'", expectedName, actualName)
                .isEqualTo(expectedName);
    }

    @And("Product price should be {double}")
    public void productPriceShouldBe(double expectedPrice) {
        double actualPrice = response.jsonPath().getDouble("price");

        LOG.debug("Asserting product price: expected={}, actual={}", expectedPrice, actualPrice);
        assertThat(actualPrice)
                .as("Expected product price to be %s but was %s", expectedPrice, actualPrice)
                .isEqualTo(expectedPrice);
    }

    @When("Send GET request to read all products")
    public void sendGETRequestToReadAllProducts() {
        LOG.info("Sending GET request to read all products");
        response = allProductsApi.get();
        int size = response.jsonPath().getList("records").size();

        LOG.info("GET All products finished and returned {} products", size);
    }

    @When("Send GET request to read categories")
    public void sendGETRequestToReadCategories() {
        LOG.info("Sending GET request to read all categories");
        response = categoriesApi.get();
        int size = response.jsonPath().getList("records").size();

        LOG.info("GET All categories finished and returned {} categories", size);
    }

    @Given("Product payload is prepared")
    public void productPayloadIsPrepared(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);

        String id = row.get("id");
        String name = row.get("name");
        String description = row.get("description");
        double price = parseDouble(row.get("price"));
        int categoryId = parseInt(row.get("category_id"));

        if (id != null && id.isEmpty()) {
            int idValue = parseInt(id);
            product = new Product(idValue, name, description, price, categoryId);
            LOG.debug("Prepared product payload with id: {}", GSON.toJson(product));
        } else {
            product = new Product(name, description, price, categoryId);
            LOG.debug("Prepared product payload without id: {}", GSON.toJson(product));
        }
    }

    @When("Send POST request to create product")
    public void sendPOSTRequestToCreateProduct() {
        response = createProductApi.create(product);

        LOG.debug("Received response for create product: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @When("Send PUT request to update product")
    public void sendPUTRequestToUpdateProduct() {

        LOG.info("Sending PUT request to update product: {}", GSON.toJson(product));
        response = updateProductApi.update(product);

        LOG.debug("Received response for update product: status={}, body={}",
                response.statusCode(), response.getBody().asString());
    }

    @When("Send DELETE request to delete product")
    public void sendDELETERequestToDeleteProduct() {

        LOG.info("Sending DELETE request to delete product with id={}", lastProductID.get().getId());
        response = deleteProductApi.delete(lastProductID.get());
        LOG.debug("Received DELETE request to delete product with id={}", lastProductID.get().getId());
    }

    @And("Message should be {string}")
    public void messageShouldBe(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        LOG.debug("Asserting response message: expected='{}', actual='{}'",
                expectedMessage, actualMessage);

        assertThat(actualMessage)
                .as("Expected massage to be %s but was %s", expectedMessage, actualMessage)
                .isEqualTo(expectedMessage);
    }

    @Then("Response should contain expected headers for product read API")
    public void responseShouldContainExpectedHeadersForProductReadAPI() {
        assertThat(response.getStatusCode())
                .as("Status code should be 200")
                .isEqualTo(200);

        assertThat(response.getHeader("Content-Type"))
                .as("Content-Type header")
                .isEqualTo("application/json");

        assertThat(response.getHeader("Connection"))
                .as("Connection header")
                .isEqualTo("Keep-Alive");

        assertThat(response.getHeader("Content-Encoding"))
                .as("Content-Encoding header")
                .isEqualTo("gzip");

        assertThat(response.getHeader("Server"))
                .as("Server header")
                .contains("Apache/2.4.33");

        LOG.info("Response headers for product read API are valid.");
    }
}
