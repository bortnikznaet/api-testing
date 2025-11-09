package trainingxyz.support.api.service;

import java.util.List;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.v1.ReadProductsApi;

import static org.assertj.core.api.Assertions.assertThat;

public class LastProductService {
    ReadProductsApi allProductsAPI = new ReadProductsApi();

    public Product get() {
        Response response = allProductsAPI.get();
        List<Product> ids = response
                .jsonPath()
                .getList("records", Product.class);
        assertThat(ids)
                .as("Product list returned by GET /read.php should not be empty")
                .isNotEmpty();

        int lastId = ids.stream()
                .mapToInt(p -> p.getId())
                .max()
                .orElseThrow();

        return new Product(lastId);
    }
}
