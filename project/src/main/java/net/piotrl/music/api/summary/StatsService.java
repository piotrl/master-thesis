package net.piotrl.music.api.summary;

import net.piotrl.music.api.summary.dto.ArtistProductivity;
import net.piotrl.music.api.summary.dto.MostPopularArtistsProductivity;
import net.piotrl.music.api.summary.dto.MusicActivitySalienceSummary;
import net.piotrl.music.api.summary.dto.ProductivityValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatsService {

    private final StatsRepository statsRepository;

    @Autowired
    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
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

    public List<MusicActivitySalienceSummary> musicProductivitySalienceMonthly(int year, int month, long userId) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return statsRepository.musicProductivitySalienceMonthly(firstDayOfMonth, lastDayOfMonth, userId);
    }

    public List<MusicActivitySalienceSummary> musicPlayedDuringActivities(int year, int month, long userId) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return statsRepository.musicPlayedDuringActivities(firstDayOfMonth, lastDayOfMonth, userId);
    }
}
