package net.piotrl.music.lastfm.track;

import de.umass.lastfm.Track;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackServiceTest {

    @Autowired
    private TrackService lastFmService;

    @Test
    public void testSavingTrackTo() throws Exception {
        TrackLoader trackLoader = new TrackLoader();
        List<Track> recentTracks = trackLoader.getRecentTracks();
//        lastFmService.saveNewTracks(recentTracks, artists);
    }
}