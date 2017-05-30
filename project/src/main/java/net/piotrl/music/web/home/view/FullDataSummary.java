package net.piotrl.music.web.home.view;

import lombok.Data;
import net.piotrl.music.modules.aggregation.repository.AggregationEntity;

import java.util.List;

@Data
public class FullDataSummary {
    private int countTracks;
    private int countActivities;
    private int countActivitiesTime;

    private List<AggregationEntity> aggregationList;
}
