package net.piotrl.music.api.summary.activities;

import net.piotrl.music.api.summary.activities.dto.MultitaskingOnProductivity;
import net.piotrl.music.api.summary.activities.dto.SpentTimeAndTasksCorrelationScatterChart;
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
                "      count(CASE WHEN ra.productivity > 0 " +
                "        THEN ra.spent_time END) AS productive, " +
                "      count(CASE WHEN ra.productivity < 0 " +
                "        THEN ra.spent_time END) AS distraction, " +
                "      count(*)                  AS activitiesCount " +
                "    FROM rescuetime_activity ra " +
                "    WHERE ra.account_id = :accountId " +
                "    GROUP BY start_time " +
                "    ORDER BY start_time DESC ) " +
                "SELECT " +
                "  s.date, " +
                "  sum(summary.productive)      AS productive, " +
                "  sum(summary.distraction)     AS distraction, " +
                "  sum(summary.activitiesCount) AS activitiesCount " +
                "FROM ( " +
                "       SELECT " +
                "         lag(date) " +
                "         OVER ( " +
                "           ORDER BY date ) AS previous, " +
                "         date              AS date " +
                "       FROM generate_series( " +
                "                DATE_TRUNC('minute', :from :: TIMESTAMP), " +
                "                DATE_TRUNC('minute', :to :: TIMESTAMP), " +
                "                '30 minutes' :: INTERVAL) date " +
                "     ) s " +
                "  LEFT JOIN " +
                "  amoutn_of_activities summary " +
                "    ON summary.start_time <= s.date AND summary.start_time >= s.previous " +
                "GROUP BY date ";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(MultitaskingOnProductivity.class)
        );
    }

    public List<SpentTimeAndTasksCorrelationScatterChart> spentTimeAndTasksCorrelationScatterChart(
            LocalDateTime from, LocalDateTime to, long accountId) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("from", DateUtil.toDate(from))
                .addValue("to", DateUtil.toDate(to))
                .addValue("accountId", accountId);

        String sql = "" +
                "SELECT " +
                "  periodEnd, " +
                "  productivity, " +
                "  activitystarted, " +
                "  activitytime, " +
                "  sum(activitytime) OVER (PARTITION BY activitystarted ) AS sumTimeIn5min, " +
                "  sum(activitytime) OVER (PARTITION BY periodEnd )          AS sumTimeByPeriod, " +
                "  count(*) OVER (PARTITION BY activitystarted )          AS tasks, " +
                "  count(*) OVER (PARTITION BY periodEnd )                   AS tasksInPeriod " +
                "FROM (SELECT " +
                "        lead(date) OVER (ORDER BY date) AS periodEnd, " +
                "        date                            AS periodStart " +
                "      FROM generate_series( " +
                "               DATE_TRUNC('minute', :from :: TIMESTAMP), " +
                "               DATE_TRUNC('minute', :to :: TIMESTAMP), " +
                "               '15 minutes' :: INTERVAL) date " +
                "     ) serie " +
                "  JOIN music_activity ma " +
                "    ON ma.activitystarted > serie.periodStart AND ma.activitystarted <= serie.periodEnd ";

        return jdbcOperations.query(
                sql, sqlParams, new BeanPropertyRowMapper<>(SpentTimeAndTasksCorrelationScatterChart.class)
        );
    }
}
