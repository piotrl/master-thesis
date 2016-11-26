package net.piotrl.music.aggregation.lastfm.dao;

import com.google.gson.Gson;
import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LastFmService {

    @Autowired
    private LastFmTrackRepository lastFmTrackRepository;

    public void saveTrack(Collection<Track> tracks) {
        Gson gson = new Gson();
        List<LastFmTrackData> list = tracks.stream()
                .map(track -> {
                    String trackJson = gson.toJson(track);
                    LastFmTrackData lastFmTrackData = new LastFmTrackData();
                    lastFmTrackData.setApi_data(trackJson);
                    return lastFmTrackData;
                })
                .collect(Collectors.toList());

        lastFmTrackRepository.save(list);
    }
}
