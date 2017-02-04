package net.piotrl.music.lastfm.aggregation;

import de.umass.lastfm.Artist;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import org.springframework.stereotype.Component;

public class LastFmConnector {

    private final LastFmAuthProperties authProperties;

    public LastFmConnector(LastFmAuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    public User getUserInfo() {
        return User.getInfo(this.authProperties.getUsername(), this.authProperties.getApiKey());
    }

    public PaginatedResult<Track> getUserTracksFromLastDay() {
        return User.getRecentTracks(this.authProperties.getUsername(), this.authProperties.getApiKey());
    }

    public Artist getArtistInfo(String mbid) {
        return Artist.getInfo(mbid, this.authProperties.getApiKey());
    }
}
