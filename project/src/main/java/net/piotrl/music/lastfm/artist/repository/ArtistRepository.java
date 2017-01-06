package net.piotrl.music.lastfm.artist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ArtistRepository {

    private final ArtistCrudRepository artistCrudRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ArtistRepository(ArtistCrudRepository artistCrudRepository,
                            NamedParameterJdbcTemplate jdbcTemplate) {
        this.artistCrudRepository = artistCrudRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertIfExists(ArtistEntity artistEntity) {
        ArtistEntity existingArtist = artistCrudRepository.findFirstByMbidOrNameOrderByMbid(
                artistEntity.getMbid(), artistEntity.getName()
        );
        if (existingArtist == null) {
            existingArtist = artistCrudRepository.save(artistEntity);
        }

        artistEntity.setId(existingArtist.getId());
    }
}
