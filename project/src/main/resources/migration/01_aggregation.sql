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

CREATE TABLE public.lastfm_scrobble
(
  id SERIAL PRIMARY KEY NOT NULL,
  track_id INTEGER,
  played_when TIMESTAMP,
  api_data TEXT,
  CONSTRAINT lastfm_scrobbles_lastfm_track_id_fk FOREIGN KEY (track_id) REFERENCES lastfm_track (id)
);

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