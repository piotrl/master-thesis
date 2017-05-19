package net.piotrl.music.web.home;

import net.piotrl.music.modules.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final ActivityCrudRepository activityRepository;
    private final ScrobbleCrudRepository scrobbleRepository;

    @Autowired
    public HomeService(ActivityCrudRepository activityRepository,
                       ScrobbleCrudRepository scrobbleRepository) {
        this.activityRepository = activityRepository;
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

        return dto;
    }
}
