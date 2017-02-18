package net.piotrl.music.modules.rescuetime.aggregation;

import net.piotrl.music.mocks.AggregationPropertiesMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RescueTimeAggregationTest {
    @Autowired
    private RescueTimeAggregation rescueTimeAggregation;

    @Test
    public void aggregationFromLastSixteenDays() throws Exception {
        LocalDate recentDate = LocalDate.now().minusDays(16);
        rescueTimeAggregation.startAggregation(AggregationPropertiesMock.globalContext(), recentDate);
    }
}