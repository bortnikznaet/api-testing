package trainingxyz.support.api.v1;

import io.restassured.response.Response;
import models.Product;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class CreateProductApi extends ApiHelper{

    public Response create(Product product) {
        return post(Endpoint.CREATE.path, product);
    }
}
