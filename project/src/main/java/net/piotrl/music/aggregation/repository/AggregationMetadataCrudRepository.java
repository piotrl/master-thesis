package net.piotrl.music.aggregation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregationMetadataCrudRepository extends CrudRepository<AggregationMetadataEntity, Long> {
}