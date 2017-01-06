package net.piotrl.music.lastfm.tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TagCrudRepository extends CrudRepository<TagEntity, Integer> {

    TagEntity findByName(String name);
}
