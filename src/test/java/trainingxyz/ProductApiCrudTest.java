package trainingxyz;

import io.restassured.response.Response;
import models.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import trainingxyz.support.api.v1.*;
import trainingxyz.support.api.service.LastProductService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductApiCrudTest {
    ReadCategoriesApi categoriesAPI = new ReadCategoriesApi();
    ReadProductsApi allProductsAPI = new ReadProductsApi();
    ReadOneProductApi oneProduct = new ReadOneProductApi();
    CreateProductApi createProductAPI = new CreateProductApi();
    UpdateProductApi updateProductAPI = new UpdateProductApi();
    DeleteProductApi deleteProductAPI = new DeleteProductApi();
    LastProductService lastProductID = new LastProductService();

    @Test()
    @Order(1)
    public void getCategories() {
        Response response = categoriesAPI.get();
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void getAllProducts() {
        Response response = allProductsAPI.get();
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void getOneProduct() {
        int id = 1;

        Response response = oneProduct.get(id);
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(4)
    public void createProduct() {
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                1000,
                3
        );

        Response response = createProductAPI.create(product);
        response
                .then()
                .assertThat()
                .statusCode(201);

        response.then().log().body();
    }

    @Test
    @Order(5)
    public void updateProduct() {
        Product product = new Product(
                20,
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                54,
                2);

        Response response = updateProductAPI.update(product);
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(6)
    public void deleteProduct() {
        Response response = deleteProductAPI.delete(lastProductID.get());
        response
                .then()
                .assertThat()
                .statusCode(200);

        response.then().log().body();
    }
}
