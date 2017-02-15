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
        ArtistEntity artistEntity = artistCrudRepository.findArtistByMbidThenByName("1232345", "test1");
        ArtistEntity artistEntity1 = artistCrudRepository.findArtistByMbidThenByName("12345", null);
        ArtistEntity artistEntity2 = artistCrudRepository.findArtistByMbidThenByName("123", "test");

        assertThat(artistEntity).isNull();

        assertThat(artistEntity1).isNotNull();
        assertThat(artistEntity1.getMbid()).isSameAs("12345");

        assertThat(artistEntity2).isNotNull();
        assertThat(artistEntity2.getMbid()).isSameAs("123");
    }
}