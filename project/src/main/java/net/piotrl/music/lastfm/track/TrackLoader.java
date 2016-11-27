package net.piotrl.music.lastfm.track;

import com.google.common.base.Strings;
import de.umass.lastfm.*;
import net.piotrl.music.lastfm.aggregation.LastFmAuthProperties;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TrackLoader {
    private LastFmAuthProperties lastFmProperties = new LastFmConnector().properties();

    public List<Track> fillTrackInfo(Collection<Track> tracks) {
        return tracks.stream()
                .map(track -> {
                    String artist = track.getArtist();
                    String mbid = track.getMbid();
                    if (Strings.isNullOrEmpty(mbid)) {
                        mbid = track.getName();
                    }

                    return getTrackInfo(artist, mbid);
                })
                .collect(Collectors.toList());
    }

    public Track getTrackInfo(String artist, String trackNameOrMbid) {
        return Track.getInfo(artist, trackNameOrMbid, lastFmProperties.getApi_key());
    }

    public List<Track> getRecentTracks() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        PaginatedResult<Track> trackPaginatedResult = getScrobblesFrom(tenDaysAgo);
        Stream<Track> trackStream = streamOf(trackPaginatedResult);

        for (int i = 2; i <= trackPaginatedResult.getTotalPages(); i++) {
            PaginatedResult<Track> pageTracks = getScrobblesFrom(i, tenDaysAgo);
            trackStream = Stream.concat(trackStream, streamOf(pageTracks));
        }

        return trackStream.collect(Collectors.toList());
    }

    private PaginatedResult<Track> getScrobblesFrom(LocalDateTime dateTime) {
        return getScrobblesFrom(1, dateTime);
    }

    private PaginatedResult<Track> getScrobblesFrom(int page, LocalDateTime dateTime) {
        long unixTimestamp = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        return getRecentTracks(lastFmProperties.getUsername(), page, unixTimestamp, lastFmProperties.getApi_key());
    }

    private static PaginatedResult<Track> getRecentTracks(String user, int page, long unixTimestamp, String apiKey) {
        Map<String, String> params = new HashMap<String, String>();
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
