package net.piotrl.music.modules.aggregation;

import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.modules.aggregation.repository.AggregationCrudRepository;
import net.piotrl.music.modules.aggregation.repository.AggregationEntity;
import net.piotrl.music.modules.lastfm.aggregation.LastfmAggregationService;
import net.piotrl.music.modules.rescuetime.aggregation.RescueTimeAggregation;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
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

    public void startAggregation(AggregationContext context) {
        startRescueTimeAggregation(context);
        startLastfmAggregation(context);
    }

    private void startRescueTimeAggregation(AggregationContext context) {
        LocalDateTime startingPoint = lastAggregation(context.getAccountId(), AggregationType.RESCUE_TIME);
        AggregationEntity entity = aggregationStatusInProgress(context, AggregationType.RESCUE_TIME);
        try {
            rescueTimeAggregation.startAggregation(context, startingPoint.toLocalDate());
            aggregationStatusFinished(entity);
        } catch (Exception e) {
            aggregationStatusFailed(entity, e);
        }
    }

    private void startLastfmAggregation(AggregationContext context) {
        LocalDateTime startingPoint = lastAggregation(context.getAccountId(), AggregationType.LAST_FM);
        AggregationEntity entity = aggregationStatusInProgress(context, AggregationType.LAST_FM);
        try {
            lastfmAggregationService.startAggregation(context, startingPoint.toLocalDate());
            aggregationStatusFinished(entity);
        } catch (Exception e) {
            aggregationStatusFailed(entity, e);
        }
    }

    private void aggregationStatusFailed(AggregationEntity entity, Exception e) {
        log.error("Aggregation failed | {} | User: {}", entity.getType(), entity.getAccountId());
        log.error("Aggregation failure exception", e);

        entity.setStatus(AggregationStatus.FAILED.toString());
        entity.setFinishTime(new Date());
        entity.setDetails(e.getMessage());
        aggregationCrudRepository.save(entity);
    }

    private void aggregationStatusFinished(AggregationEntity entity) {
        log.info("Aggregation {} finished for Account {}", entity.getType(), entity.getAccountId());

        entity.setStatus(AggregationStatus.SUCCESS.toString());
        entity.setFinishTime(new Date());
        aggregationCrudRepository.save(entity);
    }

    private AggregationEntity aggregationStatusInProgress(AggregationContext context, AggregationType type) {
        AggregationEntity entity = new AggregationEntity();
        entity.setType(type.toString());
        entity.setAccountId(context.getAccountId());
        entity.setStartTime(new Date());
        entity.setStatus(AggregationStatus.IN_PROGRESS.toString());

        log.info("Aggregation {} started | User: {}", type, context.getAccountId());
        return aggregationCrudRepository.save(entity);
    }

    private LocalDateTime lastAggregation(long accountId, AggregationType type) {
        Optional<AggregationEntity> lastAggregation = aggregationCrudRepository.findFirstByAccountIdAndTypeAndStatusOrderByStartTimeDesc(
                accountId, type.toString(), AggregationStatus.SUCCESS.toString()
        );

        if (!lastAggregation.isPresent()) {
            return LocalDateTime.now()
                    .minusDays(DEFAULT_AGGREGATION_DAYS);
        }

        return DateUtil.toLocalDateTime(lastAggregation.get().getStartTime());
    }
}
