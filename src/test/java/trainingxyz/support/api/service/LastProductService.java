package trainingxyz.support.api.service;

import java.util.List;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.endpoints.ReadProductsAPI;

import static org.assertj.core.api.Assertions.assertThat;

public class LastProductService {
    ReadProductsAPI allProductsAPI = new ReadProductsAPI();

    public Product get() {
        Response response = allProductsAPI.get();
        List<Product> ids = response
                .jsonPath()
                .getList("records", Product.class);
        assertThat(ids).isNotEmpty();

        int lastId = ids.stream()
                .mapToInt(p -> p.getId())
                .max()
                .orElseThrow();

        return new Product(lastId);
    }
}
