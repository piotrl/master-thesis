package net.piotrl.music.lastfm.scrobble;

import com.google.gson.Gson;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.lastfm.scrobble.repository.LastFmTrackData;
import net.piotrl.music.lastfm.scrobble.repository.LastFmTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrackService {

    @Autowired
    private LastFmTrackRepository lastFmTrackRepository;

    public void saveTracks(Collection<Track> tracks) {
        Gson gson = new Gson();
        List<LastFmTrackData> list = tracks.stream()
                .map(track -> {
                    LastFmTrackData lastFmTrackData = new LastFmTrackData();

                    String trackJson = gson.toJson(track);
                    lastFmTrackData.setApi_data(trackJson);
                    return lastFmTrackData;
                })
                .collect(Collectors.toList());

        lastFmTrackRepository.save(list);
    }
}
