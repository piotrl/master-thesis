package net.piotrl.music.modules.rescuetime.aggregation;

import net.piotrl.music.modules.aggregation.AggregationContext;
import net.piotrl.music.modules.rescuetime.RescueTimeCaller;
import net.piotrl.music.modules.rescuetime.activity.ActivityService;
import net.piotrl.music.modules.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.modules.rescuetime.api.RescueTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

        ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
        activityService.saveAggregationResult(context, call.getBody());
    }
}
