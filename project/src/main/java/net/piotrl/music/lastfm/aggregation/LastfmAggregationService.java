package net.piotrl.music.lastfm.aggregation;

import de.umass.lastfm.Track;
import net.piotrl.music.account.Account;
import net.piotrl.music.lastfm.artist.ArtistService;
import net.piotrl.music.lastfm.artist.repository.ArtistEntity;
import net.piotrl.music.lastfm.tag.TagService;
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
public class LastfmAggregationService {

    private final ArtistService artistService;
    private final TrackService trackService;
    private final TagService tagService;
    private final TrackLoader trackLoader = new TrackLoader();

    @Autowired
    public LastfmAggregationService(ArtistService artistService, TrackService trackService, TagService tagService) {
        this.artistService = artistService;
        this.trackService = trackService;
        this.tagService = tagService;
    }

    public void startAggregation(Account account, LocalDate since) {
        List<Track> tracksWithTimePlayed = trackLoader.getTracks(LocalDateTime.of(since, LocalTime.MIDNIGHT));
        List<Track> tracksWithDuration = trackLoader.fillTrackInfo(tracksWithTimePlayed);

        List<ArtistEntity> tracksArtists = artistService.saveArtistsFromTracks(tracksWithDuration);
        List<TrackEntity> trackEntities = trackService.saveNewTracks(tracksWithDuration, tracksArtists);

        trackService.saveScrobbles(tracksWithTimePlayed, trackEntities);

    }
}
