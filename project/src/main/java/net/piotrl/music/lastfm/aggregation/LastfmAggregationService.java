package net.piotrl.music.lastfm.aggregation;

import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.aggregation.AggregationContext;
import net.piotrl.music.lastfm.artist.ArtistService;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.lastfm.track.TrackLoader;
import net.piotrl.music.lastfm.track.TrackService;
import net.piotrl.music.lastfm.track.repository.TrackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class LastfmAggregationService {

    private final ArtistService artistService;
    private final TrackService trackService;

    @Autowired
    public LastfmAggregationService(ArtistService artistService,
                                    TrackService trackService) {
        this.artistService = artistService;
        this.trackService = trackService;
    }

    public void startAggregation(AggregationContext context, LocalDate since) {
        final TrackLoader trackLoader = new TrackLoader(context);
        List<Track> scrobbleDetails = trackLoader.getTracks(LocalDateTime.of(since, LocalTime.MIDNIGHT));
        log.info("Lastfm aggregation scrobbles: {} | User: {}", scrobbleDetails.size(), context.getAccountId());
        List<Track> trackDetails = trackLoader.fillTrackInfo(scrobbleDetails);

        for (int i = 0; i < trackDetails.size(); i++) {
            try {
                persistTrackResult(context, trackDetails.get(i), scrobbleDetails.get(i));
            } catch (Exception e) {
                log.error("Saving track error | User {} | Track {}", context.getAccountId(), trackDetails.get(i).getMbid());
                throw e;
            }
        }
    }

    private void persistTrackResult(AggregationContext context, Track trackDetails, Track scrobbleDetails) {
        ArtistEntity tracksArtist = artistService.saveArtist(trackDetails);
        TrackEntity trackEntities = trackService.saveUniqueTrack(trackDetails, tracksArtist);
        trackService.saveScrobble(context.getAccountId(), scrobbleDetails, trackEntities);
    }
}
