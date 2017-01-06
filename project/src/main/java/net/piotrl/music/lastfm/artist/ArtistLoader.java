package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;
import net.piotrl.music.lastfm.artist.repository.ArtistData;
import net.piotrl.music.lastfm.artist.repository.ArtistRepository;
import net.piotrl.music.lastfm.track.repository.TrackData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArtistLoader {

    private final ArtistRepository artistRepository;
    private final LastFmConnector connector = new LastFmConnector();

    @Autowired
    public ArtistLoader(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void saveArtistsFromTracks(Collection<TrackData> tracks) {
        tracks.forEach(track -> {
            ArtistData artistData = new ArtistData();
            artistData.setName(track.getArtist());
            artistData.setMbid(track.getArtistMbid());

            artistRepository.insertIfExists(artistData);
            track.setArtistId(artistData.getId());
        });
    }

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return Artist.getInfo(mbid, connector.properties().getApi_key());
    }
}
