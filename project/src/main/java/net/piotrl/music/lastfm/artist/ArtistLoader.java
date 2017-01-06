package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.lastfm.artist.repository.ArtistRepository;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
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

    public void saveArtistsFromTracks(Collection<TrackEntity> tracks) {
        tracks.forEach(track -> {
            ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setName(track.getArtist());
            artistEntity.setMbid(track.getArtistMbid());

            artistRepository.insertIfExists(artistEntity);
            track.setArtistId(artistEntity.getId());
        });
    }

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return Artist.getInfo(mbid, connector.properties().getApi_key());
    }
}
