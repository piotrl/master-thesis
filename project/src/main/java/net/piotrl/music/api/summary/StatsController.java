package net.piotrl.music.api.summary;

import net.piotrl.music.api.summary.artists.ArtistsSummary;
import net.piotrl.music.api.summary.dto.MusicActivitySalienceSummary;
import net.piotrl.music.api.summary.tags.TagSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/stats")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping("music/year/{year}/month/{month}/summary")
    public List<MusicActivitySalienceSummary> topArtistsProductivity(@PathVariable int year,
                                                                     @PathVariable int month,
                                                                     Principal principal) {
//        Assert.notNull(principal);
        return statsService.musicProductivitySalienceMonthly(year, month, 1l);
    }

    @RequestMapping("music/year/{year}/month/{month}/musicPlayedDuringActivities")
    public List<MusicActivitySalienceSummary> musicPlayedDuringActivities(@PathVariable int year,
                                                                          @PathVariable int month,
                                                                          Principal principal) {
//        Assert.notNull(principal);
        return statsService.musicPlayedDuringActivities(year, month, 1l);
    }

    @RequestMapping("tags/year/{year}/month/{month}/popular")
    public List<TagSummary> mostPopularTags(@PathVariable int year,
                                            @PathVariable int month,
                                            Principal principal) {
//        Assert.notNull(principal);
        return statsService.mostPopularTags(year, month, 1l);
    }

    @RequestMapping("artists/year/{year}/month/{month}/popular")
    public List<ArtistsSummary> topArtists(@PathVariable int year,
                                           @PathVariable int month,
                                           Principal principal) {
//        Assert.notNull(principal);
        return statsService.mostPopularArtists(year, month, 1l);
    }

}
