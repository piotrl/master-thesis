package net.piotrl.music.rescuetime.aggregation;

import net.piotrl.music.account.Account;
import net.piotrl.music.rescuetime.RescueTimeCaller;
import net.piotrl.music.rescuetime.activity.repository.ActivityEntity;
import net.piotrl.music.rescuetime.activity.ActivityService;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public class RescueTimeAggregation {

    private final ActivityService activityService;

    @Autowired
    public RescueTimeAggregation(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void continueAggregation(Account account) {
        ActivityEntity lastAggregatedActivity = activityService.getLastAggregatedActivity();
        LocalDate lastAggregationDate = DateUtil.toLocalDate(lastAggregatedActivity.getEndTime());

        startAggregation(account.getRescuetimeApiKey(), lastAggregationDate);
    }

    public void startAggregation(String apiKey, LocalDate since) {
        RescueTimeRequest rescueTimeRequest = RescueTimeRequestUtil.buildRequest(apiKey, since, LocalDate.now());
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

        ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
        activityService.saveAggregationResult(call.getBody());
    }
}
