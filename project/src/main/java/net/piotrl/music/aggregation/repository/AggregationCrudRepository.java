package net.piotrl.music.aggregation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregationCrudRepository extends CrudRepository<AggregationEntity, Integer> {
    Optional<AggregationEntity> findOneByAccountIdAndType(long accountId, String type);
}
