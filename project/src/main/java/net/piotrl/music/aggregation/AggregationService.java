package net.piotrl.music.aggregation;

import net.piotrl.music.account.Account;
import net.piotrl.music.aggregation.repository.AggregationCrudRepository;
import net.piotrl.music.aggregation.repository.AggregationEntity;
import net.piotrl.music.lastfm.aggregation.LastfmAggregationService;
import net.piotrl.music.rescuetime.aggregation.RescueTimeAggregation;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AggregationService {

    private final static int DEFAULT_AGGREGATION_DAYS = 14;

    private final AggregationCrudRepository aggregationCrudRepository;
    private final RescueTimeAggregation rescueTimeAggregation;
    private final LastfmAggregationService lastfmAggregationService;

    @Autowired
    public AggregationService(AggregationCrudRepository aggregationCrudRepository,
                              RescueTimeAggregation rescueTimeAggregation,
                              LastfmAggregationService lastfmAggregationService) {
        this.aggregationCrudRepository = aggregationCrudRepository;
        this.rescueTimeAggregation = rescueTimeAggregation;
        this.lastfmAggregationService = lastfmAggregationService;
    }

    public void startAggregation(Account account) {
        startRescueTimeAggregation(account);
        startLastfmAggregation(account);
    }

    public void startRescueTimeAggregation(Account account) {
        LocalDateTime startingPoint = lastAggregation(account.getId(), AggregationType.RESCUE_TIME);
        AggregationEntity entity = aggregationStatusInProgress(account, AggregationType.RESCUE_TIME);
        try {
            rescueTimeAggregation.startAggregation(account, startingPoint.toLocalDate());
        } catch (Exception e) {
            aggregationStatusFailed(entity, e);
        }
    }

    public void startLastfmAggregation(Account account) {
        LocalDateTime startingPoint = lastAggregation(account.getId(), AggregationType.LAST_FM);
        AggregationEntity entity = aggregationStatusInProgress(account, AggregationType.LAST_FM);
        try {
            lastfmAggregationService.startAggregation(account, startingPoint.toLocalDate());
        } catch (Exception e) {
            aggregationStatusFailed(entity, e);
        }
    }

    private void aggregationStatusFailed(AggregationEntity entity, Exception e) {
        entity.setStatus(AggregationStatus.FAILED.toString());
        entity.setFinishTime(new Date());
        entity.setDetails(e.getMessage());
        aggregationCrudRepository.save(entity);
    }

    private AggregationEntity aggregationStatusInProgress(Account account, AggregationType type) {
        AggregationEntity entity = new AggregationEntity();
        entity.setType(type.toString());
        entity.setAccountId(account.getId());
        entity.setStartTime(new Date());
        entity.setStatus(AggregationStatus.IN_PROGRESS.toString());

        return aggregationCrudRepository.save(entity);
    }

    private LocalDateTime lastAggregation(long accountId, AggregationType type) {
        Optional<AggregationEntity> lastAggregation = aggregationCrudRepository.findOneByAccountIdAndType(
                accountId, type.toString()
        );

        if (!lastAggregation.isPresent()) {
            return LocalDateTime.now()
                    .minusDays(DEFAULT_AGGREGATION_DAYS);
        }

        return DateUtil.toLocalDateTime(lastAggregation.get().getStartTime());
    }
}
