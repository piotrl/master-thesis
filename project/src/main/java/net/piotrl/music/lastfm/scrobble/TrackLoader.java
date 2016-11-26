package net.piotrl.music.lastfm.scrobble;

import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import net.piotrl.music.lastfm.aggregation.LastFmConnector;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TrackLoader {

    public List<Track> getRecentTracks() {
        LastFmConnector connector = new LastFmConnector();
        PaginatedResult<Track> trackPaginatedResult = connector.getUserTracksFromLastDay();
        Iterator<Track> trackIterator = trackPaginatedResult.iterator();
        Iterable<Track> trackIterable = () -> trackIterator;

        return StreamSupport.stream(trackIterable.spliterator(), false)
                .collect(Collectors.toList());

    }
}
