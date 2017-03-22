package net.piotrl.music.api.summary.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MostPopularArtistsProductivity {
    public Map<Integer, Double> averagePercentageActivity;
    public Map<Integer, Double> averageHoursActivity;
    public List<ArtistProductivity> artists;

    @Data
    public class ArtistProductivity {
        public String name;
        public double averageProductivity;
        public long playedTime;
        public Map<Integer, Double> percentageActivity;
        public Map<Integer, Double> hoursActivity;
    }
}
