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