package trainingxyz;

import io.restassured.http.ContentType;
import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class SweatbandTest {
    @Test
    public void createProductWithTaskModule(){
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Sweatband",
                "Moisture-wicking sports headband that keeps sweat and hair in place.",
                5,
                3
        );
        given().body(product).when().post(endpoint).then().assertThat().statusCode(201);
    }

    @Test
    public void updateProductWithTaskModule(){
        String endpoint = "http://localhost:8888/api_testing/product/create.php";
        Product product = new Product(
                "Sweatband",
                "Moisture-wicking sports headband that keeps sweat and hair in place.",
                6,
                3
        );
        given().
                body(product).when().put(endpoint).then().
                assertThat().
                statusCode(201);

    }

    @Test
    public void getProductWithTaskModule(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        Product product = new Product(21);

        given().queryParam("id", 21).
                when().get(endpoint).
                then().
                assertThat().
                statusCode(200).
                body("id",equalTo("21"));

    }

    @Test
    public void deleteProductWithTaskModule(){
        String endpoint = "http://localhost:8888/api_testing/product/delete.php";

        given().queryParam("id", 22).when().delete(endpoint).then().assertThat().statusCode(200);
    }

    @Test
    public void getDeserialzedProduct(){
        String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        Product actualProduct = given().
                                queryParam("id", 2).
                                when().get(endpoint).as(Product.class);
        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));
        given().
                queryParam("id", "2").
                when().
                get(endpoint).
                as(Product.class);
    }
}
