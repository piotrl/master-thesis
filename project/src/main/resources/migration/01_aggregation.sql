CREATE TABLE public.account
(
  id SERIAL PRIMARY KEY,
  email TEXT NOT NULL,
  password TEXT NOT NULL,
  role TEXT NOT NULL,
  created TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX account_email_uindex ON public.account (email);

CREATE TABLE public.lastfm_track
(
  id SERIAL PRIMARY KEY NOT NULL,
  duration INTEGER,
  name TEXT,
  mbid TEXT,
  artist TEXT,
  artist_mbid TEXT,
  album TEXT,
  album_mbid TEXT,
  url TEXT,
  image_url_small TEXT,
  image_url_medium TEXT,
  image_url_large TEXT,
  image_url_extra_large TEXT
);

CREATE TABLE public.lastfm_artist
(
  id SERIAL PRIMARY KEY,
  name TEXT,
  mbid TEXT,
  image_url TEXT
);
COMMENT ON COLUMN public.lastfm_artist.mbid IS 'LastFm artist id';

ALTER TABLE public.lastfm_artist
  ADD CONSTRAINT lastfm_artist_mbid_name_pk UNIQUE (mbid, name);

ALTER TABLE public.lastfm_track ADD artist_id INT NULL;
ALTER TABLE public.lastfm_track
  ADD CONSTRAINT lastfm_track_lastfm_artist_id_fk
FOREIGN KEY (artist_id) REFERENCES lastfm_artist (id);

CREATE TABLE public.lastfm_scrobble
(
  id SERIAL PRIMARY KEY NOT NULL,
  track_id INTEGER,
  played_when TIMESTAMP,
  api_data TEXT,
  CONSTRAINT lastfm_scrobbles_lastfm_track_id_fk FOREIGN KEY (track_id) REFERENCES lastfm_track (id)
);

CREATE TABLE public.lastfm_tag
(
  id SERIAL PRIMARY KEY NOT NULL,
  name TEXT NOT NULL
);
CREATE UNIQUE INDEX lastfm_tag_name_uindex ON public.lastfm_tag (name);

CREATE TABLE rescuetime_activity
(
  id SERIAL PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  productivity INTEGER NOT NULL,
  activity_name TEXT NOT NULL,
  category_name TEXT,
  spent_time INTEGER NOT NULL
);
COMMENT ON COLUMN rescuetime_activity.productivity IS '-2, -1, 0, 1, 2';

ALTER TABLE public.lastfm_scrobble ADD account_id INT NULL;
ALTER TABLE public.lastfm_scrobble
  ADD CONSTRAINT lastfm_scrobble_account_id_fk
FOREIGN KEY (account_id) REFERENCES account (id);

CREATE TABLE public.lastfm_tag_artist
(
  artist_id INT,
  tag_id INT,
  CONSTRAINT lastfm_artist_tag_artist_id_tag_id_pk PRIMARY KEY (artist_id, tag_id),
  CONSTRAINT lastfm_artist_tag_lastfm_artist_id_fk FOREIGN KEY (artist_id) REFERENCES lastfm_artist (id),
  CONSTRAINT lastfm_artist_tag_lastfm_tag_id_fk FOREIGN KEY (tag_id) REFERENCES lastfm_tag (id)
);

CREATE TABLE public.lastfm_tag_track
(
  track_id INT,
  tag_id INT,
  CONSTRAINT lastfm_artist_tag_track_id_tag_id_pk PRIMARY KEY (track_id, tag_id),
  CONSTRAINT lastfm_artist_tag_lastfm_track_id_fk FOREIGN KEY (track_id) REFERENCES lastfm_artist (id),
  CONSTRAINT lastfm_artist_tag_lastfm_tag_id_fk FOREIGN KEY (tag_id) REFERENCES lastfm_tag (id)
);

CREATE TABLE public.rescuetime_category
(
  id SERIAL PRIMARY KEY NOT NULL,
  name TEXT
);

CREATE TABLE public.rescuetime_action
(
  id SERIAL PRIMARY KEY NOT NULL,
  name TEXT,
  category_id INT NOT NULL,
  CONSTRAINT rescuetime_action_rescuetime_category_id_fk FOREIGN KEY (category_id) REFERENCES rescuetime_category (id)
);

ALTER TABLE public.rescuetime_activity ADD account_id INT NOT NULL;
ALTER TABLE public.rescuetime_activity
  ADD CONSTRAINT rescuetime_activity_account_id_fk
FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE public.rescuetime_activity ADD action_id INT NOT NULL;
ALTER TABLE public.rescuetime_activity
  ADD CONSTRAINT rescuetime_activity_rescuetime_action_id_fk
FOREIGN KEY (action_id) REFERENCES rescuetime_action (id);
ALTER TABLE public.rescuetime_activity ADD category_id INT NOT NULL;
ALTER TABLE public.rescuetime_activity
  ADD CONSTRAINT rescuetime_activity_rescuetime_category_id_fk
FOREIGN KEY (category_id) REFERENCES rescuetime_category (id);

CREATE TABLE public.aggregation
(
  id SERIAL PRIMARY KEY NOT NULL,
  account_id INT NOT NULL,
  type TEXT NOT NULL,
  start_time TIMESTAMP NOT NULL,
  finish_time TIMESTAMP,
  CONSTRAINT aggregation_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

ALTER TABLE public.aggregation ADD status TEXT NOT NULL;
ALTER TABLE public.aggregation ADD details TEXT NULL;

CREATE TABLE public.aggregation_userdata
(
  account_id INT PRIMARY KEY NOT NULL,
  lastfm_username TEXT,
  lastfm_api_key TEXT,
  lastfm_secure_key TEXT,
  rescuetime_api_key TEXT,
  CONSTRAINT aggregation_userdata_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);
CREATE UNIQUE INDEX aggregation_userdata_account_id_uindex ON public.aggregation_userdata (account_id);

DROP MATERIALIZED VIEW IF EXISTS music_activity;
CREATE MATERIALIZED VIEW music_activity AS
  SELECT
    activity.id            AS activityId,
    activity.activity_name AS activityName,
    activity.productivity  AS productivity,
    activity.start_time    AS activityStarted,
    activity.end_time      AS activityFinished,
    activity.spent_time    AS activityTime,
    scrobble.id            AS scrobbleId,
    scrobble.track_id      AS trackId,
    scrobble.played_when   AS playedWhen,
    activity.account_id    AS accountId
  FROM rescuetime_activity activity
    LEFT JOIN lastfm_scrobble scrobble
      ON scrobble.played_when >= activity.start_time
         AND scrobble.played_when <= activity.end_time
         AND scrobble.account_id = activity.account_id
  ORDER BY played_when DESC;
