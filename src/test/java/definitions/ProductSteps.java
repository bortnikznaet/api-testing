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

import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSteps {
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
        String actualName = response.jsonPath().getString("name");

        assertThat(actualName)
                .as("Expected product name to be '%s' but was '%s'", expectedName, actualName)
                .isEqualTo(expectedName);
    }

    @And("Product price should be {double}")
    public void productPriceShouldBe(double expectedPrice) {
        double actualPrice = response.jsonPath().getDouble("price");

        assertThat(actualPrice)
                .as("Expected product price to be %s but was %s", expectedPrice, actualPrice)
                .isEqualTo(expectedPrice);
    }

    @When("Send GET request to read all products")
    public void sendGETRequestToReadAllProducts() {
        response = allProductsApi.get();
    }

    @When("Send GET request to read categories")
    public void sendGETRequestToReadCategories() {
        response = categoriesApi.get();
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

        if (id != null) {
            int idValue = parseInt(id);
            product = new Product(idValue, name, description, price, categoryId);
        } else {
            product = new Product(name, description, price, categoryId);
        }
    }

    @When("Send POST request to create product")
    public void sendPOSTRequestToCreateProduct() {
        response = createProductApi.create(product);
    }

    @When("Send PUT request to update product")
    public void sendPUTRequestToUpdateProduct() {
        response = updateProductApi.update(product);
    }

    @When("Send DELETE request to delete product")
    public void sendDELETERequestToDeleteProduct() {
        response = deleteProductApi.delete(lastProductID.get());
    }

    @And("Message should be {string}")
    public void messageShouldBe(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");

        assertThat(actualMessage)
                .as("Expected massage to be %s but was %s", expectedMessage, actualMessage)
                .isEqualTo(expectedMessage);
    }
}
