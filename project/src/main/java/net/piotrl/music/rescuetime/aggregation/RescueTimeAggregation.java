package net.piotrl.music.rescuetime.aggregation;

import net.piotrl.music.account.Account;
import net.piotrl.music.rescuetime.RescueTimeCaller;
import net.piotrl.music.rescuetime.activity.ActivityService;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
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

    public void startAggregation(Account account, LocalDate since) {
        RescueTimeRequest rescueTimeRequest = RescueTimeRequestUtil.buildRequest(account.getRescuetimeApiKey(), since, LocalDate.now());
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

        ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
        activityService.saveAggregationResult(account, call.getBody());
    }
}
