package net.piotrl.music.modules.lastfm.aggregation;

import de.umass.lastfm.Track;
import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.modules.aggregation.AggregationContext;
import net.piotrl.music.modules.lastfm.artist.ArtistService;
import net.piotrl.music.modules.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.modules.lastfm.track.TrackDto;
import net.piotrl.music.modules.lastfm.track.TrackLoader;
import net.piotrl.music.modules.lastfm.track.TrackService;
import net.piotrl.music.modules.lastfm.track.repository.TrackEntity;
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
        List<TrackDto> trackDtos = trackService.fillTrackInfo(scrobbleDetails, trackLoader);

        for (int i = 0; i < trackDtos.size(); i++) {
            try {
                persistTrackResult(context, trackDtos.get(i), scrobbleDetails.get(i));
            } catch (Exception e) {
                log.error("Saving track error | User {} | Track {}", context.getAccountId(), scrobbleDetails.get(i).getMbid());
                throw e;
            }
        }
    }

    private void persistTrackResult(AggregationContext context, TrackDto trackDetails, Track scrobbleDetails) {
        ArtistEntity tracksArtist = artistService.saveArtist(trackDetails.getScrobble());
        TrackEntity trackEntity = trackDetails.getDetails();
        if (trackEntity == null) {
            trackEntity = trackService.saveUniqueTrack(trackDetails.getLastfmTrackDetails(), tracksArtist);
        }
        trackService.saveScrobble(context.getAccountId(), scrobbleDetails, trackEntity);
    }
}
