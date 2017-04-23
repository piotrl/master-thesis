package net.piotrl.music.api.summary.tags;

import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TagsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public TagsRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public List<TagSummary> mostPopularTagsInMonth(LocalDate month, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(month.withDayOfMonth(1)))
                .addValue("to", DateUtil.toDate(month.withDayOfMonth(month.lengthOfMonth())))
                .addValue("accountId", accountId);

        String sql = "SELECT " +
                "  tag.name, " +
                "  count(*) AS playedTimes," +
                "  sum(track.duration) / 60   AS duration " +
                "FROM (SELECT DISTINCT ON (scrobbleId) * " +
                "      FROM music_activity) ma " +
                "  JOIN lastfm_track track ON track.id = ma.trackid " +
                "  JOIN lastfm_tag_track tagtrack ON tagtrack.track_id = track.id " +
                "  JOIN lastfm_tag tag ON tag.id = tagtrack.tag_id " +
                "WHERE ma.activitystarted >= :from " +
                "      AND ma.activitystarted <= :to " +
                "      AND ma.accountid = :accountId " +
                "GROUP BY tag.name " +
                "ORDER BY playedTimes DESC " +
                "LIMIT 10";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(TagSummary.class)
        );
    }
}
