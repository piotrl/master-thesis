package net.piotrl.music.lastfm.scrobble.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LastFmTrackRepository extends CrudRepository<LastFmTrackData, Integer> {

}
