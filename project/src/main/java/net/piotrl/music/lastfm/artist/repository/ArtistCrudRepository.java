package net.piotrl.music.lastfm.artist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArtistCrudRepository extends CrudRepository<ArtistData, Integer> {

    ArtistData findByNameOrMbid(String name, String mbid);
}
