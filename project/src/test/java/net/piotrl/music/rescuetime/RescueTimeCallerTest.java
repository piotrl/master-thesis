package net.piotrl.music.rescuetime;

import net.piotrl.music.rescuetime.api.RescueTimeQueryParameters;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;

public class RescueTimeCallerTest {

    @Test
    public void callApi() throws Exception {
        String apiKey = "B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe";
        RescueTimeCaller caller = new RescueTimeCaller();

        RescueTimeRequest request = buildRequest(apiKey);
        ResponseEntity<RescueTimeResponse> responseEntity = caller.call(request);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test(expected = HttpClientErrorException.class)
    public void callApiWithWrongKey() throws Exception {
        String apiKey = "wrong";
        RescueTimeCaller caller = new RescueTimeCaller();

        RescueTimeRequest request = buildRequest(apiKey);
        caller.call(request);
    }

    @Test
    public void callApiWithoutParams() throws Exception {
        String apiKey = "B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe";
        RescueTimeCaller caller = new RescueTimeCaller();

        RescueTimeRequest request = RescueTimeRequest.builder()
                .key(apiKey)
                .build();
        ResponseEntity<RescueTimeResponse> entity = caller.call(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody().getRows()).isEmpty();
    }

    private RescueTimeRequest buildRequest(String apiKey) {
        RescueTimeQueryParameters queryParameters = RescueTimeQueryParameters.builder()
                .perspective("interval")
                .restrict_begin("2016-12-01")
                .restrict_end("2016-12-01")
                .build();

        return RescueTimeRequest.builder()
                .key(apiKey)
                .queryParameters(queryParameters)
                .build();
    }
}