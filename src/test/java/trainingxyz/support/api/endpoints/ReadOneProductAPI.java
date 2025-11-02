package trainingxyz.support.api.endpoints;

import io.restassured.response.Response;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class ReadOneProductAPI {
    ApiHelper api = new ApiHelper();

    public Response get(int id) {
        return api
                .get(Endpoint.READ_ONE.path, id);
    }
}
