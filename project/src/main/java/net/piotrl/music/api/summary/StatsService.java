package net.piotrl.music.api.summary;

import net.piotrl.music.api.summary.artists.ArtistsRepository;
import net.piotrl.music.api.summary.artists.ArtistsSummary;
import net.piotrl.music.api.summary.dto.ArtistProductivity;
import net.piotrl.music.api.summary.dto.MostPopularArtistsProductivity;
import net.piotrl.music.api.summary.dto.MusicActivitySalienceSummary;
import net.piotrl.music.api.summary.dto.ProductivityValue;
import net.piotrl.music.api.summary.tags.TagSummary;
import net.piotrl.music.api.summary.tags.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatsService {

    private final StatsRepository statsRepository;
    private final TagsRepository tagsRepository;
    private final ArtistsRepository artistsRepository;

    @Autowired
    public StatsService(StatsRepository statsRepository, TagsRepository tagsRepository, ArtistsRepository artistsRepository) {
        this.statsRepository = statsRepository;
        this.tagsRepository = tagsRepository;
        this.artistsRepository = artistsRepository;
    }

    public MostPopularArtistsProductivity topArtistsProductivity(LocalDate month, long accountId) {
        if (month == null) {
            month = LocalDate.now();
        }
        List<ArtistProductivity> artistsProductivity = statsRepository.mostPopularArtistsProductivityStats(month, accountId);
        ProductivityValue<Double> averageProductivityForMusic = statsRepository.averageProductivityForMusic(month, accountId);

        MostPopularArtistsProductivity mostPopularArtistsProductivity = new MostPopularArtistsProductivity();
        mostPopularArtistsProductivity.setArtists(artistsProductivity);
        mostPopularArtistsProductivity.setAverageHoursActivity(averageProductivityForMusic);
        return mostPopularArtistsProductivity;
    }

    List<MusicActivitySalienceSummary> musicProductivitySalienceMonthly(int year, int month, long userId) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return statsRepository.musicProductivitySalienceMonthly(firstDayOfMonth, lastDayOfMonth, userId);
    }

    List<MusicActivitySalienceSummary> musicPlayedDuringActivities(int year, int month, long userId) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return statsRepository.musicPlayedDuringActivities(firstDayOfMonth, lastDayOfMonth, userId);
    }

    List<TagSummary> mostPopularTags(int year, int month, long accountId) {
        LocalDate monthDate = LocalDate.of(year, month, 1);
        return tagsRepository.mostPopularTagsInMonth(monthDate, accountId);
    }
    List<ArtistsSummary> mostPopularArtists(int year, int month, long accountId) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return artistsRepository.mostPopularArtists(firstDayOfMonth, lastDayOfMonth, accountId);
    }
}
