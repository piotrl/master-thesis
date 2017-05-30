package net.piotrl.music.web.home;

import net.piotrl.music.modules.aggregation.repository.AggregationCrudRepository;
import net.piotrl.music.modules.aggregation.repository.AggregationEntity;
import net.piotrl.music.modules.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityEntity;
import net.piotrl.music.web.home.view.FullDataSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final ActivityCrudRepository activityRepository;
    private final AggregationCrudRepository aggregationCrudRepository;
    private final ScrobbleCrudRepository scrobbleRepository;

    @Autowired
    public HomeService(ActivityCrudRepository activityRepository,
                       AggregationCrudRepository aggregationCrudRepository, ScrobbleCrudRepository scrobbleRepository) {
        this.activityRepository = activityRepository;
        this.aggregationCrudRepository = aggregationCrudRepository;
        this.scrobbleRepository = scrobbleRepository;
    }

    public FullDataSummary summary(long accountId) {
        int countScrobbles = scrobbleRepository.countByAccountId(accountId);
        List<ActivityEntity> allByAccountId = activityRepository.findAllByAccountId(accountId);
        int count = allByAccountId.size();
        Integer spentTime = allByAccountId.stream()
                .map(ActivityEntity::getSpentTime)
                .reduce(0, Integer::sum);

        FullDataSummary dto = new FullDataSummary();
        dto.setCountTracks(countScrobbles);
        dto.setCountActivities(count);
        dto.setCountActivitiesTime(spentTime / 3600);

        List<AggregationEntity> aggregationList = aggregationCrudRepository.findAllByAccountIdOrderByStartTimeDesc(accountId);
        List<AggregationEntity> first24Records = aggregationList.stream()
                .filter(ae -> !Objects.equals(ae.getStatus(), "IN_PROGRESS"))
                .limit(24)
                .collect(Collectors.toList());
        dto.setAggregationList(first24Records);

        return dto;
    }
}
