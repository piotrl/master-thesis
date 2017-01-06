package net.piotrl.music.lastfm.track;

import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.lastfm.track.repository.ScrobbleCrudRepository;
import net.piotrl.music.lastfm.track.repository.ScrobbleEntity;
import net.piotrl.music.lastfm.track.repository.TrackCrudRepository;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TrackEntity> saveNewTracks(List<Track> tracks, List<ArtistEntity> tracksArtists) {
        ArrayList<TrackEntity> savedTracks = new ArrayList<>(tracks.size());
        for (int i = 0; i < tracks.size(); i++) {
            TrackEntity savedTrack = saveUniqueTrack(tracks.get(i), tracksArtists.get(i));
            savedTracks.add(savedTrack);
        }

        return savedTracks;
    }

    private TrackEntity saveUniqueTrack(Track track, ArtistEntity artistEntity) {
        log.debug("Saving track | Name: %s | Mbid: %s", track.getName(), track.getMbid());
        TrackEntity savedTrack = trackCrudRepository.findFirstByMbidOrNameOrderByMbid(
                track.getMbid(), track.getName()
        );

        if (savedTrack == null) {
            TrackEntity entity = convertToTrackData(track, artistEntity);
            log.debug("Track not found. Creating new one");
            savedTrack = trackCrudRepository.save(entity);
        }
        return savedTrack;
    }

    private ScrobbleEntity convertToScrobbleData(Track track, TrackEntity entity) {
        ScrobbleEntity scrobbleEntity = new ScrobbleEntity();
        scrobbleEntity.setApiData(gson.toJson(track));
        scrobbleEntity.setPlayedWhen(track.getPlayedWhen());
        scrobbleEntity.setTrackId(entity.getId());

        return scrobbleEntity;
    }

    private TrackEntity convertToTrackData(Track track, ArtistEntity artistEntity) {
        TrackEntity trackEntity = new TrackEntity();
        BeanUtils.copyProperties(track, trackEntity);

        trackEntity.setImageUrlSmall(track.getImageURL(ImageSize.SMALL));
        trackEntity.setImageUrlMedium(track.getImageURL(ImageSize.MEDIUM));
        trackEntity.setImageUrlLarge(track.getImageURL(ImageSize.LARGE));
        trackEntity.setImageUrlExtraLarge(track.getImageURL(ImageSize.EXTRALARGE));
        trackEntity.setArtistId(artistEntity.getId());
        return trackEntity;
    }
}
