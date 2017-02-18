package net.piotrl.music.modules.rescuetime.activity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryCrudRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findFirstByName(String name);

}

