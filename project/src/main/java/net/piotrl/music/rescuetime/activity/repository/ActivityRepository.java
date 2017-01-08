package net.piotrl.music.rescuetime.activity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ActivityRepository extends CrudRepository<ActivityEntity, Integer> {

    ActivityEntity findFirstOrderByEndTime();
}
