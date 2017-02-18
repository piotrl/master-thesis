package net.piotrl.music.api.raw;

import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RawDataRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public RawDataRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<RawActivity> rawActivities(LocalDateTime from, LocalDateTime to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  activity.id           AS activityId, " +
                "  category.name         AS categoryName, " +
                "  action.name           AS actionName, " +
                "  activity.productivity AS productivityScore, " +
                "  activity.spent_time   AS duration, " +
                "  activity.start_time   AS startTime " +
                "FROM rescuetime_activity activity " +
                "  JOIN rescuetime_action action ON activity.action_id = action.id " +
                "  JOIN rescuetime_category category ON action.category_id = category.id " +
                "WHERE start_time >= :from AND start_time <= :to " +
                "   AND activity.account_id = :accountId " +
                "ORDER BY startTime";

        return jdbcOperations.query(sql, sqlParams, new BeanPropertyRowMapper<>(RawActivity.class));
    }

    public List<RawScrobbles> rawScrobbles(LocalDateTime from, LocalDateTime to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  scrobble.id          AS scrobbleId, " +
                "  scrobble.played_when AS startTime, " +
                "  track.name           AS trackName, " +
                "  artist.name          AS artistName " +
                "FROM lastfm_scrobble scrobble " +
                "  JOIN lastfm_track track ON scrobble.track_id = track.id " +
                "  JOIN lastfm_artist artist ON track.artist_id = artist.id " +
                "WHERE scrobble.played_when >= :from AND scrobble.played_when <= :to " +
                "  AND scrobble.account_id = :accountId " +
                "ORDER BY played_when";

        return jdbcOperations.query(sql, sqlParams, new BeanPropertyRowMapper<>(RawScrobbles.class));
    }
}
