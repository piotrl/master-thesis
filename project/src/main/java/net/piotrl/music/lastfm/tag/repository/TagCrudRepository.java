package net.piotrl.music.lastfm.tag.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface TagCrudRepository extends CrudRepository<TagEntity, Long> {
    Optional<TagEntity> findFirstByName(String name);
}
