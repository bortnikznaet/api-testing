package trainingxyz.support.api.endpoints;

import io.restassured.response.Response;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class ReadProductsAPI {
    ApiHelper api = new ApiHelper();

    public Response get() {
        return api
                .get(Endpoint.READ.path);
    }
}
