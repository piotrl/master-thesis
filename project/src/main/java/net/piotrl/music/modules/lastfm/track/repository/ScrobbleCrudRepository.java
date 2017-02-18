package net.piotrl.music.modules.lastfm.track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ScrobbleCrudRepository extends CrudRepository<ScrobbleEntity, Integer> {

}
