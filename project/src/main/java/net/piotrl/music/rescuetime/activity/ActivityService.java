package net.piotrl.music.rescuetime.activity;

import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.aggregation.AggregationContext;
import net.piotrl.music.rescuetime.activity.repository.*;
import net.piotrl.music.rescuetime.api.RescueTimeResponse;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityService {

    private final ActivityCrudRepository activityCrudRepository;
    private final CategoryCrudRepository categoryCrudRepository;
    private final ActionCrudRepository actionCrudRepository;

    @Autowired
    public ActivityService(ActivityCrudRepository activityCrudRepository,
                           CategoryCrudRepository categoryCrudRepository,
                           ActionCrudRepository actionCrudRepository) {
        this.activityCrudRepository = activityCrudRepository;
        this.categoryCrudRepository = categoryCrudRepository;
        this.actionCrudRepository = actionCrudRepository;
    }

    public void saveAggregationResult(AggregationContext context, RescueTimeResponse activityApiResponse) {
        List<ActivityEntity> activities = activityApiResponse.getRows().stream()
                .map(row -> mapActivityTime(activityApiResponse.getRow_headers(), row))
                .map(activity -> {
                    activity.setAccountId(context.getAccountId());
                    return activity;
                })
                .collect(Collectors.toList());
        log.info("RescueTime aggregation | User: {} | Records: {}", context.getAccountId(), activities.size());

        mapRelations(activities);
        activityCrudRepository.save(activities);
    }

    private void mapRelations(List<ActivityEntity> activities) {
        activities.forEach(activity -> {
            CategoryEntity categoryEntity = findCategoryEntity(activity.getCategoryName());
            ActionEntity actionEntity = findActionEntity(activity.getActivityName(), categoryEntity.getId());
            activity.setCategoryId(categoryEntity.getId());
            activity.setActionId(actionEntity.getId());
        });
    }

    private CategoryEntity findCategoryEntity(String categoryName) {
        return categoryCrudRepository.findFirstByName(categoryName)
                .orElseGet(() -> categoryCrudRepository.save(new CategoryEntity(categoryName)));
    }

    private ActionEntity findActionEntity(String actionName, Long categoryId) {
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setName(actionName);
        actionEntity.setCategoryId(categoryId);

        return actionCrudRepository.findFirstByName(actionName)
                .orElseGet(() -> actionCrudRepository.save(actionEntity));
    }

    private ActivityEntity mapActivityTime(List<String> columnNames, List<String> values) {
        String dateValue = getValue("Date", columnNames, values);
        String timeValue = getValue("Time Spent (seconds)", columnNames, values);
        String activityValue = getValue("Activity", columnNames, values);
        String categoryValue = getValue("Category", columnNames, values);
        String productivityValue = getValue("Productivity", columnNames, values);

        Integer time = Ints.tryParse(timeValue);
        LocalDateTime endTime = LocalDateTime.parse(dateValue).plusMinutes(5);
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

        if (dateIndex >= values.size()) {
            log.error("Column not exists | column: %s | index: %s | values: %s", columnName, dateIndex, values);
        }
        return values.get(dateIndex);
    }
}
