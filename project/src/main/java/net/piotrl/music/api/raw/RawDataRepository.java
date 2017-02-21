package net.piotrl.music.api.raw;

import com.google.common.collect.Lists;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RawDataRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public RawDataRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<RawActivity> rawActivities(String from, String to, long accountId) {
        return rawActivities(
                DateUtil.toLocalDate(from),
                DateUtil.toLocalDate(to),
                accountId
        );
    }

    public List<RawActivity> rawActivities(LocalDate from, LocalDate to, long accountId) {
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

    public List<RawScrobbles> rawScrobbles(String from, String to, long accountId) {
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

    public List<Integer> monthlyActivities(int year, int month, long accountId) {
        LocalDate initial = LocalDate.of(year, month, 1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());

        List<RawActivity> rawActivities = rawActivities(initial, end, accountId);
        List<Integer> groupedActivities = Lists.newArrayList();
        for (int i = 1; i <= initial.lengthOfMonth(); i++) {
            List<RawActivity> dailyActivities = getDailyActivities(year, month, i, rawActivities);
            groupedActivities.add(dailyActivities.size());
        }

        return groupedActivities;
    }

    private List<RawActivity> getDailyActivities(int year, int month, int day, List<RawActivity> rawActivities) {
        return rawActivities.stream()
                .filter(activity -> {
                    LocalDate dayOfMonth = LocalDate.of(year, month, day);
                    return DateUtil.formatDate(activity.getStartTime()).equals(DateUtil.formatDate(dayOfMonth));
                }).collect(Collectors.toList());
    }
}
