package net.piotrl.music.lastfm.track;

import com.google.common.base.Strings;
import de.umass.lastfm.*;
import net.piotrl.music.aggregation.AggregationContext;
import net.piotrl.music.lastfm.aggregation.LastfmApiProperties;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TrackLoader {
    public static final int RECENT_DAYS = 1;

    private final LastfmApiProperties lastFmProperties;

    public TrackLoader(AggregationContext context) {
        this.lastFmProperties = context.getLastfmProperties();
    }

    public List<Track> getRecentTracks() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(RECENT_DAYS);
        return getTracks(tenDaysAgo);
    }

    public List<Track> getTracks(LocalDateTime since) {
        PaginatedResult<Track> trackPaginatedResult = getScrobblesFrom(since);
        Stream<Track> trackStream = streamOf(trackPaginatedResult);

        for (int i = 2; i <= trackPaginatedResult.getTotalPages(); i++) {
            PaginatedResult<Track> pageTracks = getScrobblesFrom(i, since);
            trackStream = Stream.concat(trackStream, streamOf(pageTracks));
        }

        return trackStream.collect(Collectors.toList());
    }

    public List<Track> fillTrackInfo(Collection<Track> tracks) {
        return tracks.stream()
                .map(track -> getTrackDetails(track))
                .collect(Collectors.toList());
    }

    public Track getTrackDetails(Track track) {
        String artist = track.getArtist();
        String mbid = track.getMbid();
        if (Strings.isNullOrEmpty(mbid)) {
            mbid = track.getName();
        }

        return getTrackInfo(artist, mbid);
    }

    private Track getTrackInfo(String artist, String trackNameOrMbid) {
        return Track.getInfo(artist, trackNameOrMbid, lastFmProperties.getApiKey());
    }

    private PaginatedResult<Track> getScrobblesFrom(LocalDateTime dateTime) {
        return getScrobblesFrom(1, dateTime);
    }

    private PaginatedResult<Track> getScrobblesFrom(int page, LocalDateTime dateTime) {
        long unixTimestamp = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        return getRecentTracks(lastFmProperties.getUsername(), page, unixTimestamp, lastFmProperties.getApiKey());
    }

    private static PaginatedResult<Track> getRecentTracks(String user, int page, long unixTimestamp, String apiKey) {
        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("limit", String.valueOf(100));
        params.put("page", String.valueOf(page));
        params.put("from", String.valueOf(unixTimestamp));
        Result result = Caller.getInstance().call("user.getRecentTracks", apiKey, params);
        return ResponseBuilder.buildPaginatedResult(result, Track.class);
    }

    private Stream<Track> streamOf(PaginatedResult<Track> pageTracks) {
        Iterator<Track> trackIterator = pageTracks.iterator();
        Iterable<Track> trackIterable = () -> trackIterator;

        return StreamSupport.stream(trackIterable.spliterator(), false);
    }
}
