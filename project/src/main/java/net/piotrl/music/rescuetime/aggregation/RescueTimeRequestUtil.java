package net.piotrl.music.rescuetime.aggregation;


import lombok.experimental.UtilityClass;
import net.piotrl.music.rescuetime.api.RescueTimeQueryParameters;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class RescueTimeRequestUtil {

    public RescueTimeRequest buildRequest(String apiKey, LocalDate since, LocalDate to) {

        return buildRequest(
                apiKey,
                since.format(DateTimeFormatter.ISO_LOCAL_DATE),
                to.format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    public RescueTimeRequest buildRequest(String apiKey, String since, String to) {
        RescueTimeQueryParameters queryParameters = buildParameters(since, to);

        return RescueTimeRequest.builder()
                .key(apiKey)
                .queryParameters(queryParameters)
                .build();
    }

    private static RescueTimeQueryParameters buildParameters(String start, String to) {
        return RescueTimeQueryParameters.builder()
                .perspective("interval")
                .resolution_time("minute")
                .restrict_begin(start)
                .restrict_end(to)
                .build();
    }
}
