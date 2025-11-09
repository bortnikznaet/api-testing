package trainingxyz.support.api.v1;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class UpdateProductApi extends ApiHelper {

    public Response update(Product product) {
        return put(Endpoint.UPDATE.path, product);
    }
}
