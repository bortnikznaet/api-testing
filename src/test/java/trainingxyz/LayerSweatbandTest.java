package trainingxyz;

import io.restassured.response.Response;
import models.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import trainingxyz.support.api.endpoints.*;
import trainingxyz.support.api.service.LastProductService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LayerSweatbandTest {
    ReadOneProductAPI oneProduct = new ReadOneProductAPI();
    CreateProductAPI createProductAPI = new CreateProductAPI();
    UpdateProductAPI updateProductAPI = new UpdateProductAPI();
    DeleteProductAPI deleteProductAPI = new DeleteProductAPI();
    LastProductService lastProductID = new LastProductService();

    @Test
    @Order(1)
    public void createProductWithTaskModule() {
        Product product = new Product(
                "Sweatband",
                "Moisture-wicking sports headband that keeps sweat and hair in place.",
                5,
                3
        );

        Response response = createProductAPI.create(product);
        response
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Order(2)
    public void updateProductWithTaskModule() {
        Product product = new Product(
                "Sweatband",
                "Moisture-wicking sports headband that keeps sweat and hair in place.",
                6,
                3
        );

        Response response = updateProductAPI.update(product);
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void getProductWithTaskModule() {
        Response response = oneProduct.get(lastProductID.get().getId());
        response
                .then()
                .assertThat()
                .statusCode(200);

        response
                .then()
                .log()
                .body();
    }

    @Test
    @Order(5)
    public void deleteProductWithTaskModule() {
        Response response = deleteProductAPI.delete(lastProductID.get());
        response
                .then()
                .assertThat()
                .statusCode(200);

        response.then().log().body();

    }

    @Test
    @Order(4)
    public void getDeserializeProduct() {
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct = oneProduct
                .get(2).
                as(Product.class);

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
    }
}
