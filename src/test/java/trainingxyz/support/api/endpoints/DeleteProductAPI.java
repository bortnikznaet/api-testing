package trainingxyz.support.api.endpoints;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class DeleteProductAPI {
    ApiHelper api = new ApiHelper();

    public Response delete(Product productId) {
        return api
                .delete(Endpoint.DELETE.path, productId);
    }
}
