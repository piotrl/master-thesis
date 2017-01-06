package net.piotrl.music.rescuetime.activity;

import com.google.common.primitives.Ints;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;


    public void saveAggregationResult(RescueTimeResponse activityApiResponse) {
        List<ActivityEntity> activities = activityApiResponse.getRows().stream()
                .map(row -> mapActivityTime(activityApiResponse.getRow_headers(), row))
                .collect(Collectors.toList());

        repository.save(activities);
    }

    private ActivityEntity mapActivityTime(List<String> columnNames, List<String> values) {
        String dateValue = getValue("Date", columnNames, values);
        String timeValue = getValue("Time Spent (seconds)", columnNames, values);
        String activityValue = getValue("ActivityEntity", columnNames, values);
        String categoryValue = getValue("Category", columnNames, values);
        String productivityValue = getValue("Productivity", columnNames, values);

        Integer time = Ints.tryParse(timeValue);
        LocalDateTime endTime = LocalDateTime.parse(dateValue).plusSeconds(time);
        return ActivityEntity.builder()
                .activityName(activityValue)
                .startTime(DateUtil.toDate(dateValue))
                .endTime(DateUtil.toDate(endTime))
                .spentTime(time)
                .categoryName(categoryValue)
                .productivity(Ints.tryParse(productivityValue))
                .build();
    }

    private String getValue(String columnName, List<String> columnNames, List<String> values) {
        int dateIndex = columnNames.indexOf(columnName);
        return values.get(dateIndex);
    }
}
