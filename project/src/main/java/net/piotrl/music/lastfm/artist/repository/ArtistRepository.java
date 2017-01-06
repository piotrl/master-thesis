package net.piotrl.music.lastfm.artist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

    public void insertIfExists(ArtistData artistData) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("name", artistData.getName())
                .addValue("mbid", artistData.getMbid())
                .addValue("imageUrl", artistData.getImageUrl());

        String query = "INSERT INTO lastfm_artist" +
                "   (name, mbid, image_url) " +
                "   VALUES " +
                "   (:name, :mbid, :imageUrl) " +
                "  " +
                " ON CONFLICT DO NOTHING" +
                " RETURNING id";

        Integer id = jdbcTemplate.queryForObject(query, sqlParameter, Integer.class);

        if (id == null) {
            ArtistData existingArtist = artistCrudRepository.findByNameOrMbid(
                    artistData.getName(),
                    artistData.getMbid()
            );
            id = existingArtist.getId();
        }

        artistData.setId(id);
    }
}
