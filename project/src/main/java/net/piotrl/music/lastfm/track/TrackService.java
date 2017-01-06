package net.piotrl.music.lastfm.track;

import com.google.gson.Gson;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.track.repository.ScrobbleData;
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

    private Gson gson = new Gson();

    @Autowired
    private TrackRepository trackRepository;

    public void saveScrobbles(Collection<Track> tracks) {
        List<TrackData> list = tracks.stream()
                .map(convertToTrackData())
                .collect(Collectors.toList());

        trackRepository.save(list);
    }


    public void saveTracks(Collection<Track> tracks) {
        List<TrackData> list = tracks.stream()
                .map(convertToTrackData())
                .collect(Collectors.toList());

        trackRepository.save(list);
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
