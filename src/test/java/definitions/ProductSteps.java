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
import trainingxyz.support.api.model.AllProductsResponseModel;
import trainingxyz.support.api.model.CategoriesResponseModel;
import trainingxyz.support.api.model.UpdateResponseModel;
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
        LOG.debug("Sending GET request to read product with id={}", id);
        response = oneProductApi.get(id);
    }

    @Then("Status code should be {int}")
    public void thenStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        assertThat(actualStatusCode)
                .as("Expected HTTP status code to be %s but was %s", expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @And("Product name should be {string}")
    public void productNameShouldBe(String expectedName) {
        Product actualProduct = response.as(Product.class);
        String actualName = actualProduct.getName();

        assertThat(actualName)
                .as("Expected product name to be '%s' but was '%s'", expectedName, actualName)
                .isEqualTo(expectedName);
    }

    @And("Product price should be {double}")
    public void productPriceShouldBe(double expectedPrice) {
        Product actualProduct = response.as(Product.class);
        double actualPrice = actualProduct.getPrice();

        assertThat(actualPrice)
                .as("Expected product price to be %s but was %s", expectedPrice, actualPrice)
                .isEqualTo(expectedPrice);
    }

    @When("Send GET request to read all products")
    public void sendGETRequestToReadAllProducts() {
        LOG.debug("Sending GET request to read all products");

        response = allProductsApi.get();
        AllProductsResponseModel body = response.as(AllProductsResponseModel.class);
        int size = (body.getRecords() == null) ? 0 : body.getRecords().size();

        LOG.info("GET All products finished and returned {} products", size);
    }

    @When("Send GET request to read categories")
    public void sendGETRequestToReadCategories() {
        LOG.debug("Sending GET request to read all categories");

        response = categoriesApi.get();
        CategoriesResponseModel body = response.as(CategoriesResponseModel.class);

        int size = (body.getRecords() == null) ? 0 : body.getRecords().size();

        LOG.info("GET All categories finished and returned {} categories", size);
    }

    @Given("Product payload is prepared with id {int}")
    public void productPayloadIsPreparedWithId(int id, DataTable dataTable) {
        this.product = buildSingleProduct(dataTable, id);
    }

    @Given("Product payload is prepared")
    public void productPayloadIsPrepared(DataTable dataTable) {
        this.product = buildSingleProduct(dataTable, null);
    }

    @When("Send POST request to create product")
    public void sendPOSTRequestToCreateProduct() {
        LOG.debug("Sending POST request to create product: {}", GSON.toJson(this.product));
        response = createProductApi.create(product);
    }

    @When("Send PUT request to update product")
    public void sendPUTRequestToUpdateProduct() {

        LOG.info("Sending PUT request to update product with id={}", product.getId());
        LOG.debug("Product update payload: {}", GSON.toJson(this.product));
        response = updateProductApi.update(product);
    }

    @When("Send DELETE request to delete product")
    public void sendDELETERequestToDeleteProduct() {
        Product lastProduct = lastProductID.get();
        LOG.info("Sending DELETE request to delete product with id={}", lastProduct.getId());
        response = deleteProductApi.delete(lastProduct);
    }

    @And("Message should be {string}")
    public void messageShouldBe(String expectedMessage) {
        UpdateResponseModel actualMessage = response.as(UpdateResponseModel.class);

        assertThat(actualMessage.getMessage())
                .as("Expected message to be %s but was %s", expectedMessage, actualMessage.getMessage())
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
    }


    private Product buildSingleProduct(DataTable dataTable, Integer idOverride) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("Product DataTable is empty");
        }
        if (rows.size() != 1) {
            throw new IllegalArgumentException("Expected exactly 1 product row, but got: " + rows.size());
        }

        Map<String, String> row = rows.iterator().next();
        try {
            Product.ProductBuilder builder = Product.builder()
                    .name(require(row, "name"))
                    .description(require(row, "description"))
                    .price(parseDoubleSafe(require(row, "price")))
                    .categoryId(parseIntSafe(require(row, "category_id")));

            if (idOverride != null) {
                builder.id(idOverride);
                LOG.info("Prepared product payload (update). id={}, name='{}'", idOverride, row.get("name"));
            } else {
                LOG.info("Prepared product payload (create). name='{}'", row.get("name"));
            }

            return builder.build();

        } catch (RuntimeException e) {
            LOG.error("Failed to prepare product payload. Row={}", row, e);
            throw e;
        }
    }

    private String require(Map<String, String> row, String key) {
        String val = row.get(key);
        if (val == null || val.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing/empty required field: " + key);
        }
        return val.trim();
    }

    private int parseIntSafe(String val) {
        return Integer.parseInt(val.trim());
    }

    private double parseDoubleSafe(String val) {
        return Double.parseDouble(val.trim());
    }
}
