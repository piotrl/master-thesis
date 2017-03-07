package net.piotrl.music.modules.lastfm.track.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TrackCrudRepository extends CrudRepository<TrackEntity, Integer> {

    @Query(" SELECT t FROM TrackEntity t " +
            " WHERE (t.mbid IS NOT NULL AND t.mbid = ?1) "
    )
    TrackEntity findTrackByMbid(String mbid);

    @Query(" SELECT t FROM TrackEntity t " +
            " WHERE (t.mbid IS NULL AND t.name = ?1) "
    )
    TrackEntity findTrackByName(String name);

}
