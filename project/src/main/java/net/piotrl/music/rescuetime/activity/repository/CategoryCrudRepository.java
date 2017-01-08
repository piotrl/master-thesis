package net.piotrl.music.rescuetime.activity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CategoryCrudRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findFirstByName(String name);

}

