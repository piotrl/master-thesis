package net.piotrl.music.modules.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import net.piotrl.music.modules.lastfm.aggregation.LastfmApiProperties;
import net.piotrl.music.modules.lastfm.aggregation.LastfmApiConnector;

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
