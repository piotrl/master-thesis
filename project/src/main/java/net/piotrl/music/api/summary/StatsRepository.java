package net.piotrl.music.api.summary;

import net.piotrl.music.api.raw.RawActivity;
import net.piotrl.music.api.summary.dto.MostPopularArtistsProductivity;
import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.time.LocalDate;

public class StatsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public StatsRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public MostPopularArtistsProductivity mostPopularArtistsProductivityStats(LocalDate month, long accountId) {
        if (month == null) {
            month = LocalDate.now();
        }

        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(month.withDayOfMonth(1)))
                .addValue("to", DateUtil.toDate(month.withDayOfMonth(month.lengthOfMonth())))
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

        return jdbcOperations.queryForObject(sql, sqlParams, new BeanPropertyRowMapper<>(MostPopularArtistsProductivity.class));
    }
}
