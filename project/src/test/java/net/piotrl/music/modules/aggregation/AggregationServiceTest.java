package net.piotrl.music.modules.aggregation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static net.piotrl.music.mocks.AggregationPropertiesMock.globalContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregationServiceTest {
    @Autowired
    private AggregationService aggregationService;

    @Test
    public void startAggregation() {
        aggregationService.startAggregation(globalContext());
    }

}