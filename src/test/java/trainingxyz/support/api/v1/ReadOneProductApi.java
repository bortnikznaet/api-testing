package trainingxyz.support.api.v1;

import io.restassured.response.Response;
import trainingxyz.support.api.ApiHelper;
import trainingxyz.support.api.Endpoint;

public class ReadOneProductApi extends ApiHelper {

    public Response get(int id) {
        return get(Endpoint.READ_ONE.path, id);
    }
}
