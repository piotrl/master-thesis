-- Look for correlation between amount activities within one hour and music played
SELECT
  date_trunc('hour', ma.activityStarted) AS hour,
  count(DISTINCT ma.activityName)        AS activities,
  sum(DISTINCT track.duration) / 60.0    AS musicMinutes,
  sum(DISTINCT ma.activityTime) / 60.0   AS activityMinutes
FROM music_activity ma
  JOIN lastfm_track track ON track.id = ma.trackId
WHERE ma.accountId = :accountId
--   AND date_trunc('day', ma.activityStarted) = '2017-02-23' -- mock
GROUP BY hour
ORDER BY hour ASC;

/**
   Most popular artists played in minutes
 */
SELECT
  artist.name,
  sum(track.duration)           AS minutes,
  count(DISTINCT ma.scrobbleId) AS scrobbles
FROM music_activity ma
  JOIN lastfm_track track ON track.id = ma.trackId
  JOIN lastfm_artist artist ON track.artist_id = artist.id
WHERE ma.activityStarted >= :from AND ma.activityStarted <= :to
      AND ma.accountid = :accountId
GROUP BY artist.name
ORDER BY minutes DESC;