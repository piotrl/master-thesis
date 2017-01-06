package net.piotrl.music.lastfm.artist;

import de.umass.lastfm.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistLoaderTest {

    @Autowired
    private ArtistLoader artistLoader;

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