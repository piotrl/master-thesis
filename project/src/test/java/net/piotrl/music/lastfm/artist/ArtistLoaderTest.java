package net.piotrl.music.lastfm.artist;

import de.umass.lastfm.Artist;
import net.piotrl.music.mocks.AggregationPropertiesMock;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArtistLoaderTest {

    private ArtistLoader artistLoader;

    @Before
    public void setUp() throws Exception {
        artistLoader = new ArtistLoader(AggregationPropertiesMock.lastfmProperties());
    }

    @Test
    public void loadsSingleArtistByMbid() throws Exception {
        String mbid = "05d4d95c-8803-450f-80c2-7ea606e6f78b";

        Artist artist = artistLoader.getArtistFromApi(mbid);

        assertThat(artist).isNotNull();
        assertThat(artist.getName()).isEqualTo("Maurice Jarre");
    }

    @Test
    public void loadsSingleArtistByEmptyMbid() throws Exception {
        Artist artist = artistLoader.getArtistFromApi("");

        assertThat(artist).isNull();
    }

    @Test
    public void loadsSingleArtistByNullMbid() throws Exception {
        Artist artist = artistLoader.getArtistFromApi(null);

        assertThat(artist).isNull();
    }
}