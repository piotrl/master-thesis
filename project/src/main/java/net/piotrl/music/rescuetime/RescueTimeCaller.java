package net.piotrl.music.rescuetime;

import com.google.gson.Gson;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RescueTimeCaller {

    public ResponseEntity<RescueTimeResponse> callFor(RescueTimeRequest api) {
        URI requestUri = url(api);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(requestUri, RescueTimeResponse.class);
    }

    public URI url(RescueTimeRequest api) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(RescueTimeRequest.API_URL)
                .queryParam("key", api.getKey());
        Map<String, String> stringStringMap = buildParams(api);
        stringStringMap.forEach(uriComponentsBuilder::queryParam);


        return uriComponentsBuilder.build()
                .encode()
                .toUri();
    }

    private Map<String, String> buildParams(RescueTimeRequest api) {
        Gson gson = new Gson();
        String json = gson.toJson(api.getQueryParameters());

        Map<String, String> params = new HashMap<>();
        params = gson.fromJson(json, params.getClass());

        return params;
    }
}
