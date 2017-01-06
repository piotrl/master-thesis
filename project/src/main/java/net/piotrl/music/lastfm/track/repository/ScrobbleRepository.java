package net.piotrl.music.lastfm.track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ScrobbleRepository extends CrudRepository<ScrobbleEntity, Integer> {

}
