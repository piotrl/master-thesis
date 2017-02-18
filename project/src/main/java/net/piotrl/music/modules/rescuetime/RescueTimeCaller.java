package net.piotrl.music.modules.rescuetime;

import net.piotrl.music.modules.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.modules.rescuetime.api.RescueTimeResponse;
import net.piotrl.music.shared.RestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RescueTimeCaller {

    public ResponseEntity<RescueTimeResponse> call(RescueTimeRequest api) {
        URI requestUri = url(api);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(requestUri, RescueTimeResponse.class);
    }

    public URI url(RescueTimeRequest api) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(RescueTimeRequest.API_URL)
                .queryParam("key", api.getKey());

        RestUtil.buildParams(api.getQueryParameters())
                .forEach(uriComponentsBuilder::queryParam);

        return uriComponentsBuilder.build()
                .encode()
                .toUri();
    }
}
