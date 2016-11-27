package net.piotrl.music.lastfm.scrobble;

import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TrackLoader {
    private LastFmConnector connector = new LastFmConnector();

    public List<Track> getRecentTracks() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        PaginatedResult<Track> trackPaginatedResult = connector.getScrobblesFrom(tenDaysAgo);
        Stream<Track> trackStream = streamOf(trackPaginatedResult);
        for (int i = 2; i <= trackPaginatedResult.getTotalPages(); i++) {
            PaginatedResult<Track> pageTracks = connector.getScrobblesFrom(i, tenDaysAgo);
            trackStream = Stream.concat(trackStream, streamOf(pageTracks));
        }
        return trackStream
                .collect(Collectors.toList());
    }

    private Stream<Track> streamOf(PaginatedResult<Track> pageTracks) {
        Iterator<Track> trackIterator = pageTracks.iterator();
        Iterable<Track> trackIterable = () -> trackIterator;

        return StreamSupport.stream(trackIterable.spliterator(), false);
    }
}
