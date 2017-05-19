package net.piotrl.music.modules.rescuetime.activity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityCrudRepository extends CrudRepository<ActivityEntity, Integer> {

    List<ActivityEntity> findAllByAccountId(long accountId);
}
