package net.piotrl.music.aggregation.lastfm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LastFmTrackRepository extends CrudRepository<LastFmTrackData, Integer> {

}
