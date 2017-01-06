package net.piotrl.music.lastfm.artist.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistCrudRepositoryTest {

    @Autowired
    private ArtistCrudRepository artistCrudRepository;

    @Test
    public void priorityMbidOverNameSearch() throws Exception {
        ArtistData artistData = artistCrudRepository.findFirstByMbidOrNameOrderByMbid("1232345", "test1");
        ArtistData artistData1 = artistCrudRepository.findFirstByMbidOrNameOrderByMbid("12345", null);
        ArtistData artistData2 = artistCrudRepository.findFirstByMbidOrNameOrderByMbid("123", "test");

        assertThat(artistData).isNull();

        assertThat(artistData1).isNotNull();
        assertThat(artistData1.getMbid()).isSameAs("12345");

        assertThat(artistData2).isNotNull();
        assertThat(artistData2.getMbid()).isSameAs("123");
    }
}