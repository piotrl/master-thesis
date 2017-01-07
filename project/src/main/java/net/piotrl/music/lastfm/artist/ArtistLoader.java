package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;

public class ArtistLoader {

    private final LastFmConnector connector = new LastFmConnector();

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return Artist.getInfo(mbid, connector.properties().getApi_key());
    }
}
