package net.piotrl.music.lastfm.track;

import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.track.repository.ScrobbleEntity;
import net.piotrl.music.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
import net.piotrl.music.lastfm.track.repository.TrackCrudRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class TrackService {

    private final Gson gson = new Gson();
    private final TrackCrudRepository trackCrudRepository;
    private final ScrobbleCrudRepository scrobbleCrudRepository;

    @Autowired
    public TrackService(TrackCrudRepository trackCrudRepository, ScrobbleCrudRepository scrobbleCrudRepository) {
        this.trackCrudRepository = trackCrudRepository;
        this.scrobbleCrudRepository = scrobbleCrudRepository;
    }

    public void saveScrobbles(List<Track> tracks, List<TrackEntity> trackEntities) {
        for (int i = 0; i < tracks.size(); i++) {
            ScrobbleEntity entity = convertToScrobbleData(tracks.get(i), trackEntities.get(i));
            scrobbleCrudRepository.save(entity);
        }
    }

    public List<TrackEntity> saveNewTracks(Collection<Track> tracks) {
        List<TrackEntity> list = tracks.stream()
                .map(convertToTrackData())
                .collect(toList());

        return list.stream().map(track -> {
            log.debug("Saving track | Name: %s | Mbid: %s", track.getName(), track.getMbid());
            TrackEntity existingTrack = trackCrudRepository.findFirstByMbidOrNameOrderByMbid(
                    track.getMbid(), track.getName()
            );

            if (existingTrack == null) {
                log.debug("Track not found. Creating new one");
                existingTrack = trackCrudRepository.save(track);
            }

            return existingTrack;
        }).collect(toList());
    }

    private ScrobbleEntity convertToScrobbleData(Track track, TrackEntity entity) {
        ScrobbleEntity scrobbleEntity = new ScrobbleEntity();
        scrobbleEntity.setApiData(gson.toJson(track));
        scrobbleEntity.setPlayedWhen(track.getPlayedWhen());
        scrobbleEntity.setTrackId(entity.getId());

        return scrobbleEntity;
    }

    private Function<Track, TrackEntity> convertToTrackData() {
        return track -> {
            TrackEntity trackEntity = new TrackEntity();
            BeanUtils.copyProperties(track, trackEntity);

            trackEntity.setImageUrlSmall(track.getImageURL(ImageSize.SMALL));
            trackEntity.setImageUrlMedium(track.getImageURL(ImageSize.MEDIUM));
            trackEntity.setImageUrlLarge(track.getImageURL(ImageSize.LARGE));
            trackEntity.setImageUrlExtraLarge(track.getImageURL(ImageSize.EXTRALARGE));
            return trackEntity;
        };
    }
}
