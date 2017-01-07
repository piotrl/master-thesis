package net.piotrl.music.lastfm.artist;

import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.artist.repository.ArtistCrudRepository;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ArtistService {

    private final ArtistCrudRepository artistCrudRepository;

    @Autowired
    public ArtistService(ArtistCrudRepository artistCrudRepository) {
        this.artistCrudRepository = artistCrudRepository;
    }

    public List<ArtistEntity> saveArtistsFromTracks(Collection<Track> tracks) {
        return tracks.stream()
                .map(track -> {
                    ArtistEntity artistEntity = new ArtistEntity();
                    artistEntity.setName(track.getArtist());
                    artistEntity.setMbid(track.getArtistMbid());

                    return insertIfExists(artistEntity);
                })
                .collect(Collectors.toList());
    }

    private ArtistEntity insertIfExists(ArtistEntity artistEntity) {
        ArtistEntity existingArtist = artistCrudRepository.findFirstByMbidOrNameOrderByMbid(
                artistEntity.getMbid(), artistEntity.getName()
        );
        if (existingArtist == null) {
            existingArtist = artistCrudRepository.save(artistEntity);
        }

        artistEntity.setId(existingArtist.getId());

        return artistEntity;
    }
}
