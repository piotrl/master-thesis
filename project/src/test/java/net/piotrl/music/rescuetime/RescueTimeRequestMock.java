package net.piotrl.music.rescuetime;


import lombok.experimental.UtilityClass;
import net.piotrl.music.rescuetime.api.RescueTimeQueryParameters;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;

@UtilityClass
public class RescueTimeRequestMock {

    public RescueTimeRequest buildRequest() {
        return buildRequest("B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe");
    }

    public RescueTimeRequest buildRequest(String apiKey) {
        RescueTimeQueryParameters queryParameters = buildParameters("2016-12-01", "2016-12-01");

        return RescueTimeRequest.builder()
                .key(apiKey)
                .queryParameters(queryParameters)
                .build();
    }

    private static RescueTimeQueryParameters buildParameters(String start, String to) {
        return RescueTimeQueryParameters.builder()
                    .perspective("interval")
                    .restrict_begin(start)
                    .restrict_end(to)
                    .build();
    }
}
