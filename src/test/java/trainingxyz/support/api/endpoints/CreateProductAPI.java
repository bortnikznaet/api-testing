package trainingxyz.support.api.endpoints;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class CreateProductAPI {
    ApiHelper api = new ApiHelper();

    public Response create(Product product) {
        return api.post(Endpoint.CREATE.path, product);
    }
}
