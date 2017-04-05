package net.piotrl.music.api.summary;

import net.piotrl.music.api.summary.dto.ArtistProductivity;
import net.piotrl.music.api.summary.dto.ProductivityValue;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class StatsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public StatsRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public List<ArtistProductivity> mostPopularArtistsProductivityStats(LocalDate month, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(month.withDayOfMonth(1)))
                .addValue("to", DateUtil.toDate(month.withDayOfMonth(month.lengthOfMonth())))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  artist.name                AS artistName, " +
                "  sum(track.duration) / 60.0 AS sumMinutes, " +
                "  count(*)         AS countTracks, " +
                "  AVG(ra.productivity)       AS AVG_PRODUCTIVITY, " +
                "  count(CASE WHEN ra.productivity = -2 " +
                "    THEN 1 END)              AS VERY_UNPRODUCTIVE, " +
                "  count(CASE WHEN ra.productivity = -1 " +
                "    THEN 1 END)              AS UNPRODUCTIVE, " +
                "  count(CASE WHEN ra.productivity = 0 " +
                "    THEN 1 END)              AS NEUTRAL, " +
                "  count(CASE WHEN ra.productivity = 1 " +
                "    THEN 1 END)              AS PRODUCTIVE, " +
                "  count(CASE WHEN ra.productivity = 2 " +
                "    THEN 1 END)              AS VERY_PRODUCTIVE " +
                "FROM lastfm_scrobble scrobble " +
                "  JOIN lastfm_track track ON scrobble.track_id = track.id " +
                "  JOIN lastfm_artist artist ON track.artist_id = artist.id " +
                "  JOIN rescuetime_activity ra ON scrobble.played_when >= ra.start_time AND scrobble.played_when <= ra.end_time " +
                "WHERE scrobble.played_when >= :from AND scrobble.played_when <= :to " +
                "      AND scrobble.account_id = :accountId " +
                "      AND ra.account_id = :accountId " +
                "GROUP BY artist.id " +
                "ORDER BY sumMinutes DESC " +
                "LIMIT 10;";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(ArtistProductivity.class)
        );
    }

    public ProductivityValue averageProductivityForMusic(LocalDate month, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(month.withDayOfMonth(1)))
                .addValue("to", DateUtil.toDate(month.withDayOfMonth(month.lengthOfMonth())))
                .addValue("accountId", accountId);

        String sql = "";
        return jdbcOperations.queryForObject(
                sql, sqlParams, new BeanPropertyRowMapper<>(ProductivityValue.class)
        );
    }
}
