package net.piotrl.music.rescuetime.api;

import lombok.Builder;
import lombok.Data;


/**
 * @apiNote https://www.rescuetime.com/developers
 */
@Builder
@Data
public class RescueTimeRequest {
    public final static String API_URL = "https://www.rescuetime.com/anapi/data";
    private final static String API_FORMAT = "json";

    private String key;

    private RescueTimeQueryParameters queryParameters;
}
