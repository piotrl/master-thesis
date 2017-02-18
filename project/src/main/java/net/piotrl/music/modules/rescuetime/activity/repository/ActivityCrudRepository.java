package net.piotrl.music.modules.rescuetime.activity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityCrudRepository extends CrudRepository<ActivityEntity, Integer> {
}
