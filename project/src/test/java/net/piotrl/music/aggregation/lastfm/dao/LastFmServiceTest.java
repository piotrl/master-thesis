package net.piotrl.music.aggregation.lastfm.dao;

import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import net.piotrl.music.aggregation.lastfm.LastFmConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastFmServiceTest {

    @Autowired
    private LastFmService lastFmService;

    @Test
    public void testSavingTrackTo() throws Exception {
        LastFmConnector connector = new LastFmConnector();
        PaginatedResult<Track> userTracksFromLastDay = connector.getUserTracksFromLastDay();

        Collection<Track> pageResults = userTracksFromLastDay.getPageResults();
        lastFmService.saveTrack(pageResults);
    }
}