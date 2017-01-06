package net.piotrl.music.lastfm.track;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.track.repository.ScrobbleData;
import net.piotrl.music.lastfm.track.repository.ScrobbleRepository;
import net.piotrl.music.lastfm.track.repository.TrackData;
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

    public List<ScrobbleData> saveScrobbles(Collection<Track> tracks) {
        List<ScrobbleData> list = tracks.stream()
                .map(convertToScrobbleData())
                .collect(Collectors.toList());

        Iterable<ScrobbleData> savedEntities = scrobbleRepository.save(list);
        return Lists.newArrayList(savedEntities);
    }

    public List<TrackData> saveTracks(Collection<Track> tracks) {
        List<TrackData> list = tracks.stream()
                .map(convertToTrackData())
                .collect(Collectors.toList());

        Iterable<TrackData> savedEntities = trackRepository.save(list);
        return Lists.newArrayList(savedEntities);
    }

    private Function<Track, ScrobbleData> convertToScrobbleData() {
        return track -> {
            ScrobbleData scrobbleData = new ScrobbleData();
            scrobbleData.setApiData(gson.toJson(track));
            scrobbleData.setPlayedWhen(track.getPlayedWhen());
            scrobbleData.setTrackId(null);

            return scrobbleData;
        };
    }

    private Function<Track, TrackData> convertToTrackData() {
        return track -> {
            TrackData trackData = new TrackData();
            BeanUtils.copyProperties(track, trackData);

            trackData.setImageUrlSmall(track.getImageURL(ImageSize.SMALL));
            trackData.setImageUrlMedium(track.getImageURL(ImageSize.MEDIUM));
            trackData.setImageUrlLarge(track.getImageURL(ImageSize.LARGE));
            trackData.setImageUrlExtraLarge(track.getImageURL(ImageSize.EXTRALARGE));
            return trackData;
        };
    }
}
