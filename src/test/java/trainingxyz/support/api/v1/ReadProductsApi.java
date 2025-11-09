package trainingxyz.support.api.v1;

import io.restassured.response.Response;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class ReadProductsApi extends ApiHelper{

    public Response get() {
        return get(Endpoint.READ.path);
    }
}
