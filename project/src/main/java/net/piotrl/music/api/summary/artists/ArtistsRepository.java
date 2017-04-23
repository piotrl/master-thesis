package net.piotrl.music.api.summary.artists;

import net.piotrl.music.api.summary.tags.TagSummary;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ArtistsRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public ArtistsRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public List<ArtistsSummary> mostPopularArtists(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(firstDayOfMonth))
                .addValue("to", DateUtil.toDate(lastDayOfMonth))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  track.artist               AS name, " +
                "  max(track.image_url_small) AS imageUrl, " +
                "  count(*)                   AS playedTimes " +
                "FROM (SELECT DISTINCT ON (scrobbleId) * " +
                "      FROM music_activity) ma " +
                "  JOIN lastfm_track track ON track.id = ma.trackid " +
                "WHERE ma.activitystarted >= :from " +
                "      AND ma.activitystarted <= :to " +
                "      AND ma.accountid = :accountId " +
                "GROUP BY track.artist " +
                "ORDER BY playedTimes DESC " +
                "LIMIT 10 ";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(ArtistsSummary.class)
        );
    }
}
