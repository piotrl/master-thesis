package net.piotrl.music.rescuetime.activity;

import net.piotrl.music.rescuetime.RescueTimeCaller;
import net.piotrl.music.rescuetime.RescueTimeRequestMock;
import net.piotrl.music.rescuetime.api.RescueTimeRequest;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityEntityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    public void saveActivitiesToDatabase() throws Exception {
        RescueTimeRequest rescueTimeRequest = RescueTimeRequestMock.buildRequest();
        RescueTimeCaller rescueTimeCaller = new RescueTimeCaller();

        ResponseEntity<RescueTimeResponse> call = rescueTimeCaller.call(rescueTimeRequest);
        activityService.saveAggregationResult(call.getBody());
    }
}