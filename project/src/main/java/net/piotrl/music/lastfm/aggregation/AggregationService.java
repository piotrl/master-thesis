package net.piotrl.music.lastfm.aggregation;

import de.umass.lastfm.Track;
import net.piotrl.music.lastfm.artist.ArtistLoader;
import net.piotrl.music.lastfm.track.TrackLoader;
import net.piotrl.music.lastfm.track.TrackService;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AggregationService {

    private final ArtistLoader artistLoader;
    private final TrackService trackService;
    private final TrackLoader trackLoader = new TrackLoader();

    @Autowired
    public AggregationService(ArtistLoader artistLoader, TrackService trackService) {
        this.artistLoader = artistLoader;
        this.trackService = trackService;
    }

    public void startAggregation(LocalDate since) {
        List<Track> tracksWithoutDuration = trackLoader.getTracks(LocalDateTime.of(since, LocalTime.MIDNIGHT));
        List<Track> tracks = trackLoader.fillTrackInfo(tracksWithoutDuration);
        trackService.saveScrobbles(tracks);
        List<TrackEntity> trackEntities = trackService.saveTracks(tracks);
        artistLoader.saveArtistsFromTracks(trackEntities);
    }
}
