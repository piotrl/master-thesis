package net.piotrl.music.modules.lastfm.track;

import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.modules.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.modules.lastfm.tag.TagService;
import net.piotrl.music.modules.lastfm.tag.repository.TagEntity;
import net.piotrl.music.modules.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.modules.lastfm.track.repository.ScrobbleEntity;
import net.piotrl.music.modules.lastfm.track.repository.TrackCrudRepository;
import net.piotrl.music.modules.lastfm.track.repository.TrackEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class TrackService {

    private final TagService tagService;
    private final TrackCrudRepository trackCrudRepository;
    private final ScrobbleCrudRepository scrobbleCrudRepository;

    @Autowired
    public TrackService(TagService tagService,
                        TrackCrudRepository trackCrudRepository,
                        ScrobbleCrudRepository scrobbleCrudRepository) {
        this.trackCrudRepository = trackCrudRepository;
        this.tagService = tagService;
        this.scrobbleCrudRepository = scrobbleCrudRepository;
    }

    public void saveScrobble(Long accountId, Track track, TrackEntity trackEntity) {
        if (track.getPlayedWhen() == null) {
            log.warn("Scrobble has no played time. Skipped | User: {} | Track: {}", accountId, track.getName());
        }
        ScrobbleEntity entity = convertToScrobbleData(accountId, track, trackEntity);
        scrobbleCrudRepository.save(entity);
    }

    public TrackEntity saveUniqueTrack(Track tracksDetails, ArtistEntity artistEntity) {
        log.info("Saving track | Name: {} | Mbid: {}", tracksDetails.getName(), tracksDetails.getMbid());
        TrackEntity savedTrack = trackCrudRepository.findTrackByMbid(tracksDetails.getMbid());
        if (savedTrack == null) {
            savedTrack = trackCrudRepository.findTrackByName(tracksDetails.getName());
        }

        if (savedTrack == null) {
            log.info("Track not found | {} | Creating new entity", tracksDetails.getName());
            TrackEntity entity = convertToTrackData(tracksDetails, artistEntity);
            savedTrack = trackCrudRepository.save(entity);

            saveTags(savedTrack, tracksDetails.getTags());
        }

        return savedTrack;
    }

    public List<TrackDto> fillTrackInfo(List<Track> tracks, TrackLoader loader) {
        return tracks.stream()
                .map(scrobble -> fillTrackInfo(scrobble, loader))
                .collect(toList());
    }

    public TrackDto fillTrackInfo(Track scrobble, TrackLoader loader) {
        log.info("Looking for track | Name: {} | Mbid: {}", scrobble.getName(), scrobble.getMbid());
        TrackDto trackDto = new TrackDto();
        trackDto.setScrobble(scrobble);

        TrackEntity savedTrack = trackCrudRepository.findTrackByMbid(scrobble.getMbid());
        if (savedTrack == null) {
            savedTrack = trackCrudRepository.findTrackByName(scrobble.getName());
        }
        if (savedTrack != null) {
            log.info("Track found in database");
            trackDto.setDetails(savedTrack);
        } else {
            log.info("Track not found, loading from API");
            Track trackDetails = loader.getTrackDetails(scrobble);
            trackDto.setLastfmTrackDetails(trackDetails);
        }

        return trackDto;
    }

    private void saveTags(TrackEntity savedTrack, Collection<String> tags) {
        log.info("Save tags for track | {} | Tags: {}", savedTrack.getId(), tags);
        List<TagEntity> tracksTags = tagService.saveTags(tags);
        tagService.saveTagTrackRelation(savedTrack, tracksTags);
    }

    private ScrobbleEntity convertToScrobbleData(Long accountId, Track track, TrackEntity entity) {
        ScrobbleEntity scrobbleEntity = new ScrobbleEntity();
        scrobbleEntity.setApiData(new Gson().toJson(track));
        scrobbleEntity.setPlayedWhen(track.getPlayedWhen());
        scrobbleEntity.setTrackId(entity.getId());
        scrobbleEntity.setAccountId(accountId);

        return scrobbleEntity;
    }

    private TrackEntity convertToTrackData(Track track, ArtistEntity artistEntity) {
        TrackEntity trackEntity = convertToTrackData(track);

        trackEntity.setArtistId(artistEntity.getId());
        return trackEntity;
    }

    private TrackEntity convertToTrackData(Track track) {
        TrackEntity trackEntity = new TrackEntity();
        BeanUtils.copyProperties(track, trackEntity);

        trackEntity.setImageUrlSmall(track.getImageURL(ImageSize.SMALL));
        trackEntity.setImageUrlMedium(track.getImageURL(ImageSize.MEDIUM));
        trackEntity.setImageUrlLarge(track.getImageURL(ImageSize.LARGE));
        trackEntity.setImageUrlExtraLarge(track.getImageURL(ImageSize.EXTRALARGE));
        return trackEntity;
    }
}
