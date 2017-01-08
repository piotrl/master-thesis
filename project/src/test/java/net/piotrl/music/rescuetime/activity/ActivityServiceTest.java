package net.piotrl.music.rescuetime.activity;

import net.piotrl.music.rescuetime.RescueTimeCaller;
import net.piotrl.music.rescuetime.aggregation.RescueTimeRequestUtil;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    public void saveActivitiesToDatabase() throws Exception {
        String apiKey = "B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe";
        LocalDate to = LocalDate.now();
        LocalDate since = to.minusDays(7);

        RescueTimeRequest rescueTimeRequest = RescueTimeRequestUtil.buildRequest(apiKey, since, to);
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

        ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
        activityService.saveAggregationResult(null, call.getBody());
    }
}