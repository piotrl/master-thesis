package net.piotrl.music.api.summary;

import net.piotrl.music.api.summary.dto.ArtistProductivity;
import net.piotrl.music.api.summary.dto.MusicActivitySalienceSummary;
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


    public List<ArtistProductivity> mostPopularArtistsProductivityStats(LocalDate from, LocalDate to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  artist.name                AS name, " +
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

    public ProductivityValue averageProductivityForMusic(LocalDate from, LocalDate to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "";
        return jdbcOperations.queryForObject(
                sql, sqlParams, new BeanPropertyRowMapper<>(ProductivityValue.class)
        );
    }

    public List<MusicActivitySalienceSummary> musicProductivitySalienceMonthly(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(firstDayOfMonth))
                .addValue("to", DateUtil.toDate(lastDayOfMonth))
                .addValue("accountId", accountId);

        String sql = "" +
                "WITH aggregation_summary AS ( " +
                "    SELECT " +
                "      day                                   AS aggregated_day, " +
                "      sum(music) / 3600.0                   AS music, " +
                "      sum(activity) / 3600.0                AS activity, " +
                "      (sum(activity) - sum(music)) / 3600.0 AS salience " +
                "    FROM (SELECT " +
                "            played_when :: DATE AS day, " +
                "            track.duration      AS music, " +
                "            0                   AS activity " +
                "          FROM lastfm_scrobble scrobble " +
                "            JOIN lastfm_track track ON scrobble.track_id = track.id " +
                "          WHERE scrobble.account_id = :accountId " +
                "          UNION " +
                "          SELECT " +
                "            start_time :: DATE AS day, " +
                "            0                  AS music, " +
                "            spent_time         AS activity " +
                "          FROM rescuetime_activity " +
                "          WHERE rescuetime_activity.account_id = :accountId " +
                "         ) m " +
                "    GROUP BY day " +
                "    ORDER BY day " +
                ") " +
                "SELECT " +
                "  date AS timestamp, " +
                "  summary.* " +
                "FROM generate_series( " +
                "         DATE_TRUNC('day', :from :: DATE), " +
                "         DATE_TRUNC('day', :to :: DATE), " +
                "         '1 day' :: INTERVAL " +
                "     ) date " +
                "  LEFT JOIN aggregation_summary summary ON summary.aggregated_day = date";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(MusicActivitySalienceSummary.class)
        );
    }

    public List<MusicActivitySalienceSummary> musicPlayedDuringActivities(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(firstDayOfMonth))
                .addValue("to", DateUtil.toDate(lastDayOfMonth))
                .addValue("accountId", accountId);

        String sql = "" +
               "WITH aggregation_summary AS ( " +
                "    SELECT " +
                "      day                                   AS aggregated_day, " +
                "      sum(music) / 3600.0                   AS music, " +
                "      sum(activity) / 3600.0                AS activity, " +
                "      (sum(activity) - sum(music)) / 3600.0 AS salience " +
                "    FROM (SELECT DISTINCT ON (m.trackid, m.activitystarted) " +
                "            m.activitystarted :: DATE AS day, " +
                "            track.duration            AS music, " +
                "            0                         AS activity " +
                "          FROM music_activity m " +
                "            LEFT JOIN lastfm_scrobble scrobble ON m.scrobbleid = scrobble.id " +
                "            LEFT JOIN lastfm_track track ON scrobble.track_id = track.id " +
                "          WHERE m.accountid = :accountId " +
                "          UNION " +
                "          SELECT " +
                "            m.activitystarted :: DATE AS day, " +
                "            0                         AS music, " +
                "            ra.spent_time             AS activity " +
                "          FROM music_activity m " +
                "            JOIN rescuetime_activity ra ON ra.id = m.activityid " +
                "          WHERE m.accountid = :accountId " +
                "         ) m " +
                "    GROUP BY day " +
                "    ORDER BY day " +
                ") " +
                "SELECT " +
                "  date AS timestamp, " +
                "  summary.* " +
                "FROM generate_series( " +
                "         DATE_TRUNC('day', :from :: DATE), " +
                "         DATE_TRUNC('day', :to :: DATE), " +
                "         '1 day' :: INTERVAL " +
                "     ) date " +
                "  LEFT JOIN aggregation_summary summary ON summary.aggregated_day = date";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(MusicActivitySalienceSummary.class)
        );
    }
}
