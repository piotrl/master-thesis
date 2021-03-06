package net.piotrl.music.modules.rescuetime.aggregation;

import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.modules.aggregation.AggregationContext;
import net.piotrl.music.modules.rescuetime.RescueTimeCaller;
import net.piotrl.music.modules.rescuetime.activity.ActivityService;
import net.piotrl.music.modules.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.modules.rescuetime.api.RescueTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;

@Slf4j
@Service
public class RescueTimeAggregation {

    private final ActivityService activityService;

    @Autowired
    public RescueTimeAggregation(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void startAggregation(AggregationContext context, LocalDate since) {
        RescueTimeRequest rescueTimeRequest = RescueTimeRequestUtil.buildRequest(context.getRescuetimeApiKey(), since, LocalDate.now());
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

        try {
            ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
            activityService.saveAggregationResult(context, call.getBody());
        } catch (HttpClientErrorException ex) {
            log.error("RescueTime http error: {}", ex.getRawStatusCode());
            log.error("RescueTime error response: {}", ex.getResponseBodyAsString());
            context.getResult().addError("Http error: " + ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        }
    }
}
