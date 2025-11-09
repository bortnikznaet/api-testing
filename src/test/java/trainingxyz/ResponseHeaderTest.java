package trainingxyz;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import trainingxyz.support.api.v1.ReadOneProductApi;

import static org.hamcrest.Matchers.containsString;

public class ResponseHeaderTest {
    ReadOneProductApi oneProductApi = new ReadOneProductApi();

    @Test
    public void shouldReturnExpectedHeadersForProductReadApi() {
        int id = 1;

        Response response = oneProductApi.get(id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .header("Connection", "Keep-Alive")
                .header("Content-Encoding", "gzip")
                .header("Server", containsString("Apache/2.4.33")) ;
    }
}
