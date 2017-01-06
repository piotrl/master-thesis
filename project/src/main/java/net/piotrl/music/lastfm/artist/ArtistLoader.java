package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;
import net.piotrl.music.lastfm.artist.repository.ArtistData;
import net.piotrl.music.lastfm.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArtistLoader {

    @Autowired
    private ArtistRepository artistRepository;

    private LastFmConnector connector = new LastFmConnector();

    public void saveArtistsFromTracks(Collection<Track> tracks) {
        tracks.forEach(track -> {
                    String artistSearchString = getArtistIdentificator(track);

                    ArtistData artistData = new ArtistData();
                    artistData.setName(track.getArtist());
                    artistData.setMbid(track.getArtistMbid());

                    artistRepository.insertIfExists(artistData);
                });
    }

    private String getArtistIdentificator(Track track) {
        String mbid = track.getArtistMbid();
        String artistName = track.getArtist();
        if (!Strings.isNullOrEmpty(mbid)) {
            return mbid;
        } else {
            return artistName;
        }
    }

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return Artist.getInfo(mbid, connector.properties().getApi_key());
    }
}
