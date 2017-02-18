package net.piotrl.music.modules.lastfm.artist.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ArtistCrudRepository extends CrudRepository<ArtistEntity, Integer> {

    @Query(" SELECT a FROM ArtistEntity a " +
            "WHERE " +
            "   (a.mbid IS NOT NULL AND a.mbid = ?1) " +
            "OR " +
            "   (a.mbid IS NULL AND a.name = ?2) "
    )
    ArtistEntity findArtistByMbidThenByName(String mbid, String name);
}
