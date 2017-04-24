package net.piotrl.music.api.summary.activities;

import net.piotrl.music.shared.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ActivitiesStatsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public ActivitiesStatsRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<MultitaskingOnProductivity> activitiesFrequencyAndProductivity(LocalDateTime from, LocalDateTime to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "" +
                "WITH amoutn_of_activities AS ( " +
                "    SELECT " +
                "      start_time :: TIMESTAMP WITHOUT TIME ZONE, " +
                "      sum(CASE WHEN ra.productivity > 0 " +
                "        THEN ra.spent_time END) AS productive, " +
                "      sum(CASE WHEN ra.productivity < 0 " +
                "        THEN ra.spent_time END) AS distraction, " +
                "      count(*) AS activitiesCount " +
                "    FROM rescuetime_activity ra " +
                "    WHERE ra.account_id = :accountId " +
                "    GROUP BY start_time " +
                "    ORDER BY start_time DESC " +
                ") " +
                "SELECT * " +
                "FROM generate_series( " +
                "         DATE_TRUNC('day', :from :: TIMESTAMP), " +
                "         DATE_TRUNC('day', :to :: TIMESTAMP), " +
                "         '5 min' :: INTERVAL " +
                "     ) date " +
                "  LEFT JOIN amoutn_of_activities summary ON summary.start_time = date";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(MultitaskingOnProductivity.class)
        );
    }
}
