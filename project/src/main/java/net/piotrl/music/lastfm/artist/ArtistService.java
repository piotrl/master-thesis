package net.piotrl.music.lastfm.artist;

import de.umass.lastfm.ImageSize;
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

    public ArtistEntity saveArtist(Track track) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(track.getArtist());
        artistEntity.setMbid(track.getArtistMbid());
        artistEntity.setImageUrl(track.getImageURL(ImageSize.LARGE));

        return insertIfExists(artistEntity);
    }

    private ArtistEntity insertIfExists(ArtistEntity artistEntity) {
        log.info("Saving track | Name: {} | Mbid: {}", artistEntity.getName(), artistEntity.getMbid());
        ArtistEntity existingArtist = artistCrudRepository.findFirstByMbidOrNameOrderByMbid(
                artistEntity.getMbid(), artistEntity.getName()
        );

        if (existingArtist == null) {
            log.info("Artist not found | Name: {} | Mbid: {}", artistEntity.getName(), artistEntity.getMbid());
            log.info("Create new artist");
            return artistCrudRepository.save(artistEntity);
        }
        return existingArtist;
    }
}
