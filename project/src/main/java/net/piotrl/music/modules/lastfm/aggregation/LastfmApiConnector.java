package net.piotrl.music.modules.lastfm.aggregation;

import de.umass.lastfm.Artist;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class LastfmApiConnector {

    private final LastfmApiProperties authProperties;

    public LastfmApiConnector(LastfmApiProperties authProperties) {
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
