package net.piotrl.music.aggregation.lastfm.dao;

import de.umass.lastfm.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LastFmService {

    @Autowired
    private LastFmTrackRepository lastFmTrackRepository;

    public void saveTrack(Collection<Track> tracks) {
        List<LastFmTrackData> list = tracks.stream()
                .map(track -> {
                    LastFmTrackData lastFmTrackData = new LastFmTrackData();
                    lastFmTrackData.setApi_data(track);
                    return lastFmTrackData;
                })
                .collect(Collectors.toList());

        lastFmTrackRepository.save(list);
    }
}
