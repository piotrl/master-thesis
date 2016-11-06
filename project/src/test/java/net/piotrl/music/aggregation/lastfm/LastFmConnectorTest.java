package net.piotrl.music.aggregation.lastfm;

import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastFmConnectorTest {

    private static final String API_KEY = "5f062176c25b0c3570a65bca887188f8";
    private static final String FAKE_API_KEY = "FAKE_5f062176c25b0c3570a65bca887188f8";
    public static final String CORRECT_USERNAME = "grovman";
    public static final String FAKE_USERNAME = "grovman1233211";

    @Test
    public void testPropertiesParams() throws Exception {
        LastFmConnector connector = new LastFmConnector(CORRECT_USERNAME, API_KEY);
        LastFmAuthProperties lastFmAuthProperties = connector.properties();

        assertThat(lastFmAuthProperties.getUsername()).isEqualTo(CORRECT_USERNAME);
    }

    @Test
    public void testGettingUserInfoForExistingUser() throws Exception {
        LastFmConnector connector = new LastFmConnector("grovman", API_KEY);
        User userInfo = connector.getUserInfo();
        assertThat(userInfo).isNotNull();
        assertThat(userInfo.getName()).isEqualTo("grovman");
    }

    @Test
    public void testGettingUserInfoForNotExistingUser() throws Exception {
        LastFmConnector connector = new LastFmConnector(FAKE_USERNAME, API_KEY);
        User userInfo = connector.getUserInfo();
        assertThat(userInfo).isNull();
    }

    @Test
    public void testGettingUserInfoWithFakeApiKey() throws Exception {
        LastFmConnector connector = new LastFmConnector(CORRECT_USERNAME, FAKE_API_KEY);
        User userInfo = connector.getUserInfo();
        assertThat(userInfo).isNull();
    }

    @Test
    public void testLoadingRecentTracks() throws Exception {
        LastFmConnector connector = new LastFmConnector(CORRECT_USERNAME, API_KEY);
        PaginatedResult<Track> userTracksFromLastDay = connector.getUserTracksFromLastDay();
        assertThat(userTracksFromLastDay).isNotNull();
    }

}