package net.piotrl.music.modules.aggregation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AggregationCrudRepository extends CrudRepository<AggregationEntity, Long> {
    Optional<AggregationEntity> findFirstByAccountIdAndTypeAndStatusOrderByStartTimeDesc(long accountId, String type, String status);
    List<AggregationEntity> findAllByAccountIdOrderByStartTimeDesc(long accountId);
}
