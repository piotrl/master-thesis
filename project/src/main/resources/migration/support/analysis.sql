REFRESH MATERIALIZED VIEW music_activity;

-- Look for correlation between amount activities within one hour and music played
SELECT
  date_trunc('hour', ma.activityStarted)           AS hour,
  count(DISTINCT ma.activityName)                  AS activities,
  sum(DISTINCT COALESCE(track.duration, 0)) / 60.0 AS musicMinutes,
  sum(DISTINCT ma.activityTime) / 60.0             AS activityMinutes
FROM music_activity ma
  LEFT JOIN lastfm_track track ON track.id = ma.trackId
WHERE ma.accountId = :accountId
--   AND date_trunc('day', ma.activityStarted) = '2017-02-23' -- mock
GROUP BY hour
ORDER BY hour ASC;

/**
 *  Most popular artists played in hours (all time)
 */
SELECT
  artist.name,
  sum(track.duration) / 3600.0  AS hours,
  count(DISTINCT ma.scrobbleId) AS scrobbles
FROM music_activity ma
  JOIN lastfm_track track ON track.id = ma.trackId
  JOIN lastfm_artist artist ON track.artist_id = artist.id
WHERE ma.activityStarted >= :from AND ma.activityStarted <= :to
      AND ma.accountid = :accountId
GROUP BY artist.name
ORDER BY scrobbles DESC;

/**
 * summary of listened activities, music and salience per day for current month
 */
WITH aggregation_summary AS (
    SELECT
      day                                   AS aggregated_day,
      sum(music) / 3600.0                   AS music,
      sum(activity) / 3600.0                AS activity,
      (sum(activity) - sum(music)) / 3600.0 AS salience
    FROM (SELECT
            played_when :: DATE AS day,
            track.duration      AS music,
            0                   AS activity
          FROM lastfm_scrobble scrobble
            JOIN lastfm_track track ON scrobble.track_id = track.id
          WHERE scrobble.account_id = :accountId
          UNION
          SELECT
            start_time :: DATE AS day,
            0                  AS music,
            spent_time         AS activity
          FROM rescuetime_activity
          WHERE rescuetime_activity.account_id = :accountId
         ) m
    GROUP BY day
    ORDER BY day
)
SELECT
  date AS timestamp,
  summary.*
FROM generate_series(
         DATE_TRUNC('day', :from :: DATE),
         DATE_TRUNC('day', :to :: DATE),
         '1 day' :: INTERVAL
     ) date
  LEFT JOIN aggregation_summary summary ON summary.aggregated_day = date;

/**
 * Music played ONLY during activities (UNION prevents tracks duplication)
 */
WITH aggregation_summary AS (
    SELECT
      day                                   AS aggregated_day,
      sum(music) / 3600.0                   AS music,
      sum(activity) / 3600.0                AS activity,
      (sum(activity) - sum(music)) / 3600.0 AS salience
    FROM (SELECT DISTINCT ON (m.scrobbleid, m.activitystarted)
            m.activitystarted :: DATE AS day,
            track.duration            AS music,
            0                         AS activity
          FROM music_activity m
            LEFT JOIN lastfm_scrobble scrobble ON m.scrobbleid = scrobble.id
            LEFT JOIN lastfm_track track ON scrobble.track_id = track.id
          WHERE m.accountid = :accountId
          UNION
          SELECT
            m.activitystarted :: DATE AS day,
            0                         AS music,
            ra.spent_time             AS activity
          FROM music_activity m
            JOIN rescuetime_activity ra ON ra.id = m.activityid
          WHERE m.accountid = :accountId
         ) m
    GROUP BY day
    ORDER BY day
)
SELECT
  date AS timestamp,
  summary.*
FROM generate_series(
         DATE_TRUNC('day', :from :: DATE),
         DATE_TRUNC('day', :to :: DATE),
         '1 day' :: INTERVAL
     ) date
  LEFT JOIN aggregation_summary summary ON summary.aggregated_day = date;

/**
 * Most popular tags in month
 */

SELECT
  tag.name,
  count(*)                 AS playedTimes,
  sum(track.duration) / 60 AS duration,
  count(CASE WHEN track.duration = 0 THEN 1 END) AS corrupted
FROM (SELECT DISTINCT ON (scrobbleId) *
      FROM music_activity) ma
  JOIN lastfm_track track ON track.id = ma.trackid
  JOIN lastfm_tag_track tagtrack ON tagtrack.track_id = track.id
  JOIN lastfm_tag tag ON tag.id = tagtrack.tag_id
WHERE ma.accountid = :accountId
GROUP BY tag.name
ORDER BY playedTimes DESC
LIMIT 10;

/**
 * Most popular artists
 */

SELECT
  track.artist               AS artistName,
  max(track.image_url_small) AS imageUrl,
  count(*)                   AS playedTimes,
  sum(track.duration) / 60   AS duration,
  count(CASE WHEN track.duration = 0 THEN 1 END) AS corrupted
FROM (SELECT DISTINCT ON (scrobbleId) *
      FROM music_activity) ma
  JOIN lastfm_track track ON track.id = ma.trackid
WHERE ma.activitystarted >= :from
      AND ma.activitystarted <= :to
      AND ma.accountid = :accountId
GROUP BY track.artist
ORDER BY playedTimes DESC
LIMIT 10;

SELECT
  artist,
  name,
  duration
FROM lastfm_track
WHERE duration = 0;

WITH amoutn_of_activities AS (SELECT
      start_time,
      sum(CASE WHEN ra.productivity > 0 THEN ra.spent_time END) AS productive,
      sum(CASE WHEN ra.productivity < 0 THEN ra.spent_time END) AS distraction,
      count(*) AS activitiesCount
    FROM rescuetime_activity ra
    WHERE ra.account_id = :accountId
    GROUP BY start_time
    ORDER BY start_time DESC)
SELECT
  s.date,
  sum(summary.productive)      AS productive,
  sum(summary.distraction)     AS distraction,
  sum(summary.activitiesCount) AS activitiesCount
FROM (SELECT
         lag(date) OVER (ORDER BY date) AS previous,
         date              AS date
       FROM generate_series(
                DATE_TRUNC('minute', :from :: TIMESTAMP),
                DATE_TRUNC('minute', :to :: TIMESTAMP),
                '30 minutes' :: INTERVAL) date
     ) s
  LEFT JOIN
  amoutn_of_activities summary
    ON summary.start_time <= s.date
   AND summary.start_time >= s.previous
GROUP BY date;