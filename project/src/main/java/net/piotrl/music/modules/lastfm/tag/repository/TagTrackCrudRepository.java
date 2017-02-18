package net.piotrl.music.modules.lastfm.tag.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TagTrackCrudRepository extends CrudRepository<TagTrackEntity, Long> {
}
