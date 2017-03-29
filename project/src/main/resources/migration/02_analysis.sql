
-- Look for correlation between amount activities within one hour and music played
SELECT
  date_trunc('hour', ma.activityStarted) as hour,
  count(DISTINCT ma.activityName) AS activities,
  sum(DISTINCT ma.trackDuration) / 60.0 AS musicMinutes,
  sum(DISTINCT ma.activityTime) / 60.0 AS activityMinutes
FROM music_activity ma
WHERE ma.accountId = :accountId
--   AND date_trunc('day', ma.activityStarted) = '2017-02-23' -- mock
GROUP BY hour
ORDER BY hour ASC;