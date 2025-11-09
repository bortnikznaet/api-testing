package trainingxyz.support.api.v1;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class DeleteProductApi extends ApiHelper {

    public Response delete(Product productId) {
        return delete(Endpoint.DELETE.path, productId);
    }
}
