package net.piotrl.music.rescuetime;

import net.piotrl.music.rescuetime.api.RescueTimeQueryParameters;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class RescueTimeCallerTest {

    @Test
    public void callApi() throws Exception {
        String apiKey = "B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe";
        RescueTimeCaller caller = new RescueTimeCaller();

        RescueTimeRequest request = buildRequest(apiKey);
        ResponseEntity<RescueTimeResponse> responseEntity = caller.callFor(request);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    private RescueTimeRequest buildRequest(String apiKey) {
        RescueTimeQueryParameters queryParameters = RescueTimeQueryParameters.builder()
                .restrict_begin("2016-12-01")
                .restrict_end("2016-12-01")
                .build();
        return RescueTimeRequest.builder()
                .key(apiKey)
                .queryParameters(queryParameters)
                .build();
    }
}