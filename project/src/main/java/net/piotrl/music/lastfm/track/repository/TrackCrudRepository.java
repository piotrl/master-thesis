package net.piotrl.music.lastfm.track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TrackCrudRepository extends CrudRepository<TrackEntity, Integer> {

    TrackEntity findFirstByMbidOrNameOrderByMbid(String mbid, String name);

}
