package net.piotrl.music.lastfm.track;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.track.repository.ScrobbleEntity;
import net.piotrl.music.lastfm.track.repository.ScrobbleRepository;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
import net.piotrl.music.lastfm.track.repository.TrackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrackService {

    private final Gson gson = new Gson();
    private final TrackRepository trackRepository;
    private final ScrobbleRepository scrobbleRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository, ScrobbleRepository scrobbleRepository) {
        this.trackRepository = trackRepository;
        this.scrobbleRepository = scrobbleRepository;
    }

    public List<ScrobbleEntity> saveScrobbles(Collection<Track> tracks) {
        List<ScrobbleEntity> list = tracks.stream()
                .map(convertToScrobbleData())
                .collect(Collectors.toList());

        Iterable<ScrobbleEntity> savedEntities = scrobbleRepository.save(list);
        return Lists.newArrayList(savedEntities);
    }

    public List<TrackEntity> saveTracks(Collection<Track> tracks) {
        List<TrackEntity> list = tracks.stream()
                .map(convertToTrackData())
                .collect(Collectors.toList());

        Iterable<TrackEntity> savedEntities = trackRepository.save(list);
        return Lists.newArrayList(savedEntities);
    }

    private Function<Track, ScrobbleEntity> convertToScrobbleData() {
        return track -> {
            ScrobbleEntity scrobbleEntity = new ScrobbleEntity();
            scrobbleEntity.setApiData(gson.toJson(track));
            scrobbleEntity.setPlayedWhen(track.getPlayedWhen());
            scrobbleEntity.setTrackId(null);

            return scrobbleEntity;
        };
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
