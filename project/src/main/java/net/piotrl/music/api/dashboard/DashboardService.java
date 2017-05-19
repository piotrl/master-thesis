package net.piotrl.music.api.dashboard;

import net.piotrl.music.modules.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityCrudRepository;
import net.piotrl.music.modules.rescuetime.activity.repository.ActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final ActivityCrudRepository activityRepository;
    private final ScrobbleCrudRepository scrobbleRepository;

    @Autowired
    public DashboardService(ActivityCrudRepository activityRepository,
                            ScrobbleCrudRepository scrobbleRepository) {
        this.activityRepository = activityRepository;
        this.scrobbleRepository = scrobbleRepository;
    }

    public long countScrobbles(long accountId) {
        return scrobbleRepository.countByAccountId(accountId);
    }

    public long countActivities(long accountId) {
        List<ActivityEntity> allByAccountId = activityRepository.findAllByAccountId(accountId);
        int count = allByAccountId.size();
        Integer spentTime = allByAccountId.stream()
                .map(ActivityEntity::getSpentTime)
                .reduce(0, Integer::sum);

        return spentTime;
    }
}
