package net.piotrl.music.api.raw;

import net.piotrl.music.mocks.AggregationPropertiesMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RawDataRepositoryTest {
    @Autowired
    private RawDataRepository rawDataRepository;

    @Test
    public void rawActivities() throws Exception {
        LocalDate from = LocalDate.of(2017, Month.FEBRUARY, 1);
        LocalDate to = LocalDate.now();
        long accountId = AggregationPropertiesMock.globalContext().getAccountId();

        List<RawActivity> rawActivities = rawDataRepository.rawActivities(from, to, accountId);
        assertThat(rawActivities).isNotEmpty();
    }

}