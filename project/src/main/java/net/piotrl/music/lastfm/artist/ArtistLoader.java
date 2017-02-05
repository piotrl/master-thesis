package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import net.piotrl.music.lastfm.aggregation.LastfmApiProperties;
import net.piotrl.music.lastfm.aggregation.LastfmApiConnector;

public class ArtistLoader {

    private final LastfmApiConnector connector;

    public ArtistLoader(LastfmApiProperties properties) {
        connector = new LastfmApiConnector(properties);
    }

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return connector.getArtistInfo(mbid);
    }
}
