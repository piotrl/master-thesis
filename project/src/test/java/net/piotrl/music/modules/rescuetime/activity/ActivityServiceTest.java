package net.piotrl.music.modules.rescuetime.activity;

import net.piotrl.music.modules.rescuetime.RescueTimeCaller;
import net.piotrl.music.modules.rescuetime.aggregation.RescueTimeRequestUtil;
import net.piotrl.music.modules.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.modules.rescuetime.api.RescueTimeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    @Transactional
    public void saveActivitiesToDatabase() throws Exception {
        String apiKey = "B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe";
        LocalDate to = LocalDate.now();
        LocalDate since = to.minusDays(7);

        RescueTimeRequest rescueTimeRequest = RescueTimeRequestUtil.buildRequest(apiKey, since, to);
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

            ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
            // FIXME: context is null
            activityService.saveAggregationResult(null, call.getBody());
    }
}