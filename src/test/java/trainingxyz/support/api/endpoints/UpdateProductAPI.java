package trainingxyz.support.api.endpoints;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class UpdateProductAPI {
    ApiHelper api = new ApiHelper();

    public Response update(Product product) {
        return api
                .put(Endpoint.UPDATE.path, product);
    }
}
