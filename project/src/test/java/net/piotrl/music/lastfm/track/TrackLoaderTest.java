package net.piotrl.music.lastfm.track;

import de.umass.lastfm.Track;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TrackLoaderTest {

    TrackLoader trackLoader = new TrackLoader();

    @Test
    public void getTrackInfo() throws Exception {
        List<Track> recentTracks = trackLoader.getRecentTracks();
        List<Track> tracks = trackLoader.fillTrackInfo(recentTracks);

        assertThat(tracks).hasSameSizeAs(recentTracks);
    }

    @Test
    public void getRecentTracks() throws Exception {

    }

}