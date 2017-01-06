package net.piotrl.music.lastfm.artist;

import com.google.common.base.Strings;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.lastfm.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistLoader {

    private final ArtistRepository artistRepository;
    private final LastFmConnector connector = new LastFmConnector();

    @Autowired
    public ArtistLoader(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistEntity> saveArtistsFromTracks(Collection<Track> tracks) {
        return tracks.stream()
                .map(track -> {
                    ArtistEntity artistEntity = new ArtistEntity();
                    artistEntity.setName(track.getArtist());
                    artistEntity.setMbid(track.getArtistMbid());

                    return artistRepository.insertIfExists(artistEntity);
                })
                .collect(Collectors.toList());
    }

    public Artist getArtistFromApi(String mbid) {
        if (Strings.isNullOrEmpty(mbid)) {
            return null;
        }
        return Artist.getInfo(mbid, connector.properties().getApi_key());
    }
}
